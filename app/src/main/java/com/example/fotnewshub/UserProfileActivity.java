package com.example.fotnewshub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    private EditText usernameInput, emailInput, passwordInput;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        ImageView backToHomeUser = findViewById(R.id.backToHomeUser);
        Button editbutton = findViewById(R.id.editbutton);
        Button signoutbutton = findViewById(R.id.signoutbutton);

        usernameInput = findViewById(R.id.Username_input);
        emailInput = findViewById(R.id.Email_input);
        passwordInput = findViewById(R.id.Password_input);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        loadUserData();

        usernameInput.setEnabled(false);
        emailInput.setEnabled(false);
        passwordInput.setEnabled(false);

        backToHomeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, EditInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, SignoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        if (currentUser != null) {
            String firebaseEmail = currentUser.getEmail();
            if (firebaseEmail != null) {
                emailInput.setText(firebaseEmail);
                String storedEmail = sharedPreferences.getString(KEY_EMAIL, "");
                if (!firebaseEmail.equals(storedEmail)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_EMAIL, firebaseEmail);
                    editor.apply();
                }
            }
        }

        String username = sharedPreferences.getString(KEY_USERNAME, "");
        String email = sharedPreferences.getString(KEY_EMAIL, "");
        String password = sharedPreferences.getString(KEY_PASSWORD, "");

        usernameInput.setText(username);

        if (currentUser != null && currentUser.getEmail() != null) {
            emailInput.setText(currentUser.getEmail());
        } else {
            emailInput.setText(email);
        }

        if (!password.isEmpty()) {
            passwordInput.setText("••••••••");
        }
    }
}