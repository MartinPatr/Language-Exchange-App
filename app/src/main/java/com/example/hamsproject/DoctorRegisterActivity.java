package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

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
                if(isValidated()) {
                    Intent intent = new Intent(DoctorRegisterActivity.this, SpecialtySelectionActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isValidated(){
        String doctorFirstName = doctorNameField.getText().toString();
        String doctorLastName = doctorLastNameField.getText().toString();
        String doctorEmail = doctorEmailField.getText().toString();
        String doctorPassword = doctorPasswordField.getText().toString();
        String doctorPhoneNumber = doctorPhoneField.getText().toString();
        String doctorAddress = doctorAddressField.getText().toString();
        String doctorEmployeeNumber = doctorEmployeeNumberField.getText().toString();

        return (!doctorFirstName.isEmpty()
                && !doctorLastName.isEmpty()
                && !doctorEmail.isEmpty()
                && !doctorPassword.isEmpty()
                && !doctorPhoneNumber.isEmpty()
                && !doctorAddress.isEmpty()
                && !doctorEmployeeNumber.isEmpty()
        );
    }
}