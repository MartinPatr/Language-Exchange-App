package com.example.hamsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PendingRequest_RecyclerViewAdapter extends RecyclerView.Adapter<PendingRequest_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<pendingRequestModel> pendingRequestModels;


    public PendingRequest_RecyclerViewAdapter(Context context, ArrayList<pendingRequestModel> pendingRequestModels) {
        this.context = context;
        this.pendingRequestModels = pendingRequestModels;
    }
    @NonNull
    @Override
    public PendingRequest_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflates layout giving look to the rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pending_request_row, parent, false);
        return new PendingRequest_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRequest_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to views created in recycler_view_row layout file based on position of recycler view

        holder.tvName.setText(pendingRequestModels.get(position).getName());
        holder.tvUserType.setText(pendingRequestModels.get(position).getUserType());
    }

    @Override
    public int getItemCount() {
        // necessary for recycler view to know number of items needed to be displayed
        return pendingRequestModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabs views from recycler_view_row layout file

        TextView tvName, tvUserType;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvUserType = itemView.findViewById(R.id.userType);

        }
    }
}
