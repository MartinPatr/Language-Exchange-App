package com.example.hamsproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.Button;

public class PatientShiftAdapter extends RecyclerView.Adapter<PatientShiftAdapter.PatientShiftViewHolder> {

    private static List<Shift> shifts;
    private OnItemClickListener onItemClickListener;

    public PatientShiftAdapter(List<Shift> patientShifts, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.shifts = patientShifts;
    }

    public interface OnItemClickListener {
        void onItemClick(Shift shift);

        void onBookButtonClick(Shift shift);
    }

    @NonNull
    @Override
    public PatientShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_shift_row, parent, false);
        return new PatientShiftViewHolder(itemView, onItemClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientShiftViewHolder holder, int position) {

        Shift shift = shifts.get(position);

        //Adds the date and time to the shift view
        holder.dateField.setText("Dr. " + shift.getDoctorName());

        //Formats the way the time is displayed
        if (Integer.toString(shift.getEndMinute()).equals("0") && Integer.toString(shift.getStartMinute()).equals("0")) {
            holder.timeField.setText(shift.getDate() + " > " + Integer.toString(shift.getStartHour()) + ":00" + " - " + Integer.toString(shift.getEndHour()) + ":00");
        } else if (Integer.toString(shift.getEndMinute()).equals("0")) {
            holder.timeField.setText(shift.getDate() + " > " + Integer.toString(shift.getStartHour()) + ":" + Integer.toString(shift.getStartMinute()) + " - " + Integer.toString(shift.getEndHour()) + ":00");
        } else if (Integer.toString(shift.getStartMinute()).equals("0")) {
            holder.timeField.setText(shift.getDate() + " > " + Integer.toString(shift.getStartHour()) + ":00" + " - " + Integer.toString(shift.getEndHour()) + ":" + Integer.toString(shift.getEndMinute()));
        } else {
            holder.timeField.setText(shift.getDate() + " > " + Integer.toString(shift.getStartHour()) + ":" + Integer.toString(shift.getStartMinute()) + " - " + Integer.toString(shift.getEndHour()) + ":" + Integer.toString(shift.getEndMinute()));
        }
    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }

    public static class PatientShiftViewHolder extends RecyclerView.ViewHolder {
        TextView dateField;
        TextView timeField;
        Button bookButton;

        public PatientShiftViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            dateField = itemView.findViewById(R.id.dateField);
            timeField = itemView.findViewById(R.id.timeField);
            bookButton = itemView.findViewById(R.id.bookButton);

            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onBookButtonClick(shifts.get(position));
                    }
                }
            });
        }
    }
}