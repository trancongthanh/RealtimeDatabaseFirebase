package com.example.trancongthanh.realtimedatabasefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    EditText email, password;
    Button signin, signup, resetpass, cancel;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        matching();
        auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
                String spassword = password.getText().toString().trim();
                if (TextUtils.isEmpty(semail)) {
                    Toast.makeText(getApplicationContext(), "Hãy Nhập Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(spassword)) {
                    Toast.makeText(getApplicationContext(), "Hãy Nhập Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Chiều dài password < 6 ký tự", Toast.LENGTH_LONG).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(getApplicationContext(),"Tạo người dùng thành công",Toast.LENGTH_LONG).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Tạo người dùng thành công", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

//                String semail = email.getText().toString().trim();
//                String spassword = password.getText().toString().trim();
//                if (TextUtils.isEmpty(semail)) {
//                    Toast.makeText(getApplicationContext(), "Hãy Nhập Email", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(spassword)) {
//                    Toast.makeText(getApplicationContext(), "Hãy Nhập Password", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (spassword.length() < 6) {
//                    Toast.makeText(getApplicationContext(), "Chiều dài password < 6 ký tự", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                auth.signInWithEmailAndPassword(semail, spassword).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công thành công", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                            finish();
//                        }
//
//                    }
//                });
            }
        });

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignupActivity.this,ForgetPasswordActivity.class));
                finish();
//                String semail = email.getText().toString().trim();
//
//                if (TextUtils.isEmpty(semail)) {
//                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//                auth.sendPasswordResetEmail(semail)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(SignupActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(SignupActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            }
//                        });
            }
        });

    }


    private void matching() {
        email = (EditText) findViewById(R.id.signup_et_email);
        password = (EditText) findViewById(R.id.signup_et_password);
        signin = (Button) findViewById(R.id.signup_btn_Signin);
        signup = (Button) findViewById(R.id.signup_btn_Signup);
        resetpass = (Button) findViewById(R.id.signup_btn_ForgetPass);
        cancel = (Button) findViewById(R.id.signup_btn_Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}