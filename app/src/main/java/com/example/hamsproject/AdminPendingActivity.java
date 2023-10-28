package com.example.hamsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class AdminPendingActivity extends AppCompatActivity implements RecyclerViewInterfacePending {
    private PendingRequest_RecyclerViewAdapter adapter;
    ArrayList<PendingRequestModel> PendingRequestModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recyclerItemSpacing);
        RecyclerItemsFormattingActivity itemDecoration = new RecyclerItemsFormattingActivity(spacingInPixels, this);
        recyclerView.addItemDecoration(itemDecoration);

        // Initialize the adapter
        adapter = new PendingRequest_RecyclerViewAdapter(this, PendingRequestModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpPendingRequestModels();

        Button deniedButton = findViewById(R.id.deniedButton);
        Button logoutButton = findViewById(R.id.logOutButton);

        deniedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminPendingActivity.this, AdminDeniedActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AdminPendingActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpPendingRequestModels() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Requests/PendingRequests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PendingRequestModels.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String accountID = snapshot.child("accountId").getValue(String.class);
                    String userType = snapshot.child("requestDetails").getValue(String.class);

                    PendingRequestModel model = new PendingRequestModel(accountID, userType);
                    PendingRequestModels.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public void onItemClick(int position) {
        PendingRequestModel user = PendingRequestModels.get(position);
        Intent intent = new Intent(AdminPendingActivity.this, AdminPendingInfoActivity.class);
        intent.putExtra("userInfo", user.name);
        startActivity(intent);
    }
}