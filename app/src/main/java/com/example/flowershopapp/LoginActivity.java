package com.example.flowershopapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String NAME = "NAME";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText edtName;
    private EditText edtPassword;
    private CheckBox chk;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
        setupSharedPrefs();
        checkPrefs();

        Button loginBtn = findViewById(R.id.btnLogin); // تأكد من أنك قد أضفت الزر في XML
        loginBtn.setOnClickListener(v -> {
            btnLoginOnClick();
        });
    }

    // التحقق من التفضيلات
    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag){
            String name = prefs.getString(NAME, "");
            String password = prefs.getString(PASS, "");
            edtName.setText(name);
            edtPassword.setText(password);
            chk.setChecked(true);
        }
    }

    // إعداد التفضيلات
    private void setupSharedPrefs() {
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = prefs.edit();
    }

    // إعداد الواجهات
    private void setupViews() {
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        chk = findViewById(R.id.chk);
    }

    // عند الضغط على زر تسجيل الدخول
    public void btnLoginOnClick() {
        String name = edtName.getText().toString();
        String password = edtPassword.getText().toString();

        if(chk.isChecked()){
            if(!flag) {
                editor.putString(NAME, name);
                editor.putString(PASS, password);
                editor.putBoolean(FLAG, true);
                editor.apply();
            }
        }

        // يمكنك إضافة عملية التحقق من صحة البيانات هنا
        // مثال: إذا كانت كلمة المرور صحيحة، إنتقل إلى النشاط التالي
        Intent intent = new Intent(LoginActivity.this, IntroductoryActivity.class);
        startActivity(intent);
        finish(); // اغلق شاشة تسجيل الدخول حتى لا يستطيع الرجوع إليها
    }
}
