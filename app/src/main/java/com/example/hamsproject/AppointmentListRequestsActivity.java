package com.example.hamsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppointmentListRequestsActivity extends AppCompatActivity {

    Account userData;
    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;
    String appointmentId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_requested_apps);

        userData = (Account)getIntent().getSerializableExtra("userData");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button backButton = findViewById(R.id.backButton);
        Button acceptAllAppointmentsButton = findViewById(R.id.acceptAllAppointmentsButton);


        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentListRequestsActivity.this, DoctorPageActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });

        //Used to accept all requested appointments
        acceptAllAppointmentsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseReference requestedAppointments = FirebaseDatabase.getInstance().getReference("Appointments/RequestedAppointments");

                requestedAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot requestedAppointmentsSnapshot) {
                        if (requestedAppointmentsSnapshot.exists() && requestedAppointmentsSnapshot.hasChildren()) {
                            for (DataSnapshot appointmentSnapshot : requestedAppointmentsSnapshot.getChildren()) {
                                String appointmentId = appointmentSnapshot.getKey();
                                changeStatus(appointmentId, "Accepted");
                            }
                        }
                        else {
                            Log.d("ListOfRequestedAppointments", "No Appointments");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("ListOfRequestedAppointments", "Error: " + databaseError.getMessage());
                    }
                });
            }
        });

        //Sends the user to the info page for the appointment
        appointmentAdapter = new AppointmentAdapter(new ArrayList<>(), new AppointmentAdapter.OnAppointmentItemClickListener() {
            public void onAppointmentItemClick(Appointment appointment) {
                Log.d("appointmentID: ", appointment.getAppointmentKey());
                Intent intent = new Intent(AppointmentListRequestsActivity.this, AppointmentRequestInfoActivity.class);
                intent.putExtra("appointmentId", appointment.getAppointmentKey());
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        }, appointmentId);


        recyclerView.setAdapter(appointmentAdapter);

        DatabaseReference requestedAppointments = FirebaseDatabase.getInstance().getReference("Appointments/RequestedAppointments");

        requestedAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot requestedAppointmentsSnapshot) {
                List<Appointment> appointmentList = new ArrayList<>();

                if (requestedAppointmentsSnapshot.exists() && requestedAppointmentsSnapshot.hasChildren()) {
                    for (DataSnapshot appointmentSnapshot : requestedAppointmentsSnapshot.getChildren()) {
                        if(appointmentSnapshot != null) {
                            appointmentId = appointmentSnapshot.getKey();
                            Appointment appointment = appointmentSnapshot.getValue(Appointment.class);

                            String appointmentDoctorKey = appointmentSnapshot.child("doctorKey").getValue(String.class);

                            if(userData != null){
                                if (Objects.equals(userData.getKey(), appointmentDoctorKey)) {
                                    appointmentList.add(appointment);
                                }
                            }

                        }
                    }
                }
                else {
                    Log.d("ListOfAcceptedAppointments", "No appointments found for the current doctor");
                }
                appointmentAdapter.setAppointmentList(appointmentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ListOfRequestedAppointments", "Error: " + databaseError.getMessage());
            }
        });
    };


    private void changeStatus(String appointmentId, String status){
        DatabaseReference requestedAppointmentsRef = FirebaseDatabase.getInstance().getReference("Appointments/RequestedAppointments");
        DatabaseReference acceptedAppointmentsRef = FirebaseDatabase.getInstance().getReference("Appointments/AcceptedAppointments");
        DatabaseReference specificRequestedAppointmentRef = requestedAppointmentsRef.child(appointmentId);

        if (status == "Accepted"){
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
        else if (status == "Rejected"){
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
