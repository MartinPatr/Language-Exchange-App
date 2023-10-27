package com.example.hamsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeniedRequest_RecyclerViewAdapter extends RecyclerView.Adapter<DeniedRequest_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterfaceDenied recyclerViewInterfaceDenied;

    Context context;
    ArrayList<DeniedRequestModel> deniedRequestModels;


    public DeniedRequest_RecyclerViewAdapter(Context context, ArrayList<DeniedRequestModel> deniedRequestModels,
                                             RecyclerViewInterfaceDenied recyclerViewInterfaceDenied) {
        this.context = context;
        this.deniedRequestModels = deniedRequestModels;
        this.recyclerViewInterfaceDenied = recyclerViewInterfaceDenied;
    }
    @NonNull
    @Override
    public DeniedRequest_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflates layout giving look to the rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.denied_request_row, parent, false);
        return new DeniedRequest_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterfaceDenied);
    }

    @Override
    public void onBindViewHolder(@NonNull DeniedRequest_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to views created in recycler_view_row layout file based on position of recycler view
        holder.tvName.setText(deniedRequestModels.get(position).getUserType());

    }

    @Override
    public int getItemCount() {
        // necessary for recycler view to know number of items needed to be displayed
        return deniedRequestModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabs views from recycler_view_row layout file

        TextView tvName, tvUserType;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterfaceDenied recyclerViewInterfaceDenied) {
            super(itemView);

            tvName = itemView.findViewById(R.id.infoField);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterfaceDenied != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterfaceDenied.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
