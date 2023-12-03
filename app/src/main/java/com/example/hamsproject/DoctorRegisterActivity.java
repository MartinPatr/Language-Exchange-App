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
    public EditText doctorFirstNameField;
    public EditText doctorLastNameField;
    public EditText doctorEmailField;
    public EditText doctorPasswordField;
    public EditText doctorPhoneField;
    public EditText doctorAddressField;
    public EditText doctorEmployeeNumberField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        doctorFirstNameField = findViewById(R.id.doctorFirstNameField);
        doctorLastNameField = findViewById(R.id.doctorLastNameField);
        doctorEmailField = findViewById(R.id.doctorEmailField);
        doctorPasswordField = findViewById(R.id.doctorPasswordField);
        doctorPhoneField = findViewById(R.id.doctorPhoneField);
        doctorAddressField = findViewById(R.id.doctorAddressField);
        doctorEmployeeNumberField = findViewById(R.id.doctorEmployeeNumberField);

        // Retrieving TextView and Button Objects.
        Button goToSpecialties = findViewById(R.id.goToSpecialties);

        goToSpecialties.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Map<String, String> doctorInfo = getDoctorInfo();
                if(ValidationUtils.isValidated(DoctorRegisterActivity.this, doctorInfo)) {
                    Intent intent = new Intent(DoctorRegisterActivity.this, SpecialtySelectionActivity.class);

                    intent.putExtra("firstName",doctorInfo.get("FirstName"));
                    intent.putExtra("lastName",doctorInfo.get("LastName"));
                    intent.putExtra("email", doctorInfo.get("Email"));
                    intent.putExtra("password", doctorInfo.get("Password"));
                    intent.putExtra("phone", doctorInfo.get("Phone"));
                    intent.putExtra("address", doctorInfo.get("Address"));
                    intent.putExtra("employeeNum", doctorInfo.get("EmployeeNumber"));

                    startActivity(intent);
                }
            }
        });
    }



    /* 
     * This method gets all the doctor information from the fields
     * @return Map<String, String>: Map that contains all the patient information
     */
    public Map<String, String> getDoctorInfo(){
    // Field variable declarations
    doctorFirstNameField = findViewById(R.id.doctorFirstNameField);
    doctorLastNameField = findViewById(R.id.doctorLastNameField);
    doctorEmailField = findViewById(R.id.doctorEmailField);
    doctorPasswordField = findViewById(R.id.doctorPasswordField);
    doctorPhoneField = findViewById(R.id.doctorPhoneField);
    doctorAddressField = findViewById(R.id.doctorAddressField);
    doctorEmployeeNumberField = findViewById(R.id.doctorEmployeeNumberField);

    Map<String, String> doctorInfo = new HashMap<>();
    doctorInfo.put("FirstName", doctorFirstNameField.getText().toString());
    doctorInfo.put("LastName", doctorLastNameField.getText().toString());
    doctorInfo.put("Email", doctorEmailField.getText().toString());
    doctorInfo.put("Password", doctorPasswordField.getText().toString());
    doctorInfo.put("Phone", doctorPhoneField.getText().toString());
    doctorInfo.put("Address", doctorAddressField.getText().toString());
    doctorInfo.put("EmployeeNumber", doctorEmployeeNumberField.getText().toString());
    
    return doctorInfo;
    }


}