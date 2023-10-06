package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import java.util.HashMap;
import java.util.Map;
import com.example.hamsproject.ValidationUtils;


public class DoctorRegisterActivity extends AppCompatActivity {

    private EditText doctorNameField;
    private EditText doctorLastNameField;
    private EditText doctorEmailField;
    private EditText doctorPasswordField;
    private EditText doctorPhoneField;
    private EditText doctorAddressField;
    private EditText doctorEmployeeNumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

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
}