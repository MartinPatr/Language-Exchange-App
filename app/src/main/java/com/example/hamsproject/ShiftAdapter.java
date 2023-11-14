package com.example.hamsproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ViewHolder> {

    private static List<Shift> shifts;
    private OnShiftItemClickListener onShiftItemClickListener;

    public ShiftAdapter(List<Shift> shiftList, OnShiftItemClickListener listener) {
        shifts = shiftList;
        onShiftItemClickListener = listener;
    }

    public void setShiftList(List<Shift> shiftList) {
        shifts = shiftList;
        notifyDataSetChanged();
    }

    public interface OnShiftItemClickListener {
        void onShiftItemClick(Shift shift);
        void onShiftItemDeleteClick(Shift shift);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_row, parent, false);
        return new ViewHolder(view,onShiftItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shift shift = shifts.get(position);

        //Adds the date and time to the shift view
        holder.dateField.setText(shift.getDate());

        //Formats the way the time is displayed
        if(Integer.toString(shift.getEndMinute()).equals("0") && Integer.toString(shift.getStartMinute()).equals("0")){
            holder.timeField.setText(Integer.toString(shift.getStartHour()) + ":00" + " - " + Integer.toString(shift.getEndHour()) + ":00");
        }
        else if(Integer.toString(shift.getEndMinute()).equals("0")){
            holder.timeField.setText(Integer.toString(shift.getStartHour()) + ":" + Integer.toString(shift.getStartMinute()) + " - " + Integer.toString(shift.getEndHour()) + ":00");
        }
        else if(Integer.toString(shift.getStartMinute()).equals("0")){
            holder.timeField.setText(Integer.toString(shift.getStartHour()) + ":00" + " - " + Integer.toString(shift.getEndHour()) + ":" + Integer.toString(shift.getEndMinute()));
        }
        else{
            holder.timeField.setText(Integer.toString(shift.getStartHour()) + ":" + Integer.toString(shift.getStartMinute()) + " - " + Integer.toString(shift.getEndHour()) + ":" + Integer.toString(shift.getEndMinute()));
        }

    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateField;
        TextView timeField;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView, OnShiftItemClickListener listener) {
            super(itemView);
            dateField = itemView.findViewById(R.id.dateField);
            timeField = itemView.findViewById(R.id.timeField);
            deleteButton = itemView.findViewById(R.id.deleteButton);


            //Gives functionality to the delete button for each individual shift
            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onShiftItemDeleteClick(shifts.get(position));
                    }
                }
            });
        }
    }
}