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
            public void onClick(View v){
                Map<String, String> loginInfo = getLoginInfo();
                // Check if all login fields are filled out correctly
                if(ValidationUtils.isValidated(LogInActivity.this,loginInfo)){                    
                    attemptLogin(loginInfo, new LoginCallback() {
                        @Override
                        public void onLogin(boolean success) {
                            // Check if there is an account in the database that matches the login information
                            if (success) {
                                Intent intent;
                                // Authentication successful
                                Log.i("Authentication", "Successful");
                                Log.i("Account Type", authAccount.getType());
                                if (authAccount.getType().equals("Admin")) {
                                    intent = new Intent(LogInActivity.this, AdminPendingActivity.class);
                                }else if (authAccount.getType().equals("Doctor")) {
                                    intent = new Intent(LogInActivity.this, DoctorPageActivity.class);
                                }else {
                                    intent = new Intent(LogInActivity.this, PatientPageActivity.class);
                                }
                                intent.putExtra("userData",authAccount);
                                startActivity(intent);
                            } else{
                                authAccount = null;
                            }
                        }
                    });
                    
                }
            }
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
            databaseReference.child(accountType).orderByChild("username")
                .equalTo(loginInfo.get("Email")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                                Account account = accountSnapshot.getValue(Account.class);
         
                                // Check if the password matches
                                if (account != null && account.getPassword().equals(loginInfo.get("Password"))) {
                                    // Authentication successful
                                    Log.i("Authentication", accountType);
                                    if ("Admin".equals(accountType)) {
                                        authAccount = (Admin)accountSnapshot.getValue(Admin.class);
                                    } else if ("Doctor".equals(accountType)) {
                                       authAccount = (Doctor)accountSnapshot.getValue(Doctor.class);
                                    } else if ("Patient".equals(accountType)) {
                                        authAccount = (Patient)accountSnapshot.getValue(Patient.class);
                                    }

                                    authenticationSuccessful.set(true);
                                }
                            }
                        }else{ Log.i("Data snapshot", "Does not exist");}
                        // Invoke the callback if this is the last iteration
                        if (accountTypes.indexOf(accountType) == accountTypes.size() - 1) {
                            if (authAccount == null) {
                                Toast.makeText(LogInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            } else if (authAccount.getRegistrationStatus().equals("Pending")){
                                Log.i("HERE", "HERE");
                                Toast.makeText(LogInActivity.this, "Your account is still pending approval", Toast.LENGTH_SHORT).show();
                                authenticationSuccessful.set(false);
                            } else if (authAccount.getRegistrationStatus().equals("Denied")){
                                Toast.makeText(LogInActivity.this, "Your account has been denied. Please contact 613-123-1234", Toast.LENGTH_SHORT).show();
                                authenticationSuccessful.set(false);
                            } 
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