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

public class RegisterActivity extends AppCompatActivity {

    EditText email, password;
    Button signup, signin, forget, cancel;
    TextView error;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        matching();
        auth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // đăng ký
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

                auth.createUserWithEmailAndPassword(semail, spassword)
                        .addOnCompleteListener(RegisterActivity.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            error.setText(task.getException().getMessage());
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Đăng Nhập Thành Công", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }
                                });

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new activity signin
                startActivity(new Intent(RegisterActivity.this, SigninActivity.class));
                finish();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new forgetpass activity
                startActivity(new Intent(RegisterActivity.this, ForgetPassActivity.class));
                finish();
            }
        });
    }

    private void matching() {
        email = findViewById(R.id.regiser_et_email);
        password = findViewById(R.id.register_et_Password);
        signup = findViewById(R.id.register_btn_register);
        signin = findViewById(R.id.register_btn_Singin);
        forget = findViewById(R.id.register_btn_forgetpassword);
        cancel = findViewById(R.id.register_btn_cancel);
        error = findViewById(R.id.register_tv_error);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}