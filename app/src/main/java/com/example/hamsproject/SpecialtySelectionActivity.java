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
        databaseReference = firebaseDatabase.getReference("Accounts/Teacher");

        Intent getPreviousIntents = getIntent();

        if(getPreviousIntents != null){
            firstName = getPreviousIntents.getStringExtra("firstName");
            lastName = getPreviousIntents.getStringExtra("lastName");
            email = getPreviousIntents.getStringExtra("email");
            password = getPreviousIntents.getStringExtra("password");
            phone = getPreviousIntents.getStringExtra("phone");
            address = getPreviousIntents.getStringExtra("address");
            employeeNum = getPreviousIntents.getStringExtra("employeeNum");
        }

        Button registerAsTeacher = findViewById(R.id.registerAsTeacher);
        familyMedicineSpecialty = findViewById(R.id.familyMedicineSpecialty);
        internalMedicineSpecialty = findViewById(R.id.internalMedicineSpecialty);
        pediatricsSpecialty = findViewById(R.id.pediatricsSpecialty);
        obstetricsSpecialty = findViewById(R.id.obstetricsSpecialty);
        gynecologySpecialty = findViewById(R.id.gynecologySpecialty);

        registerAsTeacher.setOnClickListener(new View.OnClickListener() {
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
        Teacher teacher = new Teacher();
        Map<String,Boolean> specialities = new HashMap<String,Boolean>();
        Map<String, Map<String, Object>> shifts = new HashMap<>();

        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setUsername(email);
        teacher.setPassword(password);
        teacher.setPhone(phone);
        teacher.setAddress(address);

        specialities.put("familyMedicine", familyMedicineSpecialty.isChecked());
        specialities.put("internalMedicine", internalMedicineSpecialty.isChecked());
        specialities.put("pediatrics", pediatricsSpecialty.isChecked());
        specialities.put("obstetrics", obstetricsSpecialty.isChecked());
        specialities.put("gynecology", gynecologySpecialty.isChecked());
        teacher.setSpecialties(specialities);
        teacher.setShifts(shifts);

        // Push the user to the appropriate location
        DatabaseReference newTeacherRef = databaseReference.push(); 
        String newTeacherKey = newTeacherRef.getKey();
        teacher.setKey(newTeacherKey);

        // Success and fail messages
        newTeacherRef.setValue(teacher)
            .addOnSuccessListener(voidCallback -> {
                CreateRequestUtils.addPendingRequest(SpecialtySelectionActivity.this,teacher, newTeacherKey);
            })

            .addOnFailureListener(exception ->{
                Toast.makeText(SpecialtySelectionActivity.this,"Unsuccessful due to " + exception.getMessage(),Toast.LENGTH_SHORT).show();
            });
    }
}