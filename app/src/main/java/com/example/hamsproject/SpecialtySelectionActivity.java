package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class SpecialtySelectionActivity extends AppCompatActivity {

    private CheckBox familyMedicineSpecialty;
    private CheckBox internalMedicineSpecialty;
    private CheckBox pediatricsSpecialty;
    private CheckBox obstetricsSpecialty;
    private CheckBox gynecologySpecialty;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String employeeNum;

    // Firebase variable declarations
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //=============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_selection);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctor");

        Intent intent2 = getIntent();

        if(intent2 != null){
            firstName = intent2.getStringExtra("firstName");
            lastName = intent2.getStringExtra("lastName");
            email = intent2.getStringExtra("email");
            password = intent2.getStringExtra("password");
            phone = intent2.getStringExtra("phone");
            address = intent2.getStringExtra("address");
            employeeNum = intent2.getStringExtra("employeeNum");
        }

        Button registerAsDoctor = findViewById(R.id.registerAsDoctor);
        familyMedicineSpecialty = findViewById(R.id.familyMedicineSpecialty);
        internalMedicineSpecialty = findViewById(R.id.internalMedicineSpecialty);
        pediatricsSpecialty = findViewById(R.id.pediatricsSpecialty);
        obstetricsSpecialty = findViewById(R.id.obstetricsSpecialty);
        gynecologySpecialty = findViewById(R.id.gynecologySpecialty);

        registerAsDoctor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(isValidated()){
                    Intent intent = new Intent(SpecialtySelectionActivity.this, LogInActivity.class);
                    addToFirebase();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SpecialtySelectionActivity.this, "Select at least 1 specialty.", Toast.LENGTH_SHORT).show();
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

    private void addToFirebase(){
        Doctor doctor = new Doctor();
        Map<String,Boolean> specialities = new HashMap<String,Boolean>();

        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setUsername(email);
        doctor.setPassword(password);
        doctor.setPhone(phone);
        doctor.setAddress(address);
        doctor.setEmployeeNum(employeeNum);

        specialities.put("familyMedicine", familyMedicineSpecialty.isChecked());
        specialities.put("internalMedicine", internalMedicineSpecialty.isChecked());
        specialities.put("pediatrics", pediatricsSpecialty.isChecked());
        specialities.put("obstetrics", obstetricsSpecialty.isChecked());
        specialities.put("gynecology", gynecologySpecialty.isChecked());
        doctor.setSpecialties(specialities);

        // Success and fail messages
        databaseReference.push().setValue(doctor)
            .addOnSuccessListener(voidCallback -> {
                //databaseReference.setValue(patient);
                Toast.makeText(SpecialtySelectionActivity.this, "Successfully signed up.", Toast.LENGTH_SHORT).show();
            })

            .addOnFailureListener(exception ->{
                Toast.makeText(SpecialtySelectionActivity.this,"Unsuccessfully signed up " + exception.getMessage(),Toast.LENGTH_SHORT).show();
            });
    }
}