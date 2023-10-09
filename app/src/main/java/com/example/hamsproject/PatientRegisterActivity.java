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

    private EditText patientNameField;
    private EditText patientLastNameField;
    private EditText patientEmailField;
    private EditText patientPasswordField;
    private EditText patientPhoneField;
    private EditText patientAddressField;
    private EditText editHealthCardNumber;

    String firstName;
    String lastName;
    String email;
    String password;
    String phoneNum;
    String address;
    String healthCardNum;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    PatientInfo patientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("PatientInfo");
        patientInfo = new PatientInfo();

        // Initialize Firebase
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button registerAsPatient = findViewById(R.id.logoutButton);

        patientNameField = findViewById(R.id.patientNameField);
        patientLastNameField = findViewById(R.id.patientLastNameField);
        patientEmailField = findViewById(R.id.patientEmailField);
        patientPasswordField = findViewById(R.id.patientPasswordField);
        patientPhoneField = findViewById(R.id.patientPhoneField);
        patientAddressField = findViewById(R.id.patientAddressField);
        editHealthCardNumber = findViewById(R.id.editHealthCardNumber);

        firstName = patientNameField.getText().toString();
        lastName = patientLastNameField.getText().toString();
        email = patientEmailField.getText().toString();
        password = patientPasswordField.getText().toString();
        phoneNum = patientPhoneField.getText().toString();
        address = patientAddressField.getText().toString();
        healthCardNum = editHealthCardNumber.getText().toString();


        registerAsPatient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Map<String, String> patientInfo = getPatientInfo();
                if(ValidationUtils.isValidated(PatientRegisterActivity.this,patientInfo)){
                    Intent intent = new Intent(PatientRegisterActivity.this, WelcomePageActivity.class);
                    startActivity(intent);

                    addToFirebase(firstName,lastName, email, password, phoneNum, address,healthCardNum);
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

    private void addToFirebase(String firstName,String lastName,String email,String password, String phoneNum, String address, String healthCardNum){
        patientInfo.setFirstName(firstName);
        patientInfo.setLastName(lastName);
        patientInfo.setEmail(email);
        patientInfo.setPassword(password);
        patientInfo.setPhoneNum(phoneNum);
        patientInfo.setAddress(address);
        patientInfo.setHealthCardNum(healthCardNum);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(patientInfo);
                Toast.makeText(PatientRegisterActivity.this,"Successfully added to Firebase.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PatientRegisterActivity.this,"Unsuccessfully added to Firebase due to " + error,Toast.LENGTH_SHORT).show();

            }
        });
    }
}
