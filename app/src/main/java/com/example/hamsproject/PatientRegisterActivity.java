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
                if(isValidated(patientInfo)){
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
        patientInfo.put("patientFirstName", patientNameField.getText().toString());
        patientInfo.put("patientLastName", patientLastNameField.getText().toString());
        patientInfo.put("patientEmail", patientEmailField.getText().toString());
        patientInfo.put("patientPassword", patientPasswordField.getText().toString());
        patientInfo.put("patientPhoneNumber", patientPhoneField.getText().toString());
        patientInfo.put("patientAddress", patientAddressField.getText().toString());
        patientInfo.put("patientHealthCard", editHealthCardNumber.getText().toString());
        return patientInfo;
    }


    /* 
     * This method checks if all the fields are filled in and valid
     * @param patientInfo: Map<String, String> that contains all the patient information
     * @return boolean: true if all the fields are filled in, false otherwise
     */
    private boolean isValidated(Map<String, String> patientInfo){
        // Iterate through the Map
        for (String key : patientInfo.keySet()) {
            // This checks if the field is empty
            if (patientInfo.get(key).isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill the following fields", Toast.LENGTH_SHORT).show();
                return false;
            }
            // This checks if the email is valid
            else if (key.equals("patientEmail") && !patientInfo.get(key).contains("@") && !patientInfo.get(key).contains(".")) {
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
