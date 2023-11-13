package com.example.hamsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_request_info);

        Intent intent = getIntent();

        String appointmentKey = intent.getStringExtra("appointmentKey");


        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AppointmentInfoActivity.this, DoctorPageActivity.class);
                intent.putExtra("userData",appointmentKey);
                startActivity(intent);
            }
        });
}}
