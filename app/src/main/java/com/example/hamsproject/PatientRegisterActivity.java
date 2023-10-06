package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import com.example.hamsproject.ValidationUtils;


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
                Map<String, String> patientInfo = getPatientInfo();
                if(ValidationUtils.isValidated(PatientRegisterActivity.this,patientInfo)){
                    Intent intent = new Intent(PatientRegisterActivity.this, WelcomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /* 
     * This method gets all the patient information from the fields
     * @return Map<String, String>: Map that contains all the patient information
     */
    private Map<String, String> getPatientInfo(){
        Map<String, String> patientInfo = new HashMap<>();
        patientInfo.put("FirstName", patientNameField.getText().toString());
        patientInfo.put("LastName", patientLastNameField.getText().toString());
        patientInfo.put("Email", patientEmailField.getText().toString());
        patientInfo.put("Password", patientPasswordField.getText().toString());
        patientInfo.put("PhoneNumber", patientPhoneField.getText().toString());
        patientInfo.put("Address", patientAddressField.getText().toString());
        patientInfo.put("HealthCard", editHealthCardNumber.getText().toString());
        return patientInfo;
    }
}
