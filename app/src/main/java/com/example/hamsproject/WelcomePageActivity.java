package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomePageActivity extends AppCompatActivity {
    String userType;
    TextView userTypeDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Button logoutButton = findViewById(R.id.logoutButton);

        Intent getPreviousIntent = getIntent();

        if (getPreviousIntent != null){
            userType = getPreviousIntent.getStringExtra("userType");
            userTypeDisplayed = (TextView) findViewById(R.id.yourRoleDisplayed);
            userTypeDisplayed.setText("You are logged in as " + userType);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(WelcomePageActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}