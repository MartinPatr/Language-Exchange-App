package com.example.hamsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.example.hamsproject.PatientShiftAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookAppointmentActivity extends AppCompatActivity implements PatientShiftAdapter.OnItemClickListener {

    Account userData;
    private RecyclerView recyclerView;
    private List<Shift> availableShifts = new ArrayList<>();
    PatientShiftAdapter adapter = new PatientShiftAdapter(availableShifts, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent getPreviousIntent = getIntent();
        userData = (Account) getPreviousIntent.getSerializableExtra("userData");

        Button backButton = findViewById(R.id.backButton);
        Spinner specialtyMenu = findViewById(R.id.spinner1);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        //Adds specialties to the dropdown menu
        String[] items = new String[]{"Select Specialty", "familyMedicine", "internalMedicine", "pediatrics", "obstetrics", "gynecology"};
        String[] itemsForDisplay = new String[]{"Select Specialty", "Family Medicine", "Internal Medicine", "Pediatrics", "Obstetrics", "Gynecology"};

        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsForDisplay);
        specialtyMenu.setAdapter(menuAdapter);
        specialtyMenu.setSelection(0, false);

        //Sets up the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BookAppointmentActivity.this, PatientPageActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });


        specialtyMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    availableShifts.clear();

                    String selectedSpecialty = items[position];
                    DatabaseReference availableAppointments = FirebaseDatabase.getInstance().getReference("Appointments").child("AvailableAppointments");

                    availableAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                                Appointment appointment = appointmentSnapshot.getValue(Appointment.class);
                                if (appointment != null) {
                                    String doctorKey = appointment.getDoctorKey();

                                    DatabaseReference doctors = FirebaseDatabase.getInstance().getReference("Accounts").child("Doctor").child(doctorKey);

                                    doctors.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot doctorSnapshot) {

                                            //Checks for the selected specialty
                                            if(doctorSnapshot.child("specialties").child(selectedSpecialty).getValue(Boolean.class) != null && doctorSnapshot.child("specialties").child(selectedSpecialty).getValue(Boolean.class)) {

                                                Shift shift = new Shift(appointment.getDate(), appointment.getStartHour(), appointment.getStartMinute(), appointment.getEndHour(), appointment.getEndMinute(), appointment.getDoctorKey(), appointment.getDoctorName());
                                                shift.setID(appointmentSnapshot.getKey());

                                                availableShifts.add(shift);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Log.e("BookAppointmentActivity", "Database error: " + databaseError.getMessage());
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("BookAppointmentActivity", "Error: " + databaseError.getMessage());
                        }
                    });
                }
                //If the user tries to press "Select Specialty" in the drop down
                else if (position == 0) {
                    availableShifts.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView parentView){
                availableShifts.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(Shift shift) {
    }

    public interface DoctorNameCallback {
        void onDoctorNameReceived(String doctorName);
    }

    public void getDoctorName(String doctorKey, DoctorNameCallback callback) {
        DatabaseReference doctors = FirebaseDatabase.getInstance().getReference("Accounts").child("Doctor").child(doctorKey);

        doctors.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot doctorSnapshot) {
                String firstName = doctorSnapshot.child("firstName").getValue(String.class);
                String lastName = doctorSnapshot.child("lastName").getValue(String.class);
                String doctorName = firstName + " " + lastName;

                // Call the callback with the result
                callback.onDoctorNameReceived(doctorName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("BookAppointmentActivity", "Database error: " + databaseError.getMessage());

                // Call the callback with a default or error value
                callback.onDoctorNameReceived("Error");
            }
        });
    }

    public void onBookButtonClick(Shift shift) {
        //Makes an appointment to add to RequestedAppointments in the database
        Appointment appointment = new Appointment();
        appointment.setAppointmentKey(shift.getID());
        appointment.setPatientName(userData.getFirstName() + " " + userData.getLastName());
        appointment.setDate(shift.getDate());
        appointment.setStartHour(shift.getStartHour());
        appointment.setStartMinute(shift.getStartMinute());
        appointment.setEndHour(shift.getEndHour());
        appointment.setEndMinute(shift.getEndMinute());
        appointment.setPatientKey(userData.getKey());
        appointment.setDoctorKey(shift.getDoctorKey());

        Log.d("id", shift.getID());

        DatabaseReference appointments = FirebaseDatabase.getInstance().getReference("Appointments");

        //Moves appointment to RequestedAppointments
        DatabaseReference requestedAppointmentSpot = appointments.child("RequestedAppointments").child(shift.getID());
        requestedAppointmentSpot.setValue(appointment);

        //Deletes shift from AvailableAppointments
        DatabaseReference availableAppointments = appointments.child("AvailableAppointments").child(shift.getID());
        availableAppointments.removeValue();

        //Updates recycler
        availableShifts.remove(shift);
        adapter.notifyDataSetChanged();

        Toast.makeText(BookAppointmentActivity.this, "Appointment Booked!", Toast.LENGTH_SHORT).show();
    }


}