package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class TeacherRegisterActivity extends AppCompatActivity {
    public EditText teacherFirstNameField;
    public EditText teacherLastNameField;
    public EditText teacherEmailField;
    public EditText teacherPasswordField;
    public EditText teacherPhoneField;
    public EditText teacherAddressField;
    public EditText teacherEmployeeNumberField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        teacherFirstNameField = findViewById(R.id.teacherFirstNameField);
        teacherLastNameField = findViewById(R.id.teacherLastNameField);
        teacherEmailField = findViewById(R.id.teacherEmailField);
        teacherPasswordField = findViewById(R.id.teacherPasswordField);
        teacherPhoneField = findViewById(R.id.teacherPhoneField);
        teacherAddressField = findViewById(R.id.teacherAddressField);

        // Retrieving TextView and Button Objects.
        Button goToSpecialties = findViewById(R.id.goToSpecialties);

        goToSpecialties.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Map<String, String> teacherInfo = getTeacherInfo();
                if(ValidationUtils.isValidated(TeacherRegisterActivity.this, teacherInfo)) {
                    Intent intent = new Intent(TeacherRegisterActivity.this, SpecialtySelectionActivity.class);

                    intent.putExtra("firstName",teacherInfo.get("FirstName"));
                    intent.putExtra("lastName",teacherInfo.get("LastName"));
                    intent.putExtra("email", teacherInfo.get("Email"));
                    intent.putExtra("password", teacherInfo.get("Password"));
                    intent.putExtra("phone", teacherInfo.get("Phone"));
                    intent.putExtra("address", teacherInfo.get("Address"));
                    intent.putExtra("employeeNum", teacherInfo.get("EmployeeNumber"));

                    startActivity(intent);
                }
            }
        });
    }



    /* 
     * This method gets all the teacher information from the fields
     * @return Map<String, String>: Map that contains all the User information
     */
    public Map<String, String> getTeacherInfo(){
    // Field variable declarations
    teacherFirstNameField = findViewById(R.id.teacherFirstNameField);
    teacherLastNameField = findViewById(R.id.teacherLastNameField);
    teacherEmailField = findViewById(R.id.teacherEmailField);
    teacherPasswordField = findViewById(R.id.teacherPasswordField);
    teacherPhoneField = findViewById(R.id.teacherPhoneField);
    teacherAddressField = findViewById(R.id.teacherAddressField);

    Map<String, String> teacherInfo = new HashMap<>();
    teacherInfo.put("FirstName", teacherFirstNameField.getText().toString());
    teacherInfo.put("LastName", teacherLastNameField.getText().toString());
    teacherInfo.put("Email", teacherEmailField.getText().toString());
    teacherInfo.put("Password", teacherPasswordField.getText().toString());
    teacherInfo.put("Phone", teacherPhoneField.getText().toString());
    teacherInfo.put("Address", teacherAddressField.getText().toString());
    teacherInfo.put("EmployeeNumber", teacherEmployeeNumberField.getText().toString());
    
    return teacherInfo;
    }


}