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

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_requested);

        userData = (Account)getIntent().getSerializableExtra("userData");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button backButton = findViewById(R.id.backButton);

        //===================================================================================================================

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentListRequestsActivity.this, DoctorPageActivity.class);
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

                        String appointmentDoctorKey = appointmentSnapshot.child("doctorKey").getValue(String.class);

                        Log.d("Test", appointment.getPatientName());
                        Log.d("ListOfAppointmentsActivity", "First key in PendingAppointments: " + appointmentId);

                        if(Objects.equals(userData.getKey(), appointmentDoctorKey)){
                            appointmentList.add(appointment);
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
                Log.e("ListOfRequestedAppointments", "Database error: " + databaseError.getMessage());
            }
        });
    };
}
