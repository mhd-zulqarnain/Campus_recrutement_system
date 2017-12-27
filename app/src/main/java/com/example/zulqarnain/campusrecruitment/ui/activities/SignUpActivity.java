package com.example.zulqarnain.campusrecruitment.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.CompanyActivity;
import com.example.zulqarnain.campusrecruitment.students.StudentActivity;
import com.example.zulqarnain.campusrecruitment.utilities.Messege;
import com.example.zulqarnain.campusrecruitment.utilities.Validation;
import com.example.zulqarnain.campusrecruitment.utilities.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private Spinner typeSpinner;
    private Button btSignUp;

    private EditText edName;
    private EditText edEmail;
    private EditText edPass;
    private ProgressBar barProgress;
    private final String TAG = "con.log.zeelog";
    private String uType;
    private String mKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edEmail = (EditText) findViewById(R.id.email);
        edPass = (EditText) findViewById(R.id.password);
        edName = (EditText) findViewById(R.id.name);
        btSignUp = (Button) findViewById(R.id.email_sign_up_button);
        barProgress = (ProgressBar) findViewById(R.id.signup_progress);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        typeSpinner = (Spinner) findViewById(R.id.sign_up_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sign_up_type,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);


    }

    public void signUp(View v) {
        String response = typeSpinner.getSelectedItem().toString();
//        Messege.messege(getBaseContext(),"response "+response);
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();
        final String name = edName.getText().toString();
        uType = typeSpinner.getSelectedItem().toString();


        if (TextUtils.isEmpty(name)) {
            Messege.messege(getBaseContext(), "Enter the name ");
            return;
        }
        if (TextUtils.isEmpty(email) || !Validation.isEmailValid(email)) {
            Messege.messege(getBaseContext(), "Invalid email");
            return;
        }
        if (TextUtils.isEmpty(pass) || TextUtils.getTrimmedLength(pass) < 6) {
            Messege.messege(getBaseContext(), "invalid password");
            return;
        }
        barProgress.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            barProgress.setVisibility(View.GONE);
                            mKey = auth.getCurrentUser().getUid().toString();
                            HashMap<String, String> user = new HashMap<String, String>();
                            user.put("uid", mKey);
                            user.put("type", uType);

                            mDatabase.child(mKey).setValue(user);
                            //inserting name
                            UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            auth.getCurrentUser().updateProfile(profileName);
                            Messege.messege(getBaseContext(), "Successfully signed up");

                            //----storing access token------
                                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                utils.updateFcm(refreshedToken);
                            //------------------------------

                            updataUi(profileName.getDisplayName());

                        } else {
                            barProgress.setVisibility(View.GONE);
                            Messege.messege(getBaseContext(), "Failed");
                            Log.d(TAG, "onComplete: " + task.getException());
                        }

                        // ...
                    }
                });
    }

    public void updataUi(String name) {
        HashMap<String, String> user = new HashMap<String, String>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (uType.equals("Student")) {
            user.put("name", name);
            mDatabase.child("student").child(mKey).setValue(user);
            Intent intent = new  Intent(SignUpActivity.this, StudentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent );
            finish();

        } else if (uType.equals("Company")) {
            user.put("name", name);
            mDatabase.child("company").child(mKey).setValue(user);
            Intent intent = new Intent(SignUpActivity.this, CompanyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            mKey = auth.getCurrentUser().getUid().toString();

            mDatabase = FirebaseDatabase.getInstance().getReference("users").child(mKey).child("type");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String val = dataSnapshot.getValue().toString();
                    if (val.equals("Student")) {
                        startActivity(new Intent(SignUpActivity.this, StudentActivity.class));
                        finish();


                    } else if (val.equals("Company")) {
                        startActivity(new Intent(SignUpActivity.this, CompanyActivity.class));
                        finish();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}

