package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisterActivity extends AppCompatActivity {
    // Firebase variable declarations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //=============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Accounts/User");

        // Retrieving TextView and Button Objects.
        Button registerAsUser = findViewById(R.id.registerButton);

        registerAsUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Map<String, String> userInfo = getUserInfo();
                Log.d("UserRegisterActivity", "User Info onClick: " + userInfo.toString());
                if(ValidationUtils.isValidated(UserRegisterActivity.this,userInfo)){
                    Intent intent = new Intent(UserRegisterActivity.this, LogInActivity.class);
                    addToFirebase();
                    startActivity(intent);
                }
            }
        });
    }

    /*
     * This method gets all the user information from the fields
     * @return Map<String, String>: Map that contains all the user information
     */
    public Map<String, String> getUserInfo(){
        EditText userNameField = findViewById(R.id.userNameField);
        EditText userLastNameField = findViewById(R.id.userLastNameField);
        EditText userEmailField = findViewById(R.id.userEmailField);
        EditText userPasswordField = findViewById(R.id.userPasswordField);
        EditText userPhoneField = findViewById(R.id.userPhoneField);
        EditText userAddressField = findViewById(R.id.userAddressField);
        EditText userHealthCardNumField  = findViewById(R.id.healthCardNumField);


        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("FirstName", userNameField.getText().toString());
        userInfo.put("LastName", userLastNameField.getText().toString());
        userInfo.put("Email", userEmailField.getText().toString());
        userInfo.put("Password", userPasswordField.getText().toString());
        userInfo.put("PhoneNumber", userPhoneField.getText().toString());
        userInfo.put("Address", userAddressField.getText().toString());
        userInfo.put("HealthCard", userHealthCardNumField.getText().toString());

        Log.d("UserRegisterActivity", "User Info getUserInfo: " + userInfo.toString());

        return userInfo;
    }

    /*
     *  This method takes all the info in getUserInfo, puts it into a User
     *  Object and uploads it to Firebase
     */
    private void addToFirebase(){
        User user = new User();
        Map<String,String> userInfo = getUserInfo();

        user.setFirstName(userInfo.get("FirstName"));
        user.setLastName(userInfo.get("LastName"));
        user.setUsername(userInfo.get("Email"));
        user.setPassword(userInfo.get("Password"));
        user.setPhone(userInfo.get("PhoneNumber"));
        user.setAddress(userInfo.get("Address"));
        user.setHealthCardNum(userInfo.get("HealthCard"));

        // Push the user to the appropriate location
        DatabaseReference newUserRef = databaseReference.push(); 
        String newUserKey = newUserRef.getKey(); 
        user.setKey(newUserKey);

        // Success and fail messages
        newUserRef.setValue(user)
                .addOnSuccessListener(voidCallback -> {
                    CreateRequestUtils.addPendingRequest(UserRegisterActivity.this,user, newUserKey);
                })
                .addOnFailureListener(exception ->{
                    Toast.makeText(UserRegisterActivity.this,"Unsuccessful due to " + exception.getMessage(),Toast.LENGTH_SHORT).show();
                });
    }
}