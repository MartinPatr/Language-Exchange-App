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

public class AppointmentRequestInfoActivity extends AppCompatActivity {
    String appointmentId;
    String patientKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_requested_app_info);

        Intent intent = getIntent();

        appointmentId = intent.getStringExtra("appointmentId");

        if (appointmentId == null){
            Log.d("App ID" , "appointmentID is null");
        }
        Account userData = (Account)getIntent().getSerializableExtra("userData");

        getUserInfo(appointmentId);

        Button backButton = findViewById(R.id.backButton);
        Button acceptButton = findViewById(R.id.acceptButton);
        Button rejectButton = findViewById(R.id.rejectButton);

        //Takes the user back to the previous page
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentRequestInfoActivity.this, AppointmentListRequestsActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });

        //Accepts the appointment
        acceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeStatus(appointmentId, "Accepted");
                Intent intent = new Intent(AppointmentRequestInfoActivity.this, AppointmentListRequestsActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });

        //Rejects the appointment
        rejectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeStatus(appointmentId, "Rejected");
                Intent intent = new Intent(AppointmentRequestInfoActivity.this, AppointmentListRequestsActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });
}
    private void getUserInfo(String accountID) {
        if (appointmentId != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments/RequestedAppointments").child(appointmentId);
            databaseReference.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot appointmentSnapshot) {
                    if (appointmentSnapshot.exists() && appointmentSnapshot.hasChildren()) {
                        patientKey = appointmentSnapshot.child("patientKey").getValue(String.class);

                        if (patientKey != null) {
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Accounts/Patient");
                            databaseReference2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
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

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.e("Firebase", "Error: " + databaseError.getMessage());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("AppointmentRequestInfoActivity", "Database error: " + databaseError.getMessage());
                }
            });
        }
    }

    private void changeStatus(String appointmentId, String status){
        DatabaseReference requestedAppointmentsRef = FirebaseDatabase.getInstance().getReference("Appointments/RequestedAppointments");
        DatabaseReference acceptedAppointmentsRef = FirebaseDatabase.getInstance().getReference("Appointments/AcceptedAppointments");
        DatabaseReference specificRequestedAppointmentRef = requestedAppointmentsRef.child(appointmentId);

        if (status.equals("Accepted")){

            specificRequestedAppointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                            Appointment appointment = dataSnapshot.getValue(Appointment.class);
                            appointment.setAppointmentStatus("Accepted");

                            acceptedAppointmentsRef.child(appointmentId).setValue(appointment);

                            specificRequestedAppointmentRef.removeValue();
                        }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "Error: " + databaseError.getMessage());

                }
            });
        }
        else if (status.equals("Rejected")){
            specificRequestedAppointmentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        specificRequestedAppointmentRef.removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "Error: " + databaseError.getMessage());

                }
            });
        }

    }
}

