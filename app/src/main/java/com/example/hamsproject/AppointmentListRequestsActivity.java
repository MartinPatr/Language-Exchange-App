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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListRequestsActivity extends AppCompatActivity {

    Account userData;
    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_requests);

        userData = (Account)getIntent().getSerializableExtra("userData");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button acceptedAppointmentsButton = findViewById(R.id.acceptedAppointmentsButton);
        Button logoutButton = findViewById(R.id.logOutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentListRequestsActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        acceptedAppointmentsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentListRequestsActivity.this, AppointmentListAcceptedActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });
        appointmentAdapter = new AppointmentAdapter(new ArrayList<>(), new AppointmentAdapter.OnAppointmentItemClickListener(){

            @Override
            public void onAppointmentItemClick(Appointment appointment) {}

        });

        recyclerView.setAdapter(appointmentAdapter);
        //========================================================================

        DatabaseReference pendingAppointmentsRef = FirebaseDatabase.getInstance().getReference("Appointments/PendingAppointments");

        pendingAppointmentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot pendingAppointmentsSnapshot) {
                List<Appointment> appointmentList = new ArrayList<>();
                if (pendingAppointmentsSnapshot.exists() && pendingAppointmentsSnapshot.hasChildren()) {
                    for (DataSnapshot appointmentSnapshot : pendingAppointmentsSnapshot.getChildren()) {
                        String appointmentId = appointmentSnapshot.getKey();
                        Appointment appointment = appointmentSnapshot.getValue(Appointment.class);
                        Log.d("Test", appointment.getPatientName());
                        Log.d("ListOfAppointmentsActivity", "First key in PendingAppointments: " + appointmentId);
                        appointmentList.add(appointment);
                    }
                }
                else {
                    Log.d("ListOfShiftsActivity", "No shifts found for the current doctor");
                }
                appointmentAdapter.setAppointmentList(appointmentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ListOfShiftsActivity", "Database error: " + databaseError.getMessage());
            }
        });
    };
}



