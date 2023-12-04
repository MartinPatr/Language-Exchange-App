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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfShiftsActivity extends AppCompatActivity {

    Account userData;
    private RecyclerView recyclerView;
    private ShiftAdapter shiftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_shifts);

        userData = (Account)getIntent().getSerializableExtra("userData");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shiftAdapter = new ShiftAdapter(new ArrayList<>(), new ShiftAdapter.OnShiftItemClickListener(){
            @Override
            public void onShiftItemClick(Shift shift) {}

            @Override
            public void onShiftItemDeleteClick(Shift shift) {
                deleteShift(shift);
            }
        });

        recyclerView.setAdapter(shiftAdapter);

        Button createShiftButton = findViewById(R.id.createShiftButton);

        //Sends the user to the shift creation page
        createShiftButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ListOfShiftsActivity.this, ShiftCreationActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.backButton);

        //Sends user back to main doctor page.
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ListOfShiftsActivity.this, DoctorPageActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });

        DatabaseReference shifts = FirebaseDatabase.getInstance().getReference("Accounts/Doctor").child(userData.getKey()).child("shifts");

        shifts.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot shiftsSnapshot){
                List<Shift> doctorShifts = new ArrayList<>();

                if (shiftsSnapshot.exists() && shiftsSnapshot.hasChildren()){
                    for (DataSnapshot shiftSnapshot : shiftsSnapshot.getChildren()){
                        Shift shift = shiftSnapshot.getValue(Shift.class);
                        Log.d("ListOfShiftsActivity", "Shift info: " + shift.getDate());
                        doctorShifts.add(shift);
                    }
                }
                else{
                    Log.d("ListOfShiftsActivity", "No Shifts");
                }
                shiftAdapter.setShiftList(doctorShifts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ListOfShiftsActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void deleteShift(Shift shift) {

        DatabaseReference shiftsRef = FirebaseDatabase.getInstance()
                .getReference("Accounts/Doctor")
                .child(userData.getKey())
                .child("shifts");

        shiftsRef.child(shift.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String doctorKey = userData.getKey();

                    int shiftStartHour = dataSnapshot.child("startHour").getValue(Integer.class);
                    int shiftStartMinute = dataSnapshot.child("startMinute").getValue(Integer.class);
                    int shiftEndHour = dataSnapshot.child("endHour").getValue(Integer.class);
                    int shiftEndMinute = dataSnapshot.child("endMinute").getValue(Integer.class);
                    String shiftDate = dataSnapshot.child("date").getValue(String.class);


                    DatabaseReference appointmentsRef = FirebaseDatabase.getInstance()
                            .getReference("Appointments")
                            .child("AcceptedAppointments");

                    appointmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot appointmentsSnapshot) {
                            boolean canDelete = true;
                            for (DataSnapshot appointmentSnapshot : appointmentsSnapshot.getChildren()) {
                                String appointmentDoctorKey = appointmentSnapshot.child("doctorKey").getValue(String.class);
                                int appointmentStartHour = appointmentSnapshot.child("startHour").getValue(Integer.class);
                                int appointmentStartMinute = appointmentSnapshot.child("startMinute").getValue(Integer.class);
                                int appointmentEndHour = appointmentSnapshot.child("endHour").getValue(Integer.class);
                                int appointmentEndMinute = appointmentSnapshot.child("endMinute").getValue(Integer.class);
                                String appointmentDate = appointmentSnapshot.child("date").getValue(String.class);


                                if (doctorKey.equals(appointmentDoctorKey)) {
                                    // Check if appointment conflicts with the shift that dr wants to del
                                    if (shiftAppointmentConflictCheck(shiftStartHour, shiftStartMinute, shiftEndHour, shiftEndMinute, shiftDate,
                                            appointmentStartHour, appointmentStartMinute, appointmentEndHour, appointmentEndMinute, appointmentDate)) {
                                        // Appointment found within shift time and date -> prevent deletion
                                        canDelete = false;
                                        Toast.makeText(ListOfShiftsActivity.this, "You have an appointment already booked during this shift.", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }

                            if (canDelete) {
                                // No conflicting appointments found -> delete shift
                                shiftsRef.child(shift.getID()).removeValue()
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(ListOfShiftsActivity.this, "Shift deleted.", Toast.LENGTH_SHORT).show();

                                        })
                                        .addOnFailureListener(e -> {

                                            Log.e("ListOfShiftsActivity", "Error deleting shift: " + e.getMessage());
                                        });
                            }
                            else {
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Log.e("ListOfShiftsActivity", "Database error: " + databaseError.getMessage());
                        }
                    });
                }
                else {

                    Log.d("ListOfShiftsActivity", "Shift doesn't exist or already deleted");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ListOfShiftsActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }

    private boolean shiftAppointmentConflictCheck(int shiftStartHour, int shiftStartMinute, int shiftEndHour, int shiftEndMinute,
                                                  String shiftDate,
                                                  int appointmentStartHour, int appointmentStartMinute, int appointmentEndHour, int appointmentEndMinute,
                                                  String appointmentDate) {

        String[] shiftDateParts = shiftDate.split("/");
        int shiftDateMonth = Integer.parseInt(shiftDateParts[0]);
        int shiftDateDay = Integer.parseInt(shiftDateParts[1]);
        int shiftDateYear = Integer.parseInt(shiftDateParts[2]);

        String[] appointmentDateParts = appointmentDate.split("/");
        int appointmentDateMonth = Integer.parseInt(appointmentDateParts[0]);
        int appointmentDateDay = Integer.parseInt(appointmentDateParts[1]);
        int appointmentDateYear = Integer.parseInt(appointmentDateParts[2]);


        if ((shiftStartHour == appointmentStartHour && shiftStartMinute == appointmentStartMinute && shiftDateYear == appointmentDateYear && shiftDateMonth == appointmentDateMonth && shiftDateDay == appointmentDateDay) ||
                (shiftEndHour == appointmentEndHour && shiftEndMinute == appointmentEndMinute && shiftDateYear == appointmentDateYear && shiftDateMonth == appointmentDateMonth && shiftDateDay == appointmentDateDay)) {
            return true; // conflicting appointment
        }

        int appointmentStart = appointmentStartHour * 60 + appointmentStartMinute;
        int appointmentEnd = appointmentEndHour * 60 + appointmentEndMinute;
        int shiftStart = shiftStartHour * 60 + shiftStartMinute;
        int shiftEnd = shiftEndHour * 60 + shiftEndMinute;


        boolean isConflict = !(appointmentEnd <= shiftStart || appointmentStart >= shiftEnd) &&
                (shiftDateYear == appointmentDateYear && shiftDateMonth == appointmentDateMonth && shiftDateDay == appointmentDateDay);

        return isConflict;
    }

}