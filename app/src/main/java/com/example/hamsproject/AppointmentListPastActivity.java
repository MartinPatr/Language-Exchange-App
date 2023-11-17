package com.example.hamsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppointmentListPastActivity extends AppCompatActivity {

    Account userData;
    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;
    String appointmentId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_past_apps);

        userData = (Account)getIntent().getSerializableExtra("userData");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button backButton = findViewById(R.id.backButton);

        //Sends user back
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentListPastActivity.this, DoctorPageActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });

        //Sends user to info page for the appointment
        appointmentAdapter = new AppointmentAdapter(new ArrayList<>(), new AppointmentAdapter.OnAppointmentItemClickListener(){
            public void onAppointmentItemClick(Appointment appointment) {
                Intent intent = new Intent(AppointmentListPastActivity.this, AppointmentPastInfoActivity.class);
                intent.putExtra("appointmentId", appointment.getAppointmentKey());
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        }, appointmentId);

        recyclerView.setAdapter(appointmentAdapter);

        DatabaseReference pastAppointments = FirebaseDatabase.getInstance().getReference("Appointments/PastAppointments");

        pastAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot requestedAppointmentsSnapshot) {
                List<Appointment> appointmentList = new ArrayList<>();

                if (requestedAppointmentsSnapshot.exists() && requestedAppointmentsSnapshot.hasChildren()) {
                    for (DataSnapshot appointmentSnapshot : requestedAppointmentsSnapshot.getChildren()) {
                        appointmentId = appointmentSnapshot.getKey();
                        Appointment appointment = appointmentSnapshot.getValue(Appointment.class);

                        String appointmentDoctorKey = appointmentSnapshot.child("doctorKey").getValue(String.class);

                        if (userData != null && Objects.equals(userData.getKey(), appointmentDoctorKey)) {
                            appointmentList.add(appointment);
                        }
                    }
                }
                else {
                    Log.d("ListOfPastAppointments", "No appointments");
                }
                appointmentAdapter.setAppointmentList(appointmentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ListOfRequestedAppointments", "Database error: " + databaseError.getMessage());
            }
        });
    }
}