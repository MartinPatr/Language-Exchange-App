package com.example.hamsproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private List<Appointment> appointments;
    private OnAppointmentItemClickListener onAppointmentItemClickListener;

    public AppointmentAdapter(List<Appointment> appointmentList, OnAppointmentItemClickListener listener){
        appointments = appointmentList;
        onAppointmentItemClickListener = listener;
    }

    public void setAppointmentList(List<Appointment> appointmentList){
        appointments = appointmentList;
        notifyDataSetChanged();
    }

    public interface OnAppointmentItemClickListener{
        void onAppointmentItemClick(Appointment appointment);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row,parent,false);
        return new ViewHolder(view,onAppointmentItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);

        //All of this just reformats the String for time to add a colon in the middle. May need to change
        String time = String.valueOf(appointment.getTime());
        String newTime = "";
        for (int i = 0; i < time.length()/2; i++) {
            char c = time.charAt(i);
            newTime += c;
        }
        newTime += ":";
        for (int i = 2; i < time.length(); i++) {
            char c = time.charAt(i);
            newTime += c;
        }

        holder.nameField.setText(String.valueOf(appointment.getPatientName()));
        holder.timeField.setText((appointment.getDate() + " at " + newTime));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameField;
        TextView timeField;

        public ViewHolder(@NonNull View itemView, OnAppointmentItemClickListener listener){
            super(itemView);

            nameField = itemView.findViewById(R.id.nameField);
            timeField = itemView.findViewById(R.id.timeField);
        }
    }





}
