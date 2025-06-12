package com.example.fotnewshub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button changePasswordBtn;
    ImageView backToHome;
    EditText currentPasswordInput, newPasswordInput, confirmNewPasswordInput;
    ImageView currentPasswordEye, newPasswordEye, confirmNewPasswordEye;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private boolean isCurrentPasswordVisible = false;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmNewPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        changePasswordBtn = findViewById(R.id.change_password_btn);
        backToHome = findViewById(R.id.backToHome);
        currentPasswordInput = findViewById(R.id.current_password_input);
        newPasswordInput = findViewById(R.id.new_password_input);
        confirmNewPasswordInput = findViewById(R.id.confirm_new_password_input);

        currentPasswordEye = findViewById(R.id.current_password_eye);
        newPasswordEye = findViewById(R.id.new_password_eye);
        confirmNewPasswordEye = findViewById(R.id.confirm_new_password_eye);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if (currentUser == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set password eye toggle listeners
        currentPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(currentPasswordInput, currentPasswordEye, isCurrentPasswordVisible);
                isCurrentPasswordVisible = !isCurrentPasswordVisible;
            }
        });

        newPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(newPasswordInput, newPasswordEye, isNewPasswordVisible);
                isNewPasswordVisible = !isNewPasswordVisible;
            }
        });

        confirmNewPasswordEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(confirmNewPasswordInput, confirmNewPasswordEye, isConfirmNewPasswordVisible);
                isConfirmNewPasswordVisible = !isConfirmNewPasswordVisible;
            }
        });
    }

    private void togglePasswordVisibility(EditText editText, ImageView eyeIcon, boolean isCurrentlyVisible) {
        if (isCurrentlyVisible) {
            // Hide password
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // Use system drawable for hidden state
            eyeIcon.setImageResource(android.R.drawable.ic_menu_view);
        } else {
            // Show password
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // Use system drawable for visible state
            eyeIcon.setImageResource(android.R.drawable.ic_menu_agenda);
        }
        // Move cursor to end of text
        editText.setSelection(editText.getText().length());
    }

    private void changePassword() {
        String currentPassword = currentPasswordInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmNewPassword = confirmNewPasswordInput.getText().toString().trim();

        if (currentPassword.isEmpty()) {
            currentPasswordInput.setError("Enter your current password");
            currentPasswordInput.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            newPasswordInput.setError("Enter new password");
            newPasswordInput.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            newPasswordInput.setError("Password must be at least 6 characters");
            newPasswordInput.requestFocus();
            return;
        }

        if (confirmNewPassword.isEmpty()) {
            confirmNewPasswordInput.setError("Confirm your new password");
            confirmNewPasswordInput.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            confirmNewPasswordInput.setError("Passwords do not match");
            confirmNewPasswordInput.requestFocus();
            return;
        }

        if (currentPassword.equals(newPassword)) {
            newPasswordInput.setError("New password must be different from current password");
            newPasswordInput.requestFocus();
            return;
        }

        progressDialog.setMessage("Changing password...");
        progressDialog.setTitle("Update Password");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String email = currentUser.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);

        currentUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updatePassword(newPassword);
                        } else {
                            progressDialog.dismiss();
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage != null && errorMessage.contains("password is invalid")) {
                                currentPasswordInput.setError("Current password is incorrect");
                                currentPasswordInput.requestFocus();
                                Toast.makeText(ForgotPasswordActivity.this,
                                        "Current password is incorrect",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this,
                                        "Authentication failed: " + errorMessage,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updatePassword(String newPassword) {
        currentUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Password changed successfully!",
                                    Toast.LENGTH_LONG).show();

                            currentPasswordInput.setText("");
                            newPasswordInput.setText("");
                            confirmNewPasswordInput.setText("");

                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Failed to update password: " + errorMessage,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}