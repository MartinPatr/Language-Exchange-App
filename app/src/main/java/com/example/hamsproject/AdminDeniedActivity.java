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

public class AdminDeniedActivity extends AppCompatActivity implements RecyclerViewInterfaceDenied{

    private DeniedRequest_RecyclerViewAdapter adapter;
    ArrayList<DeniedRequestModel> deniedRequestModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_denied);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recyclerItemSpacing);
        RecyclerItemsFormattingActivity itemDecoration = new RecyclerItemsFormattingActivity(spacingInPixels, this);
        recyclerView.addItemDecoration(itemDecoration);

        adapter = new DeniedRequest_RecyclerViewAdapter(this, deniedRequestModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpDeniedRequestModels();

        Button pendingButton = findViewById(R.id.pendingButton);
        Button logoutButton = findViewById(R.id.logOutButton);

        pendingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminDeniedActivity.this, AdminPendingActivity.class); // del
                startActivity(intent); // del
            }

        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(AdminDeniedActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpDeniedRequestModels() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Requests/DeniedRequests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("MyTag", "Data retrieval successful. Number of items: " + dataSnapshot.getChildrenCount());
                deniedRequestModels.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String accountID = snapshot.child("accountId").getValue(String.class);
                    String userType = snapshot.child("requestDetails").getValue(String.class);

                    DeniedRequestModel model = new DeniedRequestModel(accountID, userType);
                    deniedRequestModels.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    @Override
    public void onItemClick(int position) {
        DeniedRequestModel user = deniedRequestModels.get(position);
        Intent intent = new Intent(AdminDeniedActivity.this, AdminDeniedInfoActivity.class);
        intent.putExtra("userInfo", user.name);
        startActivity(intent);
    }
}