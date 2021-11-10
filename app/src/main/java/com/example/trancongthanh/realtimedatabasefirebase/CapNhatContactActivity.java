package com.example.trancongthanh.realtimedatabasefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CapNhatContactActivity extends AppCompatActivity {
    EditText id, name, address, email, gender, mobile, home, office;
    Button update, delete, cancel;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_contact);
        matching();
        getContactDetail();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Kết nối tới node có tên là contacts
                // kết nối bản contacts
                DatabaseReference myRef = database.getReference("contacts");
                // lấy dữ liệu do người dùng nhập vào
                String contactId=key;
                String sname =name.getText().toString();
                String saddress =address.getText().toString();
                String sgender=gender.getText().toString();
                String semail =email.getText().toString();
                String soffice =office.getText().toString();
                String shome =home.getText().toString();
                String smobile =mobile.getText().toString();
                // chèn các dữ liệu vào các cột cho phù hợp
                myRef.child(contactId).child("address").setValue(saddress);
                myRef.child(contactId).child("email").setValue(semail);
                myRef.child(contactId).child("name").setValue(sname);
                myRef.child(contactId).child("id").setValue(contactId);
                myRef.child(contactId).child("gender").setValue(sgender);
                myRef.child(contactId).child("home").setValue(shome);
                myRef.child(contactId).child("mobile").setValue(smobile);
                myRef.child(contactId).child("office").setValue(soffice);
                Toast.makeText(getApplicationContext(),"Cập nhật liên hệ "+String.valueOf(key)+" thành công ",Toast.LENGTH_LONG).show();
                finish(); // đóng activity
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
                DatabaseReference myRef = database.getReference("contacts");
                myRef.child(key).removeValue();
                Toast.makeText(getApplicationContext(),"Xóa liên hệ "+String.valueOf(key)+" thành công ",Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }

    private void getContactDetail() {
        // lấy key được truyền từ MainActivity
        Intent intent = getIntent();
        key = intent.getStringExtra("KEY");
        // lấy dữ liệu từ firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // kết nối tới từng table trong db
        DatabaseReference myReference = database.getReference("contacts");
        //truy suất và lắng nghe sự thay đổi dữ liệu
        //chỉ truy suất node được chọn trên ListView myRef.child(key)
        //addListenerForSingleValueEvent để lấy dữ liệu đơn
        myReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    //trả về 1 DataSnapShot, mà giá trị đơn nên gọi getValue trả về 1 HashMap
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    //HashMap này sẽ có kích  thước bằng số Node con bên trong node truy vấn
                    //mỗi phần tử có key là name được định nghĩa trong cấu trúc Json của Firebase
                    id.setText(key);
                    name.setText(hashMap.get("name").toString());
                    email.setText(hashMap.get("email").toString());
                    address.setText(hashMap.get("address").toString());
                    gender.setText(hashMap.get("gender").toString());
                    // lấy dữ liệu phần số điện thoại
                    home.setText(hashMap.get("home").toString());
                    office.setText(hashMap.get("office").toString());
                    mobile.setText(hashMap.get("mobile").toString());
                } catch (Exception e) {
                    Log.d("LOI_JSON", e.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("LOI_CHITIET", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
    private void matching() {
        id = findViewById(R.id.et_id);
        name = findViewById(R.id.et_name);
        address = findViewById(R.id.et_address);
        email = findViewById(R.id.et_email);
        gender = findViewById(R.id.et_gender);
        mobile = findViewById(R.id.et_mobile);
        home = findViewById(R.id.et_home);
        office = findViewById(R.id.et_office);
        update = findViewById(R.id.btn_Update);
        delete = findViewById(R.id.btn_Delete);
        cancel = findViewById(R.id.btn_Huy);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}