package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AdminDeniedActivity extends AppCompatActivity implements RecyclerViewInterfaceDenied{

    ArrayList<DeniedRequestModel> deniedRequestModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_denied);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setUpDeniedRequestModels();

        DeniedRequest_RecyclerViewAdapter adapter = new DeniedRequest_RecyclerViewAdapter(this, deniedRequestModels,
                this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button pendingButton = findViewById(R.id.pendingButton);
        pendingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminDeniedActivity.this, AdminPendingActivity.class); // del
                startActivity(intent); // del
            }

        });
    }

    private void setUpDeniedRequestModels() {
        String[] deniedRequestNames = getResources().getStringArray(R.array.denied_request_name_txt);
        String[] deniedRequestUserType = getResources().getStringArray(R.array.denied_request_user_type_txt);

        for (int i = 0; i < deniedRequestNames.length; i++) {
            deniedRequestModels.add(new DeniedRequestModel(deniedRequestNames[i], deniedRequestUserType[i]));
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AdminDeniedActivity.this, AdminDeniedInfoActivity.class);

        startActivity(intent);
    }
}