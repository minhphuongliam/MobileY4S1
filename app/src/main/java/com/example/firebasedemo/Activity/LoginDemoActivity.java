package com.example.firebasedemo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import com.example.firebasedemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginDemoActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.textUsernameLogin);
        passwordEditText = findViewById(R.id.textPasswordLogin);
        loginButton = findViewById(R.id.loginButtonLogin);
        signUpButton = findViewById(R.id.signUpButtonLogin);

        // Tự động kiểm tra xem người dùng đã đăng nhập trước đó hay chưa
        if (loadUserInfo()) {
            // Chuyển trực tiếp đến HomeActivity nếu đã đăng nhập
            navigateToHome();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to SignUpActivity
                Intent intent = new Intent(LoginDemoActivity.this, SignupDemoActivity.class);
                startActivity(intent);
            }
        });

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Lấy thông tin user từ Firebase Authentication
                            String userId = user.getUid();
                            String userEmail = user.getEmail();

                            // Lấy fullName từ Firestore
                            if (userId != null) {
                                //NOTE: Đoạn mã db.collection(...).get().addOnCompleteListener(...) là một hàm bất đồng bộ \
                                // => không chờ kết quả trả
                                //Do đó ta đặt Navigate về Home nếu success trong đoạn if này chứ không phải ngoài
                                db.collection("users")  // Collection tên "users"
                                        .document(userId)  // Truy cập tài liệu người dùng theo userId
                                        .get()
                                        .addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                DocumentSnapshot document = task2.getResult();
                                                if (document != null && document.exists()) {
                                                    // Lấy fullName từ tài liệu Firestore
                                                    String fullName = document.getString("fullName");

                                                    // Lưu thông tin vào SharedPreferences
                                                    saveUserInfo(userId, userEmail, fullName);

                                                    // Chuyển hướng và thông báo sau khi lưu thành công

                                                    Toast.makeText(LoginDemoActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                                                    navigateToHome();
                                                }
                                            } else {
                                                Toast.makeText(LoginDemoActivity.this, "Error fetching to sharedPrefs", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(LoginDemoActivity.this, "ERR : UserID Null", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Authentication failed.";
                        Toast.makeText(LoginDemoActivity.this, "Authentication failed: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void saveUserInfo(String userId, String email, String fullName) {
        try {
            //tạo master key để mã hóa
//        MasterKeys.getOrCreate() sẽ tạo hoặc lấy một Master Key duy nhất được lưu trữ an toàn trong Android Keystore
//        AES256_GCM_SPEC chỉ định rằng Master Key sẽ sử dụng AES với GCM (Galois/Counter Mode) có độ dài khóa 256-bit
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // khởi tạo EncryptedSharedPreferences
            SharedPreferences encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "UserPrefs", // tên file sharedPref
                    masterKeyAlias, // master key để mã hóa
                    this,  // context (Activity / App)
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // mã hóa cho key
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // mã hóa cho value
            );

            // Lưu thông tin user an toàn
            SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
            editor.putString("userId", userId);
            editor.putString("email", email);
            editor.putString("fullName", fullName);

            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving encrypted data", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean loadUserInfo() {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "UserPrefs",
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            String userId = encryptedSharedPreferences.getString("userId", null);
            String email = encryptedSharedPreferences.getString("email", null);
            String fullName = encryptedSharedPreferences.getString("fullName", null);

            Log.d("UserInfo", "userId: " + userId + ", email: " + email + ", fullName: " + fullName);

            if (userId != null && email != null) {
                Toast.makeText(this, "Welcome back, " + (fullName != null ? fullName : "User"), Toast.LENGTH_LONG).show();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading encrypted data", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void navigateToHome() {
        Intent intent = new Intent(LoginDemoActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}