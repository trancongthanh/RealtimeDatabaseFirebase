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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {
    EditText email;
    TextView error;
    Button signin, signup, forget, cancel;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        matching();
        auth = FirebaseAuth.getInstance();
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
//                String spassword = password.getText().toString().trim();
                if (TextUtils.isEmpty(semail)) {
                    error.setText("Bạn hãy nhập email");
                    return;
                }
//                if (TextUtils.isEmpty(spassword)) {
//                    error.setText("Bạn hãy nhập mật khẩu");
//                    return;
//                }
//                if (spassword.length() <= 6) {
//                    error.setText("Mật khẩu quá ngắn");
//                    return;
//                }
                auth.sendPasswordResetEmail(semail).addOnCompleteListener(ForgetPassActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            error.setText(task.getException().getMessage());
                        } else {
                            Toast.makeText(getApplicationContext(), "Đã gửi email reset password", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgetPassActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

            }
        });
    }

    private void matching() {
        email = findViewById(R.id.forget_et_email);

        signup = findViewById(R.id.forget_btn_register);
        signin = findViewById(R.id.forget_btn_Singin);
        forget = findViewById(R.id.forget_btn_forgetpassword);
        cancel = findViewById(R.id.forget_btn_cancel);
        error = findViewById(R.id.forget_tv_error);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}