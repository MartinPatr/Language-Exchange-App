package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class DoctorRegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        Button goToSpecialties = findViewById(R.id.goToSpecialties);
        goToSpecialties.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(DoctorRegisterActivity.this, SpecialtySelectionActivity.class);
                startActivity(intent);
            }
        });
    }
}