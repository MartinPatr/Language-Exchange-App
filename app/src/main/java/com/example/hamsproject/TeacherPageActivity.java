package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;


public class TeacherPageActivity extends AppCompatActivity {
    Account userData;

    TextView userTypeDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);

        Button logoutButton = findViewById(R.id.logoutButton);
        Button viewShiftsButton = findViewById(R.id.viewShiftsButton);
        Button viewAcceptedAppointmentsButton = findViewById(R.id.viewAcceptedAppointmentsButton);
        Button viewRequestedAppointmentsButton = findViewById(R.id.viewRequestedAppointmentsButton);
        Button viewPastAppointmentsButton = findViewById(R.id.viewPastAppointmentsButton);


        Intent getPreviousIntent = getIntent();

        userData = (Account)getPreviousIntent.getSerializableExtra("userData");
        userTypeDisplayed = (TextView) findViewById(R.id.yourRoleDisplayed);

        if (userData != null) {
            userTypeDisplayed.setText("You are logged in as " + userData.getType());
        }

        //Used to logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TeacherPageActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        //Used to go to the list of shifts
        viewShiftsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(TeacherPageActivity.this, ListOfShiftsActivity.class);
                intent.putExtra("userData",userData);
                startActivity(intent);
            }
        });

        //Used to go to the requested appointments
        viewRequestedAppointmentsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("TeacherPageActivity", "ViewAppsButton");
                Intent intent = new Intent(TeacherPageActivity.this, AppointmentListRequestsActivity.class);
                intent.putExtra("userData",userData);

                startActivity(intent);


            }
        });

        //Used to go to the accepted appointments
        viewAcceptedAppointmentsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("TeacherPageActivity", "ViewAppsButton");
                Intent intent = new Intent(TeacherPageActivity.this, AppointmentListAcceptedActivity.class);
                intent.putExtra("userData",userData);

                startActivity(intent);


            }
        });

        //Used to go to the past appointments
        viewPastAppointmentsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("TeacherPageActivity", "ViewAppsButton");
                Intent intent = new Intent(TeacherPageActivity.this, AppointmentListPastActivity.class);
                intent.putExtra("userData",userData);

                startActivity(intent);


            }
        });
    }

}