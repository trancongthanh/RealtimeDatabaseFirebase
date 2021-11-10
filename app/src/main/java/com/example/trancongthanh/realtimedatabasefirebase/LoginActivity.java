package com.example.trancongthanh.realtimedatabasefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button signin, signup, resetpass, cancel;
    TextView tverr;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        matching();
        tverr.setText("Login Activity");
        auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser()!=null){
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
                String spassword = password.getText().toString().trim();
                tverr.setText("");
                if (TextUtils.isEmpty(semail)) {
//                    Toast.makeText(getApplicationContext(), "Hãy Nhập Email", Toast.LENGTH_LONG).show();
                    tverr.setText("Hãy Nhập Email");
                    return;
                }
                if (TextUtils.isEmpty(spassword)) {
//                    Toast.makeText(getApplicationContext(), "Hãy Nhập Password", Toast.LENGTH_LONG).show();
                    tverr.setText("Hãy Nhập Password");
                    return;
                }

                auth.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            tverr.setText(task.getException().getMessage());
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng Nhập thành công", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

            }
        });
    }

    private void matching() {
        email = (EditText) findViewById(R.id.signin_et_email);
        password = (EditText) findViewById(R.id.signin_et_password);
        tverr = (TextView)findViewById(R.id.signin_error) ;
        signin = (Button) findViewById(R.id.signin_btn_Signin);
        signup = (Button) findViewById(R.id.signin_btn_Signup);
        resetpass = (Button) findViewById(R.id.signin_btn_ResetPass);
        cancel = (Button) findViewById(R.id.signin_btn_Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}