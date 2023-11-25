package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientUpcomingAppsActivity extends AppCompatActivity {

    Account userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_upcoming_apps);

        Intent getPreviousIntent = getIntent();
        userData = (Account)getPreviousIntent.getSerializableExtra("userData");

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PatientUpcomingAppsActivity.this, PatientPageActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });
    }
}