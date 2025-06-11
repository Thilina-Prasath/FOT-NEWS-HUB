package com.example.fotnewshub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditInfoActivity extends AppCompatActivity {

    private EditText usernameInput, currentPasswordInput, newPasswordInput;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressDialog progressDialog;

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_info);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize progress dialog
        progressDialog = new ProgressDialog(this);

        // Initialize views
        ImageView backToUserProfile = findViewById(R.id.backToUserprofile);
        usernameInput = findViewById(R.id.Username_input);
        currentPasswordInput = findViewById(R.id.current_password_input);
        newPasswordInput = findViewById(R.id.Password_input);
        Button updateButton = findViewById(R.id.okButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load current user data
        loadCurrentUserData();

        backToUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInfoActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInfoActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadCurrentUserData() {
        String currentUsername = sharedPreferences.getString(KEY_USERNAME, "");
        usernameInput.setText(currentUsername);
    }

    private void updateUserInfo() {
        String newUsername = usernameInput.getText().toString().trim();
        String currentPassword = currentPasswordInput.getText().toString();
        String newPassword = newPasswordInput.getText().toString().trim();

        // Get stored password for verification
        String storedPassword = sharedPreferences.getString(KEY_PASSWORD, "");
        String userEmail = sharedPreferences.getString(KEY_EMAIL, "");

        // Validate inputs
        if (TextUtils.isEmpty(newUsername)) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, "Please enter your current password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verify current password
        if (!currentPassword.equals(storedPassword)) {
            Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate new password if provided
        if (!TextUtils.isEmpty(newPassword) && newPassword.length() < 6) {
            Toast.makeText(this, "New password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // If password needs to be changed, update Firebase Authentication
        if (!TextUtils.isEmpty(newPassword)) {
            updateFirebasePassword(currentPassword, newPassword, newUsername, userEmail);
        } else {
            // Only update username in SharedPreferences
            updateLocalUserData(newUsername, null);
        }
    }

    private void updateFirebasePassword(String currentPassword, String newPassword, String newUsername, String userEmail) {
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Updating password...");
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // Re-authenticate user before changing password
        AuthCredential credential = EmailAuthProvider.getCredential(userEmail, currentPassword);

        currentUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Now update the password
                            currentUser.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                // Update local data after successful Firebase update
                                                updateLocalUserData(newUsername, newPassword);
                                            } else {
                                                Toast.makeText(EditInfoActivity.this,
                                                        "Failed to update password: " + task.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(EditInfoActivity.this,
                                    "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void updateLocalUserData(String newUsername, String newPassword) {
        // Save updated information to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, newUsername);

        // Update password only if a new one is provided
        if (newPassword != null) {
            editor.putString(KEY_PASSWORD, newPassword);
        }

        editor.apply();

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        // Return to user profile
        Intent intent = new Intent(EditInfoActivity.this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }
}