package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AdminActivityPending extends AppCompatActivity {

    ArrayList<pendingRequestModel> pendingRequestModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setUpPendingRequestModels();

        PendingRequest_RecyclerViewAdapter adapter = new PendingRequest_RecyclerViewAdapter(this, pendingRequestModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpPendingRequestModels() {
        String[] pendingRequestNames = getResources().getStringArray(R.array.pending_request_name_txt);
        String[] pendingRequestUserType = getResources().getStringArray(R.array.pending_request_user_type_txt);

        for (int i = 0; i < pendingRequestNames.length; i++) {
            pendingRequestModels.add(new pendingRequestModel(pendingRequestNames[i], pendingRequestUserType[i]));
        }
    }
}