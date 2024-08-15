package com.example.foodplanerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

   // private static final String TAG = "SinUpActivity";
   // private static final int RC_SIGN_IN = 9001;

    EditText email, password;
    Button sinup;
    TextView login;

    private FirebaseAuth mAuth;
   // private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        email = findViewById(R.id.emilTxt);
        password = findViewById(R.id.passwordTxt);
        sinup = findViewById(R.id.signupAtRegisterBtn);
       // google_sinup = findViewById(R.id.btn_google_sinup);
        login = findViewById(R.id.loginAtRegisterTxt);

        // Set click listener for sign-up button
        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                if (userEmail.isEmpty()||userPassword.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Email or Password cant be Empty.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                signUpWithEmailAndPassword(userEmail, userPassword);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });

    }

    private void signUpWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegistrationActivity.this, "Sign up successful.",
                                    Toast.LENGTH_SHORT).show();
                            navigateToMain();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Sign up failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void navigateToLogin() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish current activity to prevent going back to sign-up
    }
    private void navigateToMain() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish current activity to prevent going back to sign-up
    }
}