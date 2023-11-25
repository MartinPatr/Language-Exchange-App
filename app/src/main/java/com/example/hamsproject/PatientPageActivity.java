package com.example.hamsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientPageActivity extends AppCompatActivity{
    Account userData;

    TextView userTypeDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page);

        Button logoutButton = findViewById(R.id.logoutButton);
        Button viewUpcomingAppsButton = findViewById(R.id.viewUpcomingAppsButton);
        Button viewPastAppointmentsButton = findViewById(R.id.viewPastAppsButton);
        Button bookAppButton = findViewById(R.id.bookAppButton);

        Intent getPreviousIntent = getIntent();
        userData = (Account)getPreviousIntent.getSerializableExtra("userData");

        userTypeDisplayed = (TextView) findViewById(R.id.yourRoleDisplayed);
        if (userData != null) {
            userTypeDisplayed.setText("You are logged in as " + userData.getType());
        }

        //Used to logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PatientPageActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        viewUpcomingAppsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PatientPageActivity.this, PatientUpcomingAppsActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });

        viewPastAppointmentsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PatientPageActivity.this, PatientPastAppsActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });

        bookAppButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PatientPageActivity.this, BookAppointmentActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });
}}
