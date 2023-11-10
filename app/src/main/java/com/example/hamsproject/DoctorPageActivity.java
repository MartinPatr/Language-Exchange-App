package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DoctorPageActivity extends AppCompatActivity {
    Account userData;

    TextView userTypeDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);

        Button logoutButton = findViewById(R.id.logoutButton);
        Button acceptAllAppointmentsButton = findViewById(R.id.acceptAllAppointmentsButton);
        Button viewShiftsButton = findViewById(R.id.viewShiftsButton);


        Intent getPreviousIntent = getIntent();

        userData = (Account)getPreviousIntent.getSerializableExtra("userData");
        userTypeDisplayed = (TextView) findViewById(R.id.yourRoleDisplayed);
        userTypeDisplayed.setText("You are logged in as " + userData.getType());


        if (((Doctor) userData).getAcceptAllAppointments() == true ){
            acceptAllAppointmentsButton.setBackgroundColor(Color.parseColor("#FF338301"));
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(DoctorPageActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        viewShiftsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(DoctorPageActivity.this, ListOfShiftsActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });
        acceptAllAppointmentsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (((Doctor) userData).getAcceptAllAppointments() == false ){
                    updateSetting(true);
                    acceptAllAppointmentsButton.setBackgroundColor(Color.parseColor("#FF338301"));
                    ((Doctor) userData).setAcceptAllAppointments(true);
                } else{
                    updateSetting(false);
                    acceptAllAppointmentsButton.setBackgroundColor(Color.parseColor("#9F0101"));
                    ((Doctor) userData).setAcceptAllAppointments(false);
                }
            }
        });    
    }
    public void updateSetting( boolean acceptAllAppointments){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accounts");
        DatabaseReference account = databaseReference.child("Doctor");
        account.orderByKey().equalTo(userData.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        userSnapshot.getRef().child("acceptAllAppointments").setValue(acceptAllAppointments);
                    }
                }
            }
    
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });    
    }
}