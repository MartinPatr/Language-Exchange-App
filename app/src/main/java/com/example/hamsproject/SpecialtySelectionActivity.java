package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class SpecialtySelectionActivity extends AppCompatActivity {

    private CheckBox familyMedicineSpecialty;
    private CheckBox internalMedicineSpecialty;
    private CheckBox pediatricsSpecialty;
    private CheckBox obstetricsSpecialty;
    private CheckBox gynecologySpecialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_selection);

        Button registerAsDoctor = findViewById(R.id.registerAsDoctor);
        familyMedicineSpecialty = findViewById(R.id.familyMedicineSpecialty);
        internalMedicineSpecialty = findViewById(R.id.internalMedicineSpecialty);
        pediatricsSpecialty = findViewById(R.id.pediatricsSpecialty);
        obstetricsSpecialty = findViewById(R.id.obstetricsSpecialty);
        gynecologySpecialty = findViewById(R.id.gynecologySpecialty);

        registerAsDoctor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(isValidated()){
                    Intent intent = new Intent(SpecialtySelectionActivity.this, WelcomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean isValidated(){

        return (familyMedicineSpecialty.isChecked()
                || internalMedicineSpecialty.isChecked()
                || pediatricsSpecialty.isChecked()
                || obstetricsSpecialty.isChecked()
                || gynecologySpecialty.isChecked()
        );
    }
}