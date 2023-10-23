package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import android.widget.Toast;
import android.util.Log;
import java.util.concurrent.atomic.AtomicBoolean;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity {

    String userType;

    // Firebase variable declarations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // Account that is authenticated to log in
    Account authAccount = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialize FirebaseAuth
        firebaseDatabase = FirebaseDatabase.getInstance();

        TextView register = findViewById(R.id.createAccountButton);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LogInActivity.this,UserSelectionActivity.class);
                startActivity(intent);
            }
        });
        Button logInButton = findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, WelcomePageActivity.class); // del
                startActivity(intent); // del
            }

            /*
            public void onClick(View v){
                Map<String, String> loginInfo = getLoginInfo();

                if(ValidationUtils.isValidated(LogInActivity.this,loginInfo)){                    
                    attemptLogin(loginInfo, new LoginCallback() {
                        @Override
                        public void onLogin(boolean success) {
                            if (success) {
                                // Authentication successful
                                Log.i("Authentication", "Successful");
                                Intent intent = new Intent(LogInActivity.this, WelcomePageActivity.class);
                                intent.putExtra("userType",userType);
                                startActivity(intent);
                            } else {
                                // Authentication failed: Display error message
                                Toast.makeText(LogInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    
                }
            }*/
        });
    }

    /* 
     * This method gets all the patient information from the fields
     * @return Map<String, String>: Map that contains all the login information
     */
    private Map<String, String> getLoginInfo(){
        // Field variable declarations
        EditText usernameField = findViewById(R.id.usernameField);
        EditText passwordField = findViewById(R.id.passwordField);

        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("Email", usernameField.getText().toString());
        loginInfo.put("Password", passwordField.getText().toString());
        return loginInfo;
    }

    /* 
    * The interface for the callback function for the attemptLogin method
    */
    private interface LoginCallback {
        void onLogin(boolean success);
    }

    private void attemptLogin(Map<String, String> loginInfo, LoginCallback callback) {
        // Initialize Firebase database reference
        databaseReference = firebaseDatabase.getReference("Accounts");
        
        // Create a list of all the possible types of accounts
        ArrayList<String> accountTypes = new ArrayList<>();
        accountTypes.add("Admin"); accountTypes.add("Doctor"); accountTypes.add("Patient");

        // Boolean to check if authentication is successful
        AtomicBoolean authenticationSuccessful = new AtomicBoolean(false);

        // Iterate through the tables
        for (String accountType : accountTypes) {
            Log.i("Account type", accountType);
            databaseReference.child(accountType).orderByChild("username")
                .equalTo(loginInfo.get("Email")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.i("Data snapshot", "Exists");
                            for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                                Account account = accountSnapshot.getValue(Account.class);
                                
                                Log.i("Email from database", account.getUsername());
                                Log.i("Password from database", account.getPassword());
                                // Check if the password matches
                                if (account != null && account.getPassword().equals(loginInfo.get("Password"))) {
                                    // Authentication successful
                                    Log.i("Authentication", "Successful");
                                    authAccount = account;
                                    authenticationSuccessful.set(true);
                                    userType = accountType;
                                }
                            }
                        }else{ Log.i("Data snapshot", "Does not exist");}
                        // Invoke the callback if this is the last iteration
                        if (accountTypes.indexOf(accountType) == accountTypes.size() - 1) {
                            callback.onLogin(authenticationSuccessful.get());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error
                        Log.e("Firebase", "Error: " + databaseError.getMessage());
                        if (accountTypes.indexOf(accountType) == accountTypes.size() - 1) {
                            // This is the last iteration, invoke the callback
                            callback.onLogin(false);
                        }
                    }
                });
        }
    }
}