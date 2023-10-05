package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class SpecialtySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_selection);

        Button registerAsDoctor = findViewById(R.id.registerAsDoctor);
        registerAsDoctor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(SpecialtySelectionActivity.this, WelcomePageActivity.class);
                startActivity(intent);
            }
        });
    }
}