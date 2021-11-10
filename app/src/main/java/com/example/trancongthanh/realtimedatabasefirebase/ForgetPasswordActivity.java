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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText email;
    Button resetpass, cancel;
    TextView tverr;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        auth = FirebaseAuth.getInstance();
        matching();

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
//                String spassword = password.getText().toString().trim();
                tverr.setText("");
                if (TextUtils.isEmpty(semail)) {
//                    Toast.makeText(getApplicationContext(), "Hãy Nhập Email", Toast.LENGTH_LONG).show();
                    tverr.setText("Hãy Nhập Email");
                    return;
                }
//                if (TextUtils.isEmpty(spassword)) {
////                    Toast.makeText(getApplicationContext(), "Hãy Nhập Password", Toast.LENGTH_LONG).show();
//                    tverr.setText("Hãy Nhập Password");
//                    return;
//                }
                auth.sendPasswordResetEmail(semail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            tverr.setText(task.getException().getMessage());
                        } else {
                            Toast.makeText(getApplicationContext(), "Đã gửi email reset pass", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgetPasswordActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                });
            }
        });
    }
    private void matching() {
        email = (EditText) findViewById(R.id.forget_et_email);
//        password = (EditText) findViewById(R.id.signin_et_password);
        tverr = (TextView)findViewById(R.id.forget_tv_error) ;
//        signin = (Button) findViewById(R.id.signin_btn_Signin);
//        signup = (Button) findViewById(R.id.signin_btn_Signup);
        resetpass = (Button) findViewById(R.id.forget_btn_ForgetPass);
        cancel = (Button) findViewById(R.id.forget_btn_Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}