package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;

public class PatientRegisterActivity extends AppCompatActivity {

    private EditText patientNameField;
    private EditText patientLastNameField;
    private EditText patientEmailField;
    private EditText patientPasswordField;
    private EditText patientPhoneField;
    private EditText patientAddressField;
    private EditText editHealthCardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        Button registerAsPatient = findViewById(R.id.logoutButton);

        patientNameField = findViewById(R.id.patientNameField);
        patientLastNameField = findViewById(R.id.patientLastNameField);
        patientEmailField = findViewById(R.id.patientEmailField);
        patientPasswordField = findViewById(R.id.patientPasswordField);
        patientPhoneField = findViewById(R.id.patientPhoneField);
        patientAddressField = findViewById(R.id.patientAddressField);
        editHealthCardNumber = findViewById(R.id.editHealthCardNumber);


        registerAsPatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(isValidated()){
                    Intent intent = new Intent(PatientRegisterActivity.this, WelcomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isValidated(){
        String patientFirstName = patientNameField.getText().toString();
        String patientLastName = patientLastNameField.getText().toString();
        String patientEmail = patientEmailField.getText().toString();
        String patientPassword = patientPasswordField.getText().toString();
        String patientPhoneNumber = patientPhoneField.getText().toString();
        String patientAddress = patientAddressField.getText().toString();
        String patientHealthCard = editHealthCardNumber.getText().toString();

        return (!patientFirstName.isEmpty()
                && !patientLastName.isEmpty()
                && !patientEmail.isEmpty()
                && !patientPassword.isEmpty()
                && !patientPhoneNumber.isEmpty()
                && !patientAddress.isEmpty()
                && !patientHealthCard.isEmpty()
        );
    }
}