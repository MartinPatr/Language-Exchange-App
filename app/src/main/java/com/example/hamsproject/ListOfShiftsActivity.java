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
        shiftAdapter = new ShiftAdapter(new ArrayList<>(), new ShiftAdapter.OnShiftItemClickListener() {
            @Override
            public void onShiftItemClick(Shift shift) {}

            @Override
            public void onShiftItemDeleteClick(Shift shift) {
                deleteShift(shift);
            }
        });

        recyclerView.setAdapter(shiftAdapter);

        Button createShiftButton = findViewById(R.id.createShiftButton);

        createShiftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ListOfShiftsActivity.this, ShiftCreationActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });

        //////////////////////////////////////////////////////////////////////////////

        DatabaseReference shiftsRef = FirebaseDatabase.getInstance()
                .getReference("Accounts/Doctor")
                .child(userData.getKey())
                .child("shifts"); // Assuming "shifts" is the key for shifts in the database

        shiftsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot shiftsSnapshot) {
                List<Shift> doctorShifts = new ArrayList<>();

                // Check if shiftsSnapshot has children
                if (shiftsSnapshot.exists() && shiftsSnapshot.hasChildren()) {
                    for (DataSnapshot shiftSnapshot : shiftsSnapshot.getChildren()) {
                        // Assuming Shift has a constructor that takes a DataSnapshot
                        Shift shift = shiftSnapshot.getValue(Shift.class);

                        Log.d("ListOfShiftsActivity", "Shift info: " + shift.getDate());

                        doctorShifts.add(shift);
                    }
                } else {
                    // Handle the case where the doctor has no shifts
                    Log.d("ListOfShiftsActivity", "No shifts found for the current doctor");
                }

                // Update the adapter with the retrieved shifts
                shiftAdapter.setShiftList(doctorShifts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("ListOfShiftsActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }


    private void deleteShift(Shift shift) {
        DatabaseReference shiftsRef = FirebaseDatabase.getInstance().getReference("Accounts/Doctor").child(userData.getKey()).child("shifts");

        shiftsRef.child(shift.getID()).removeValue().addOnSuccessListener(aVoid -> {});
    }

}