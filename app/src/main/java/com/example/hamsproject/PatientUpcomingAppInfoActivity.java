package com.example.hamsproject;

import androidx.annotation.NonNull;
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

public class PatientUpcomingAppInfoActivity extends AppCompatActivity {
    String appointmentId;
    String doctorKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_upcoming_app_info);

        Intent intent = getIntent();

        appointmentId = intent.getStringExtra("appointmentId");
        Account userData = (Account)getIntent().getSerializableExtra("userData");

        getUserInfo(appointmentId);

        //===================================================================================================================
        //Sends the user back to the previous page

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PatientUpcomingAppInfoActivity.this, PatientUpcomingAppsActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });
        //===================================================================================================================
    }


    // Gets the doctor key given the appointment ID
    private void getUserInfo(String appointmentId) {







        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments/AcceptedAppointments").child(appointmentId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot appointmentSnapshot) {
                if (appointmentSnapshot.exists() && appointmentSnapshot.hasChildren()) {
                    doctorKey = appointmentSnapshot.child("doctorKey").getValue(String.class);
                    if (doctorKey != null) {
                        Log.d("Zelo", doctorKey);
                        getPatientInfo(doctorKey);
                    }
                    else {
                        Log.e("AppointmentPastInfoActivity", "doctorKey is null");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AppointmentPastInfoActivity", "Database error: " + databaseError.getMessage());

            };
        });
    }

    // Gets the doctor's details given the doctor key found previously.
    private void getPatientInfo(String doctorKey){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accounts/Doctor");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (doctorKey != null) {
                        DataSnapshot doctorSnapshot = dataSnapshot.child(doctorKey);

                        String firstName = doctorSnapshot.child("firstName").getValue(String.class);
                        String lastName = doctorSnapshot.child("lastName").getValue(String.class);

                        TextView firstNameField = findViewById(R.id.firstNameField);
                        TextView lastNameField = findViewById(R.id.lastNameField);

                        firstNameField.setText(firstName);
                        lastNameField.setText(lastName);
                        getAppointmentInfo();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }

    private void getAppointmentInfo(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments/AcceptedAppointments").child(appointmentId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (appointmentId != null) {
                        String date = dataSnapshot.child("date").getValue(String.class);

                        String startHour = String.valueOf(dataSnapshot.child("startHour").getValue(Long.class));
                        String startMinute = String.valueOf(dataSnapshot.child("startMinute").getValue(Long.class));
                        String endHour = String.valueOf(dataSnapshot.child("endHour").getValue(Long.class));
                        String endMinute = String.valueOf(dataSnapshot.child("endMinute").getValue(Long.class));


                        String time = startHour + ":" + startMinute + " - " + endHour + ":" + endMinute;


                        TextView dateField = findViewById(R.id.dateField);
                        dateField.setText(date);

                        TextView timeField = findViewById(R.id.timeField);
                        timeField.setText(time);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());

            }
        });
    }

}