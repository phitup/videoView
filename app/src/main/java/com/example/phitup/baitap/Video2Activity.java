package com.example.phitup.baitap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Video2Activity extends AppCompatActivity {

    EditText edtUsernameSignUp, edtiPasswordSignUp, edtConfirmPasswordSignUp;
    Button btnSignUp;
    ProgressDialog mProgressDialog;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);

        AnhXa();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtUsernameSignUp.getText().toString();
                String password = edtiPasswordSignUp.getText().toString();
                String confirmPassword = edtConfirmPasswordSignUp.getText().toString();
                if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(Video2Activity.this, "Yêu cầu điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(password.equals(confirmPassword)){
                    mProgressDialog.setTitle("Đăng Ký");
                    mProgressDialog.setMessage("Hệ thống đang xử lý vui lòng chờ");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    DangKy(email, password);
                }else{
                    Toast.makeText(Video2Activity.this, "Mật Khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void DangKy(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", email);
                            userMap.put("image", "default");

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mProgressDialog.dismiss();
                                        Toast.makeText(Video2Activity.this, "Đăng Ký thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Video2Activity.this, VideoActivity.class));
                                        finish();
                                    }
                                }
                            });

                        } else {
                            mProgressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Video2Activity.this, "Đăng Ký thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void AnhXa() {
        edtUsernameSignUp = findViewById(R.id.editTextUsernameSignUp);
        edtiPasswordSignUp = findViewById(R.id.editTextPasswordSignUp);
        edtConfirmPasswordSignUp = findViewById(R.id.editTextConfirmPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
    }

}
