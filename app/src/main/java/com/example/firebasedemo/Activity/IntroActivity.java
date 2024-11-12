package com.example.firebasedemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasedemo.R;

public class IntroActivity extends AppCompatActivity {
    private Button startButton;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityIntroBinding.inflate(getLayoutInflater());
//        setContentView(findViewById(R.layout.activity_intro));
//
//        binding.startBtnIntro.setOnClickListener(view -> {
//            startActivity(new Intent(IntroActivity.this, LoginDemo.class));
//        });
//
//        // full màn hình
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        );
//    }
    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        startButton = findViewById(R.id.startBtnIntro);
        startButton.setOnClickListener(v ->{
            Intent intent = new Intent(IntroActivity.this, LoginDemoActivity.class);
            startActivity(intent);
        });

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
               WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

}
