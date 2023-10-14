package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class UserSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        Button doctorButton = findViewById(R.id.doctorButton);
        Button patientButton = findViewById(R.id.patientButton);
        doctorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(UserSelectionActivity.this, DoctorRegisterActivity.class);
                startActivity(intent);
            }
        });
        patientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(UserSelectionActivity.this, PatientRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}