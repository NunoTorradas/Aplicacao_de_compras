package com.oteusite.aplicaodecomprasandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private Button buttonSave;
    private Button buttonCancel;

    private DatabaseHelper databaseHelper;
    private String currentUsername;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI elements
        editTextName = findViewById(R.id.edit_text_name);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);

        // Retrieve current user's data from the database
        retrieveUserData();

        // Set initial values for the EditText fields
        editTextName.setText(currentUsername);
        editTextPassword.setText(currentPassword);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve new name and password values
                String newName = editTextName.getText().toString();
                String newPassword = editTextPassword.getText().toString();

                // Update the user's name and/or password in the database
                boolean success = updateUser(newName, newPassword);

                if (success) {
                    // Display success message
                    Toast.makeText(ModifyActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();

                    // Finish the activity and return to the previous screen
                    finish();
                } else {
                    // Display error message
                    Toast.makeText(ModifyActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity without saving any changes
                finish();
            }
        });
    }

    private void retrieveUserData() {
        // Retrieve the current user's data from the database based on their identifier (e.g., username)
        // You can use a method like getUserByUsername(String username) in your DatabaseHelper class
        User currentUser = databaseHelper.getUserByUsername(currentUsername);

        // Check if user exists
        if (currentUser != null) {
            // Retrieve the user's name and password
            currentUsername = currentUser.getUsername();
            currentPassword = currentUser.getPassword();
        } else {
            // Handle case when user doesn't exist
            // For example, display an error message or take appropriate action
        }
    }

    private boolean updateUser(String newName, String newPassword) {
        // Update the user's name and/or password in the database
        // You can use a method like updateUser(String currentUsername, String newUsername, String newPassword)
        // in your DatabaseHelper class to perform the update operation

        return databaseHelper.updateUser(currentUsername, newName, newPassword);
    }
}
