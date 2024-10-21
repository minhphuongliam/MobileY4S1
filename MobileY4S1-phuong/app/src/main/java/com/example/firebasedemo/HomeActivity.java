// HomeActivity.java
package com.example.firebasedemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set a welcome message
        TextView welcomeText = findViewById(R.id.welcomeTextView);
        welcomeText.setText("Welcome to the Home Activity!");
    }
}
