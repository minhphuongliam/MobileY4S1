package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class SignupDemo extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton, loginButtonSignup; // Add loginButtonSignup
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.textEmailSignup);
        passwordEditText = findViewById(R.id.textPasswordSignup);
        confirmPasswordEditText = findViewById(R.id.textConfirmPasswordSignup);
        signUpButton = findViewById(R.id.signupButtonSignup);
        loginButtonSignup = findViewById(R.id.loginButtonSignup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        // Add click listener to loginButtonSignup to navigate to LoginDemo
        loginButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginDemo activity
                startActivity(new Intent(SignupDemo.this, LoginDemo.class));
                finish(); // Optionally finish the current activity
            }
        });
    }

    private void createAccount() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (!isValidPassword(password)) {
            passwordEditText.setError("Password must be at least 8 characters, include one uppercase letter and one special character.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match.");
            return;
        }

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getSignInMethods().isEmpty()) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignupDemo.this, task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignupDemo.this, "Registration successful.",
                                                Toast.LENGTH_SHORT).show();
                                        // Return to login page after success
                                        startActivity(new Intent(SignupDemo.this, LoginDemo.class));
                                        finish();
                                    } else {
                                        Toast.makeText(SignupDemo.this, "Registration failed: " +
                                                        task1.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(SignupDemo.this, "Invalid email format. Please enter a correct email.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignupDemo.this, "An error occurred: " + exception.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*\\.,])(?=.{8,}).*$";
        return password.matches(passwordPattern);
    }
}