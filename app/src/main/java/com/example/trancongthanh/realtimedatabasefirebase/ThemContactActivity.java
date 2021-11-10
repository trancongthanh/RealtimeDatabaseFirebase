package com.example.trancongthanh.realtimedatabasefirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemContactActivity extends AppCompatActivity {

    EditText id, name, address, email, gender, mobile, home, office;
    Button add, cancel;
    int size; // số lượng contact hiện có

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_contact);
        // lấy giá trị size
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            this.size = extras.getInt("size");
        }
        matching();
       id.setEnabled(false); // không cho người dùng nhập
        id.setText(String.valueOf(size)); // hiển thị id

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyThemMoi();
            }
        });
    }
    private void matching() {
        id = (EditText) findViewById(R.id.et_id);
        name = (EditText) findViewById(R.id.et_name);
        address = (EditText) findViewById(R.id.et_address);
        email = (EditText) findViewById(R.id.et_email);
        gender = (EditText) findViewById(R.id.et_gender);
        mobile = (EditText) findViewById(R.id.et_mobile);
        home = (EditText) findViewById(R.id.et_home);
        office = (EditText) findViewById(R.id.et_office);
        add = (Button) findViewById(R.id.btn_Add);
        cancel = (Button) findViewById(R.id.btn_Huy);
    }

    private void xulyThemMoi() {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            //Kết nối tới node có tên là contacts
            DatabaseReference myRef = database.getReference("contacts");
            String contactId = id.getText().toString();
            String sname = name.getText().toString();
            String saddress = address.getText().toString();
            String sgender = gender.getText().toString();
            String semail = email.getText().toString();
            String soffice = office.getText().toString();
            String shome = home.getText().toString();
            String smobile = mobile.getText().toString();
            myRef.child(contactId).child("address").setValue(saddress);
            myRef.child(contactId).child("email").setValue(semail);
            myRef.child(contactId).child("name").setValue(sname);
            myRef.child(contactId).child("id").setValue(contactId);
            myRef.child(contactId).child("gender").setValue(sgender);
            myRef.child(contactId).child("home").setValue(shome);
            myRef.child(contactId).child("mobile").setValue(smobile);
            myRef.child(contactId).child("office").setValue(soffice);
            Toast.makeText(this,"Thêm Liên Kết Thành Công", Toast.LENGTH_LONG).show();
            finish(); // kết thúc activity
        } catch (Exception e) {
            Toast.makeText(this,"Error:"+e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}