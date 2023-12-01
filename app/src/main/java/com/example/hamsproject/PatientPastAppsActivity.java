package com.example.hamsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatientPastAppsActivity extends AppCompatActivity {

    Account userData;

    private RecyclerView recyclerView;
    private String appointmentIdTemp;
    private AppointmentAdapter appointmentAdapter;
    ArrayList<AppointmentAdapter> appointmentAdapters = new ArrayList<>();
    List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_past_apps);

        userData = (Account)getIntent().getSerializableExtra("userData");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //===================================================================================================================

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PatientPastAppsActivity.this, PatientPageActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });

        //===================================================================================================================

        DatabaseReference pastAppsRef = FirebaseDatabase.getInstance().getReference("Appointments/PastAppointments");

        pastAppsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot requestedAppointmentsSnapshot) {
                appointmentList = new ArrayList<>();

                if (userData == null) {
                    Log.e("PatientPastAppsActivity", "userData is null");
                    return;
                }

                if (requestedAppointmentsSnapshot.exists() && requestedAppointmentsSnapshot.hasChildren()) {
                    for (DataSnapshot appointmentSnapshot : requestedAppointmentsSnapshot.getChildren()) {
                        if (appointmentSnapshot != null) {
                            String appointmentId = appointmentSnapshot.getKey();
                            appointmentIdTemp = appointmentId;
                            Appointment appointment = appointmentSnapshot.getValue(Appointment.class);

                            String appointmentPatientKey = appointmentSnapshot.child("patientKey").getValue(String.class);

                            if (Objects.equals(userData.getKey(), appointmentPatientKey)) {
                                appointmentList.add(appointment);
                            }
                        }
                    }
                    appointmentAdapter = new AppointmentAdapter(new ArrayList<>(), new AppointmentAdapter.OnAppointmentItemClickListener() {
                        @Override
                        public void onAppointmentItemClick(Appointment appointment) {
                            Log.d("PatientPastAppsActivity", "AppointmentId in list: " + appointmentIdTemp);

                            Intent intent = new Intent(PatientPastAppsActivity.this, PatientPastAppInfo.class);
                            intent.putExtra("appointmentId", appointment.getAppointmentKey());
                            intent.putExtra("userData", userData);
                            startActivity(intent);
                        }
                    }, appointmentIdTemp);

                    recyclerView.setAdapter(appointmentAdapter);
                    appointmentAdapters.add(appointmentAdapter);
                }
                else {
                    Log.d("ListOfAcceptedAppointments", "No appointments found for the current doctor");
                }

                if (appointmentAdapter != null) {
                    appointmentAdapter.setAppointmentList(appointmentList);
                    appointmentAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ListOfAcceptedAppointments", "Database error: " + databaseError.getMessage());
            }
        });
    }
}