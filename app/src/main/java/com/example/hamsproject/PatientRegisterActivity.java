package com.example.hamsproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientRegisterActivity extends AppCompatActivity {
    // Firebase variable declarations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //=============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Patient");

        // Retrieving TextView and Button Objects.
        Button registerAsPatient = findViewById(R.id.logoutButton);

        registerAsPatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Map<String, String> patientInfo = getPatientInfo();
                if(ValidationUtils.isValidated(PatientRegisterActivity.this,patientInfo)){
                    Intent intent = new Intent(PatientRegisterActivity.this, LogInActivity.class);
                    addToFirebase();
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
        // Field variable declarations
        EditText patientNameField = findViewById(R.id.patientNameField);   
        EditText patientLastNameField = findViewById(R.id.patientLastNameField);
        EditText patientEmailField = findViewById(R.id.patientEmailField);
        EditText patientPasswordField = findViewById(R.id.patientPasswordField);
        EditText patientPhoneField = findViewById(R.id.patientPhoneField);
        EditText patientAddressField = findViewById(R.id.patientAddressField);
        EditText editHealthCardNumber  = findViewById(R.id.editHealthCardNumber);


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

    /*
     *  This method takes all the info in getPatientInfo, puts it into a Patient
     *  Object and uploads it to Firebase
     */
    private void addToFirebase(){
        Patient patient = new Patient();
        Map<String,String> patientInfo = getPatientInfo();

        patient.setFirstName(patientInfo.get("FirstName"));
        patient.setLastName(patientInfo.get("LastName"));
        patient.setUsername(patientInfo.get("Email"));
        patient.setPassword(patientInfo.get("Password"));
        patient.setPhone(patientInfo.get("PhoneNumber"));
        patient.setAddress(patientInfo.get("Address"));
        patient.setHealthCardNum(patientInfo.get("HealthCard"));

        // Success and fail messages
        databaseReference.push().setValue(patient)
            .addOnSuccessListener(voidCallback -> {
                Toast.makeText(PatientRegisterActivity.this, "Successfully signed up.", Toast.LENGTH_SHORT).show();
            })

            .addOnFailureListener(exception ->{
                Toast.makeText(PatientRegisterActivity.this,"Unsuccessfully added to Firebase due to " + exception.getMessage(),Toast.LENGTH_SHORT).show();
            });
    }
}
