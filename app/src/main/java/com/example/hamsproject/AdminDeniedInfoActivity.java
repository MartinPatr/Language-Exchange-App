package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AdminDeniedInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_denied_info);

        Intent intent = getIntent();

        String accountID = intent.getStringExtra("userInfo");
        getUserInfo(accountID);

        Button acceptButton = findViewById(R.id.acceptButton);
        Button backButton = findViewById(R.id.backButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(accountID);
                Intent intent = new Intent(AdminDeniedInfoActivity.this, AdminDeniedActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AdminDeniedInfoActivity.this, AdminDeniedActivity.class);
                startActivity(intent);
            }
        }));
    }


    private void getUserInfo(String accountID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accounts");

        //This list represents the tables in the Accounts table in the database
        ArrayList<String> accountTypes = new ArrayList<>();
        accountTypes.add("Doctor");
        accountTypes.add("Patient");

        for (String accountType : accountTypes) {
            DatabaseReference accountTypeRef = databaseReference.child(accountType);
            accountTypeRef.orderByKey().equalTo(accountID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                            //Gets all of the user's info
                            String firstName = userSnapshot.child("firstName").getValue(String.class);
                            String lastName = userSnapshot.child("lastName").getValue(String.class);
                            String username = userSnapshot.child("username").getValue(String.class);
                            String phone = userSnapshot.child("phone").getValue(String.class);
                            String address = userSnapshot.child("address").getValue(String.class);
                            String userType = userSnapshot.child("type").getValue(String.class);
                            String userNum;
                            StringBuilder specialties = new StringBuilder();
                            if(userType.equals("Doctor")){
                                //Adds employee number
                                userNum = userSnapshot.child("employeeNum").getValue(String.class);

                                //Adds specialties
                                DataSnapshot specialtiesSnapshot = userSnapshot.child("specialties");
                                for (DataSnapshot specialtySnapshot : specialtiesSnapshot.getChildren()) {
                                    boolean specialized = specialtySnapshot.getValue(Boolean.class);
                                    String specialtyName = specialtySnapshot.getKey();

                                    if (specialized) {
                                        if (!specialties.toString().equals("")){
                                            specialties.append(", ");
                                        }
                                        if (Objects.equals(specialtyName, "familyMedicine")){
                                            specialties.append("Family Medicine");
                                        }
                                        else if(Objects.equals(specialtyName, "gynecology")){
                                            specialties.append("Gynecology");
                                        }
                                        else if(Objects.equals(specialtyName, "internalMedicine")){
                                            specialties.append("Internal Medicine");
                                        }
                                        else if(Objects.equals(specialtyName, "obstetrics")){
                                            specialties.append("Obstetrics");
                                        }
                                        else if (Objects.equals(specialtyName, "pediatrics")){
                                            specialties.append("Pediatrics");
                                        }
                                    }
                                }
                            }
                            else{
                                userNum = userSnapshot.child("healthCardNum").getValue(String.class);
                                specialties = new StringBuilder("N/A");
                            }



                            //Adds the user's info to the text boxes
                            TextView firstNameText = findViewById(R.id.firstNameField);
                            firstNameText.setText(firstName);

                            TextView lastNameText = findViewById(R.id.lastNameField);
                            lastNameText.setText(lastName);

                            TextView usernameText = findViewById(R.id.emailField);
                            usernameText.setText(username);

                            TextView phoneText = findViewById(R.id.phoneNumberField);
                            phoneText.setText(phone);

                            TextView addressText = findViewById(R.id.addressField);
                            addressText.setText(address);

                            TextView userTypeText = findViewById(R.id.userTypeField);
                            userTypeText.setText(userType);

                            TextView userNumText = findViewById(R.id.idField);
                            userNumText.setText(userNum);

                            TextView specialtiesText = findViewById(R.id.specialtiesField);
                            specialtiesText.setText(specialties.toString());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Error: " + databaseError.getMessage());
                }
            });
        }
    }

    private void changeStatus(String accountID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accounts");
        DatabaseReference deniedRequestsRef = FirebaseDatabase.getInstance().getReference("Requests/DeniedRequests");

        ArrayList<String> accountTypes = new ArrayList<>();
        accountTypes.add("Doctor");
        accountTypes.add("Patient");

        for (String accountType : accountTypes) {
            DatabaseReference account = databaseReference.child(accountType);
            account.orderByKey().equalTo(accountID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            //Changes the user's registrationStatus from pending
                            userSnapshot.getRef().child("registrationStatus").setValue("Approved");


                            //Removes info from the DeniedRequests part of the database
                            deniedRequestsRef.child(accountID).removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Error: " + databaseError.getMessage());
                }
            });
        }
    }







}