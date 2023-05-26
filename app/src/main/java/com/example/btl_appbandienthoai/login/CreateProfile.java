package com.example.btl_appbandienthoai.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_appbandienthoai.R;
import com.example.btl_appbandienthoai.login.model.UserDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateProfile extends AppCompatActivity {
    ArrayAdapter ad;
    EditText Name, contact, birthdate;
    TextView Email, pass;

    Button CreateProfile;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createprofile);
        Name = findViewById(R.id.sname);
        Email = findViewById(R.id.semail);
        birthdate = findViewById(R.id.birthdate);
        contact = findViewById(R.id.contact);
        pass = findViewById(R.id.pass);
        CreateProfile = findViewById(R.id.createProfile);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        Email.setText(email);
        pass.setText(password);
        CreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("profile");
                String name = Name.getText().toString();
                String contactn = contact.getText().toString();
                String Birthdate = birthdate.getText().toString();
                String phoneRegex = "^0\\d{9}$";
                Pattern phonePattern = Pattern.compile(phoneRegex);
                Matcher phoneMatcher = phonePattern.matcher(contactn);
                String pattern = "\\d{2}-\\d{2}-\\d{4}"; // Biểu thức chính quy để kiểm tra định dạng xx-xx-xxxx
                if (name.isEmpty() || contactn.isEmpty() || Birthdate.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!phoneMatcher.matches()) {
                    Toast.makeText(com.example.btl_appbandienthoai.login.CreateProfile.this, "Số điện thoại nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                } else if (!Birthdate.matches(pattern)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập ngày sinh đúng định dạng (xx-xx-xxxx)", Toast.LENGTH_SHORT).show();
                } else {
                    UserDetails helperClass = new UserDetails(name, email, contactn, Birthdate, password);
                    reference.child(name).setValue(helperClass);
                    Toast.makeText(com.example.btl_appbandienthoai.login.CreateProfile.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(com.example.btl_appbandienthoai.login.CreateProfile.this, LoginActivity.class);
                    startActivity(in);
                }
            }
        });

    }
}


