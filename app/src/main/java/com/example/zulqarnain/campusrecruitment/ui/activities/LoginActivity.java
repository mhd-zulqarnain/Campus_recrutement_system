package com.example.zulqarnain.campusrecruitment.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.company.CompanyActivity;
import com.example.zulqarnain.campusrecruitment.students.StudentActivity;
import com.example.zulqarnain.campusrecruitment.utils.Messege;
import com.example.zulqarnain.campusrecruitment.utils.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity  {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private Button btSignUp;
    private EditText edEmail;
    private EditText edPass;
    private ProgressBar barProgress;
    private final String TAG = "com.signin.log.zeelog";
    private String mKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase= FirebaseDatabase.getInstance().getReference();

        edEmail = (EditText) findViewById(R.id.email);
        edPass = (EditText) findViewById(R.id.password);
        btSignUp = (Button) findViewById(R.id.email_sign_in_button);
        barProgress = (ProgressBar) findViewById(R.id.login_progress);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");


    }

    public void signIn(View v) {
//        Messege.messege(getBaseContext(),"response "+response);
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();


        if (TextUtils.isEmpty(email) || !Validation.isEmailValid(email)) {
            Messege.messege(getBaseContext(), "Invalid email");
            return;
        }
        if (TextUtils.isEmpty(pass) || TextUtils.getTrimmedLength(pass) < 6) {
            Messege.messege(getBaseContext(), "invalid password");
            return;
        }
        barProgress.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                           updataUi();

                        } else {
                            barProgress.setVisibility(View.GONE);
                            Messege.messege(getBaseContext(), "Failed");
                            Log.d(TAG, "onComplete: " + task.getException());
                        }

                    }
                });
    }



    public void SignUp(View v){
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //checking the user type from firebasedatabase
    public void updataUi()
    {
        String userKey= auth.getCurrentUser().getUid().toString();
        mDatabase= FirebaseDatabase.getInstance().getReference("users").child(userKey);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = dataSnapshot.child("type").getValue().toString();

                if (type.equals("Student")) {
                    startActivity(new Intent(LoginActivity.this, StudentActivity.class));
                    finish();


                } else if (type.equals("Company")) {
                    startActivity(new Intent(LoginActivity.this, CompanyActivity.class));
                    finish();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null){
            updataUi();
        }
    }
}

