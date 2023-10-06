package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                if(isValidated()){
                    Intent intent = new Intent(LogInActivity.this, WelcomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isValidated(){
        String username = passwordField.getText().toString();
        String password = usernameField.getText().toString();

        return (!username.isEmpty() && !password.isEmpty());
    }
}