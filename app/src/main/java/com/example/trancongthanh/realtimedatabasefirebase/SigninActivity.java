package com.example.trancongthanh.realtimedatabasefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {
    EditText email, password;
    TextView error;
    Button signin, signup, resetpass, cancel;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        matching();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Toast.makeText(getApplicationContext(), "Đăng Ký Thành Công", Toast.LENGTH_LONG).show();
            finish();
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, RegisterActivity.class));
                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
                String spassword = password.getText().toString().trim();
                if (TextUtils.isEmpty(semail)) {
                    error.setText("Bạn hãy nhập email");
                    return;
                }
                if (TextUtils.isEmpty(spassword)) {
                    error.setText("Bạn hãy nhập mật khẩu");
                    return;
                }
                if (spassword.length() <= 6) {
                    error.setText("Mật khẩu quá ngắn");
                    return;
                }
                auth.signInWithEmailAndPassword(semail, spassword).addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            error.setText(task.getException().getMessage());
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng Ký Thành Công", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SigninActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, ForgetPassActivity.class));
                finish();
            }
        });
    }

    private void matching() {
        email = findViewById(R.id.signin_et_email);
        password = findViewById(R.id.signin_et_pass);
        signin = findViewById(R.id.signin_btn_login);
        signup = findViewById(R.id.signin_btn_register);
        resetpass = findViewById(R.id.signin_btn_forget);
        cancel = findViewById(R.id.signin_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        error = findViewById(R.id.signin_tv_error);
    }
}