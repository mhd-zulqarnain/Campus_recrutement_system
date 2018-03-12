package com.example.zulqarnain.campusrecruitment.students;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zulqarnain.campusrecruitment.R;
import com.example.zulqarnain.campusrecruitment.models.Student;
import com.example.zulqarnain.campusrecruitment.ui.activities.LoginActivity;
import com.example.zulqarnain.campusrecruitment.utilities.Messege;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class StudentProfileActivity extends AppCompatActivity implements TextWatcher {
    private EditText edSemister;
    private EditText edDepartment;
    private boolean isUpdate;
    private ImageView prfImage;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private String stuKey;
    private TextView pName;
    private static int PICKIMGREQUEST=1;
    ProgressBar progressBar;
    private Uri mImagePath;
    StorageReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        edDepartment = (EditText) findViewById(R.id.profile_student_department);
        edSemister = (EditText) findViewById(R.id.profile_student_semister);
        pName = (TextView) findViewById(R.id.profile_student_name);
        prfImage = (ImageView) findViewById(R.id.prf_pic);
        edDepartment.addTextChangedListener(this);
        edSemister.addTextChangedListener(this);
        isUpdate = false;
        progressBar= (ProgressBar) findViewById(R.id.pic_upload_progress);
        auth = FirebaseAuth.getInstance();
        stuKey = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("student").child(stuKey);
        pName.setText(auth.getCurrentUser().getDisplayName());
        reference = FirebaseStorage.getInstance().getReference("profilePics");
        updateUi();
    }

    public void updateUi() {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key = dataSnapshot.getKey();
                if (key.equals("details")) {
//                try {

                    Student std = dataSnapshot.getValue(Student.class);

                    edDepartment.setText(std.getDepartment());
                    edSemister.setText(std.getSemister());
                    if(std.getImgUrl()!=null)
                    Picasso.with(getBaseContext()).load(std.getImgUrl()).into(prfImage);
                    isUpdate = false;
                }
                /*}catch (Exception e){

                }*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();

                if (key.equals("details")) {
                    Student std = dataSnapshot.getValue(Student.class);
                    edDepartment.setText(std.getDepartment());
                    edSemister.setText(std.getSemister());
                    isUpdate = false;
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //auth.signOut();
                startActivity(new Intent(StudentProfileActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateProfile(View view) {
        if (isUpdate) {
            if(mImagePath!=null){
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle("uploading");
                dialog.show();
                StorageReference sRef= reference.child(mImagePath.getLastPathSegment());
                sRef.putFile(mImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests")
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        /*Log.d("", "onSuccess: "+downloadUrl);
                        HashMap<String,String> prf =new HashMap<String, String>();
                        prf.put("prfPic",downloadUrl.toString());*/
                        ref.child("details").child("imgUrl").setValue(downloadUrl.toString());
                        dialog.dismiss();

                    }
                });
            }
            String dpt = edDepartment.getText().toString();
            String semister = edSemister.getText().toString();
            if (!TextUtils.isEmpty(dpt) && !TextUtils.isEmpty(dpt)) {
                Student student = new Student(semister, dpt);
                ref.child("details").setValue(student);
                Messege.messege(StudentProfileActivity.this, "Profile Updated");

            } else {
                Messege.messege(StudentProfileActivity.this, "All Field should be filled");
            }


        } else
            Messege.messege(StudentProfileActivity.this, "Nothing to update");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.company_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        if (item.getItemId() == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(StudentProfileActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }

    public void addImage(View v){
            Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"),PICKIMGREQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && requestCode==PICKIMGREQUEST && resultCode== Activity.RESULT_OK){
            mImagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImagePath);
                prfImage.setImageBitmap(bitmap);
                isUpdate=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*--------check the updates happened or not-------------*/

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        isUpdate = true;
    }
}
