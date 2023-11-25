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
    private String appointmentId;

    public AppointmentAdapter(List<Appointment> appointmentList, OnAppointmentItemClickListener listener, String appointmentId){
        appointments = appointmentList;
        onAppointmentItemClickListener = listener;
        appointmentId = appointmentId;
    }

    public Appointment getAppointment(int position){
        return this.appointments.get(position);
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_app_row, parent, false);
        return new ViewHolder(view, onAppointmentItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("AppointmentAdapter", "List size: " + appointments.size());
        Appointment appointment = appointments.get(position);


        holder.nameField.setText(String.valueOf(appointment.getPatientName()));
        holder.timeField.setText((appointment.getDate() + " at " + appointment.getStartHour() + ":" + appointment.getStartMinute()));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameField;
        TextView timeField;
        OnAppointmentItemClickListener listener;

        public ViewHolder(@NonNull View itemView, OnAppointmentItemClickListener listener) {
            super(itemView);

            nameField = itemView.findViewById(R.id.nameField);
            timeField = itemView.findViewById(R.id.timeField);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        //Used for when the user clicks on an appointment in the recycler
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                Appointment clickedAppointment = appointments.get(position);
                listener.onAppointmentItemClick(clickedAppointment);
            }
        }
    }

}
