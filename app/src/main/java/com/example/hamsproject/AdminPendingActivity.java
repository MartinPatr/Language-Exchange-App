package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View;


import java.util.ArrayList;

public class AdminPendingActivity extends AppCompatActivity implements RecyclerViewInterfacePending{



    ArrayList<PendingRequestModel> PendingRequestModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setUpPendingRequestModels();

        PendingRequest_RecyclerViewAdapter adapter = new PendingRequest_RecyclerViewAdapter(this, PendingRequestModels,
                this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button deniedButton = findViewById(R.id.deniedButton);
        deniedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminPendingActivity.this, AdminDeniedActivity.class); // del
                startActivity(intent); // del
            }

        });
    }

    private void setUpPendingRequestModels() {
        String[] pendingRequestNames = getResources().getStringArray(R.array.pending_request_name_txt);
        String[] pendingRequestUserType = getResources().getStringArray(R.array.pending_request_user_type_txt);

        for (int i = 0; i < pendingRequestNames.length; i++) {
            PendingRequestModels.add(new PendingRequestModel(pendingRequestNames[i], pendingRequestUserType[i]));
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AdminPendingActivity.this, AdminPendingInfoActivity.class);

        startActivity(intent);
    }
}