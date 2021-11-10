package com.example.trancongthanh.realtimedatabasefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ListView contacts;
    ArrayAdapter<String> adapter;
    String TAG = "FIREBASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //khởi tạo các thông số
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        contacts = findViewById(R.id.lvContact);
        contacts.setAdapter(adapter);
        // lấy đối tượng DB từ firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // kết nối đến node contacts
        DatabaseReference reference = database.getReference("contacts");
        // truy cập dữ liệu
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                for (DataSnapshot record : snapshot.getChildren()
                ) {
                    String key = record.getKey();
                    String value = record.getValue().toString();

                    adapter.add(key + "\n" + value);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = adapter.getItem(position);
                String key = data.split("\n")[0];
                Intent intent = new Intent(MainActivity.this, CapNhatContactActivity.class);
                intent.putExtra("KEY", key);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // kiểm tra người dùng chọn Menu "Thêm Contact"
        if (item.getItemId() == R.id.mnuAdd) {
            //mở màn hình thêm contact ở đây
            Intent intent = new Intent(MainActivity.this, ThemContactActivity.class);
            String data = adapter.getItem(adapter.getCount()-1);
            String maxID = data.split("\n")[0]; // lấy số lượng contact hiện có
            int addID = Integer.parseInt(maxID)+1;
            intent.putExtra("size", addID); // đưa tag:giá tri cho activity mới
            startActivity(intent);
        } else if (item.getItemId() == R.id.mnuGioithieu)
            Toast.makeText(this, "Chào mừng bạn đến với môn \n Chuyên đề lập trình di động", Toast.LENGTH_LONG).show();
        else if(item.getItemId()==R.id.mnuSignin){
            Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
            startActivity(intent);
//            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}