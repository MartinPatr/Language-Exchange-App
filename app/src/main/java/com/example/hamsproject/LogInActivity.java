package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import com.example.hamsproject.ValidationUtils;


public class LogInActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        TextView register = findViewById(R.id.textView6);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LogInActivity.this,UserSelectionActivity.class);
                startActivity(intent);
            }
        });
        Button logInButton = findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Map<String, String> loginInfo = getLoginInfo();
                if(ValidationUtils.isValidated(LogInActivity.this,loginInfo)){
                    Intent intent = new Intent(LogInActivity.this, WelcomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /* 
     * This method gets all the patient information from the fields
     * @return Map<String, String>: Map that contains all the login information
     */
    private Map<String, String> getLoginInfo(){
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("Username", usernameField.getText().toString());
        loginInfo.put("Password", passwordField.getText().toString());
        return loginInfo;
    }

}