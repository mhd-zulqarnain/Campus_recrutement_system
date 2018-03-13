package com.example.zulqarnain.campusrecruitment.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.admin.AdminActivity;
import com.example.zulqarnain.campusrecruitment.company.CompanyActivity;
import com.example.zulqarnain.campusrecruitment.students.StudentActivity;
import com.example.zulqarnain.campusrecruitment.utilities.Messege;
import com.example.zulqarnain.campusrecruitment.utilities.Validation;
import com.example.zulqarnain.campusrecruitment.utilities.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private Button btSignUp;
    private EditText edEmail;
    private EditText edPass;
    ProgressDialog sessionBar;
    private ProgressBar barProgress;
    private final String TAG = "com.signin.log.zeelog";
    private String userKey;
    private static final int DEVICE_IMEI_PERMISSION = 9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edEmail = (EditText) findViewById(R.id.email);
        edPass = (EditText) findViewById(R.id.password);
        btSignUp = (Button) findViewById(R.id.email_sign_in_button);
        barProgress = (ProgressBar) findViewById(R.id.login_progress);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        askPermission(Manifest.permission.READ_PHONE_STATE, DEVICE_IMEI_PERMISSION);

        sessionBar=new ProgressDialog(LoginActivity.this);
        sessionBar.setMessage("Checking session");
        sessionBar.setCancelable(false);
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


    public void SignUp(View v) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void updataUi() {
        //----storing access token------
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        utils.updateFcm(refreshedToken);
        //------------------------------

        userKey = auth.getCurrentUser().getUid().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userKey);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String type = dataSnapshot.child("type").getValue().toString();
                    String disabled = dataSnapshot.child("disabled").getValue().toString();
                    if (disabled.equals("false")) {
                        if (type.equals("Student")) {
                            Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        } else if (type.equals("Company")) {
                            Intent intent = new Intent(LoginActivity.this, CompanyActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            sessionBar.dismiss();

                        } else if (type.equals("Admin")) {
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    } else if (disabled.equals("true")) {
                        auth.signOut();
                        Messege.messege(getBaseContext(), "Account has been disabled");
                        barProgress.setVisibility(View.GONE);
                        sessionBar.dismiss();

                    }
                }catch (Exception e){
                    barProgress.setVisibility(View.GONE);
                    Messege.messege(getBaseContext(), "Account has been disabled after account");
                    Log.d(TAG, "user gone: "+e);
                    sessionBar.dismiss();
                    auth.signOut();


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
        if (auth.getCurrentUser() != null) {
            sessionBar.show();
            updataUi();
        }
    }

    public void askPermission(String permission, int requestcode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestcode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case DEVICE_IMEI_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Messege.messege(getBaseContext(), "Permission granted");
                else {
                    Messege.messege(getBaseContext(), "Application must have that permission");
                    System.exit(0);
                }
                break;
            default:
        }
    }

}

