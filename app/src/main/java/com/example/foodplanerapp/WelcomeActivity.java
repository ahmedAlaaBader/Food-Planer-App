package com.example.foodplanerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.annotations.NonNull;

public class WelcomeActivity extends AppCompatActivity {
    Button signupBtn;
    TextView loginTxt;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    ImageView googleImage;
    public static Boolean isGuest=false;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(o.getData());
            try {
                GoogleSignInAccount googleSignInAccount = accountTask.getResult(ApiException.class);
                String idToken = googleSignInAccount.getIdToken();
                Log.d("Auth_pages", "Google ID Token: " + idToken); // Log the ID token for debugging
                AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
                auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            auth = FirebaseAuth.getInstance();
                            Toast.makeText(WelcomeActivity.this, "Authentication success."+ auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            navigateToMain();
                        } else {
                            Toast.makeText(WelcomeActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.e("Auth_pages", "Authentication failed", task.getException()); // Log the exception for debugging
                        }
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
                Log.e("Auth_pages", "Google sign in failed", e); // Log the exception for debugging
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcom);
        signupBtn=findViewById(R.id.signupBtn);
        loginTxt=findViewById(R.id.loginTxt);
        googleImage = findViewById(R.id.googleImage);

        FirebaseApp.initializeApp(this);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_Auth))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(WelcomeActivity.this, options);
        auth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,RegistrationActivity.class));
            }
        });
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
            }
        });
        googleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });

    }
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            navigateToMain();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}