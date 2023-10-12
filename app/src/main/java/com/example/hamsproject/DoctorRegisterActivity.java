package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import com.example.hamsproject.ValidationUtils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorRegisterActivity extends AppCompatActivity {
    // Field variable declarations
    private EditText doctorNameField;
    private EditText doctorLastNameField;
    private EditText doctorEmailField;
    private EditText doctorPasswordField;
    private EditText doctorPhoneField;
    private EditText doctorAddressField;
    private EditText doctorEmployeeNumberField;

    // Firebase variable declarations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //=============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctor");

        // Retrieving TextView and Button Objects.
        Button goToSpecialties = findViewById(R.id.goToSpecialties);

        doctorNameField = findViewById(R.id.doctorNameField);
        doctorLastNameField = findViewById(R.id.doctorLastNameField);
        doctorEmailField = findViewById(R.id.doctorEmailField);
        doctorPasswordField = findViewById(R.id.doctorPasswordField);
        doctorPhoneField = findViewById(R.id.doctorPhoneField);
        doctorAddressField = findViewById(R.id.doctorAddressField);
        doctorEmployeeNumberField = findViewById(R.id.doctorEmployeeNumberField);

        goToSpecialties.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Map<String, String> doctorInfo = getDoctorInfo();
                if(ValidationUtils.isValidated(DoctorRegisterActivity.this, doctorInfo)) {
                    Intent intent = new Intent(DoctorRegisterActivity.this, SpecialtySelectionActivity.class);
                    addToFirebase();
                    startActivity(intent);
                }
            }
        });
    }

    /* 
     * This method gets all the doctor information from the fields
     * @return Map<String, String>: Map that contains all the patient information
     */
    private Map<String, String> getDoctorInfo(){
        Map<String, String> doctorInfo = new HashMap<>();
        doctorInfo.put("FirstName", doctorNameField.getText().toString());
        doctorInfo.put("LastName", doctorLastNameField.getText().toString());
        doctorInfo.put("Email", doctorEmailField.getText().toString());
        doctorInfo.put("Password", doctorPasswordField.getText().toString());
        doctorInfo.put("Phone", doctorPhoneField.getText().toString());
        doctorInfo.put("Address", doctorAddressField.getText().toString());
        doctorInfo.put("EmployeeNumber", doctorEmployeeNumberField.getText().toString());
        
        return doctorInfo;
    }

    private void addToFirebase(){
        Doctor doctor = new Doctor();
        Map<String,String> doctorInfo = getDoctorInfo();

        doctor.setFirstName(doctorInfo.get("FirstName"));
        doctor.setLastName(doctorInfo.get("LastName"));
        doctor.setUsername(doctorInfo.get("Email"));
        doctor.setPassword(doctorInfo.get("Password"));
        doctor.setPhone(doctorInfo.get("PhoneNumber"));
        doctor.setAddress(doctorInfo.get("Address"));
        doctor.setEmployeeNum(doctorInfo.get("EmployeeNumber"));

        // Success and fail messages
        databaseReference.push().setValue(doctor)
                .addOnSuccessListener(voidCallback -> {
                    //databaseReference.setValue(patient);
                    Toast.makeText(DoctorRegisterActivity.this, "Successfully added to Firebase.", Toast.LENGTH_SHORT).show();
                })

                .addOnFailureListener(exception ->{
                    Toast.makeText(DoctorRegisterActivity.this,"Unsuccessfully added to Firebase due to " + exception.getMessage(),Toast.LENGTH_SHORT).show();
                });
    }
}