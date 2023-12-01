package com.example.hamsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointmentPastInfoActivity extends AppCompatActivity{
    String appointmentId;
    String patientKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_past_app_info);

        Intent intent = getIntent();

        appointmentId = intent.getStringExtra("appointmentId");
        Account userData = (Account)getIntent().getSerializableExtra("userData");

        getUserInfo(appointmentId);

        Button backButton = findViewById(R.id.backButton);

        //Sends the user back to the previous page
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentPastInfoActivity.this, AppointmentListPastActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });
    }

    private void getUserInfo(String accountID) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments/PastAppointments").child(appointmentId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot appointmentSnapshot) {
                if (appointmentSnapshot.exists() && appointmentSnapshot.hasChildren()) {
                    patientKey = appointmentSnapshot.child("patientKey").getValue(String.class);
                    if (patientKey != null) {
                        getPatientInfo(patientKey);
                    }
                    else {
                        Log.e("AppointmentPastInfoActivity", "PatientKey is null");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AppointmentPastInfoActivity", "Database error: " + databaseError.getMessage());

            };
        });
    }

    private void getPatientInfo(String patientKey){
        Log.d("patientKey", "patientKey " + patientKey);

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Accounts/Patient");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (patientKey != null) {
                        DataSnapshot patientSnapshot = dataSnapshot.child(patientKey);

                        String firstName = patientSnapshot.child("firstName").getValue(String.class);
                        String lastName = patientSnapshot.child("lastName").getValue(String.class);
                        String username = patientSnapshot.child("username").getValue(String.class);
                        String phone = patientSnapshot.child("phone").getValue(String.class);
                        String address = patientSnapshot.child("address").getValue(String.class);
                        String healthCardNumber = patientSnapshot.child("healthCardNum").getValue(String.class);

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

                        TextView healthCardNumberText = findViewById(R.id.healthCardNumField);
                        healthCardNumberText.setText(healthCardNumber);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());

            }
    });
}}