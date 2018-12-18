package com.example.phitup.baitap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VideoActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    TextView txtSignUp;
    Button btnSignIn;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        AnhXa();

        CheckUser();

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoActivity.this, Video2Activity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(VideoActivity.this, "Yêu cầu điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    mProgressDialog.setTitle("Đăng Nhập");
                    mProgressDialog.setMessage("Hệ thống đang xử lý vui lòng chờ");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    DangNhap(email, password);
                }
            }
        });

    }

    private void CheckUser() {

    }

    private void DangNhap(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Intent intent = new Intent(VideoActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {

                        }
                    }
                });
    }

    private void AnhXa() {
        edtUsername = findViewById(R.id.editTextUsername);
        edtPassword = findViewById(R.id.editTextPassword);
        txtSignUp = findViewById(R.id.textViewSignup);
        btnSignIn = findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
    }
}