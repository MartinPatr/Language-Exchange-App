package com.example.hamsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;

public class ShiftCreationActivity extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    EditText startHourText;
    EditText startMinuteText;
    EditText endHourText;
    EditText endMinuteText;
    Button saveShiftButton;
    Button backButton;
    Account userData;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_creation);

        Intent getPreviousIntent = getIntent();

        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        startHourText = findViewById(R.id.startHourText);
        startMinuteText = findViewById(R.id.startMinuteText);
        endHourText = findViewById(R.id.endHourText);
        endMinuteText = findViewById(R.id.endMinuteText);
        saveShiftButton = findViewById(R.id.saveShiftButton);
        backButton = findViewById(R.id.backButton);
        userData = (Account) getPreviousIntent.getSerializableExtra("userData");
        date = null;


        Log.d("Doctor Info: ", userData.getFirstName());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

                Calendar datePicked = Calendar.getInstance();
                datePicked.set(year, month, day);

                if (datePicked.before(Calendar.getInstance())) {
                    //If the user tries to pick a date that already happened
                    Toast.makeText(ShiftCreationActivity.this, "Date can't have already passed.", Toast.LENGTH_SHORT).show();
                } else {
                    date =  (month + 1) + "/" + day + "/" + year;
                }
            }
        });

        saveShiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startHour = startHourText.getText().toString();
                String endHour = endHourText.getText().toString();
                String startMinute = startMinuteText.getText().toString();
                String endMinute = endMinuteText.getText().toString();

                if (checkTime(startHour, endHour, startMinute, endMinute) && checkDate(date)) {

                    Shift newShift = new Shift(date, Integer.parseInt(startHour), Integer.parseInt(startMinute), Integer.parseInt(endHour), Integer.parseInt(endMinute), userData.getKey(), (userData.getFirstName() + userData.getLastName()));

                    shiftOverlapCheck(newShift);
                    createAppointments(newShift);

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ShiftCreationActivity.this, ListOfShiftsActivity.class);
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Shift> splitShift(Shift shift){
        ArrayList<Shift> availableAppointments = new ArrayList<>();
        int startHour = shift.getStartHour();
        int startMinute = shift.getStartMinute();
        int endHour = shift.getEndHour();
        int endMinute = shift.getEndMinute();

        while(true){
            //Breaks the loop when the start time is equal to the end time
            if(startHour==endHour && startMinute == endMinute) {
                break;
            }
            else{
                //If the start minute is 30, it goes to the next hour
                if(startMinute == 30){
                    Shift newShift = new Shift(shift.getDate(), startHour, startMinute, startHour+1, 00, shift.getDoctorKey(), shift.getDoctorName());
                    newShift.setDoctorName(shift.getDoctorName());
                    startHour = startHour+1;
                    startMinute = 0;

                    availableAppointments.add(newShift);
                }
                //If the start minute is 0, it keeps the same hour but increases 30 minutes
                else if(startMinute == 00){
                    Shift newShift = new Shift(shift.getDate(), startHour, startMinute, startHour, 30, shift.getDoctorKey(), shift.getDoctorName());
                    newShift.setDoctorName(shift.getDoctorName());
                    startMinute = 30;
                    availableAppointments.add(newShift);
                }
            }
        }
        return availableAppointments;
    }

    private void createAppointments(Shift shift) {
        ArrayList<Shift> availableAppointments = splitShift(shift);

        DatabaseReference availableAppointmentsReference = FirebaseDatabase.getInstance().getReference("Appointments").child("AvailableAppointments");

        for(Shift availableAppointment : availableAppointments){
            //Makes a custom id/key for the database reference
            String customId = availableAppointment.getDoctorName() +
                    availableAppointment.getDate() +
                    availableAppointment.getStartHour() +
                    availableAppointment.getStartMinute();

            //Removes invalid characters
            customId = customId.replaceAll("[^a-zA-Z0-9]", "_");
            availableAppointment.setID(customId);

            DatabaseReference appointmentReference = availableAppointmentsReference.child(customId);

            appointmentReference.setValue(availableAppointment, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError == null){
                        Toast.makeText(ShiftCreationActivity.this, "Shift Created", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.e("ShiftCreationActivity", "Error: " + databaseError.getMessage());
                    }
                }
            });
        }
    }


    private boolean checkTime(String startHour, String endHour, String startMinute, String endMinute) {
        try{

            int sHour = Integer.parseInt(startHour);
            int eHour = Integer.parseInt(endHour);
            int sMinute = Integer.parseInt(startMinute);
            int eMinute = Integer.parseInt(endMinute);

            //Checks if the entered time is valid
            if (sHour < 0 || sHour > 23 || eHour < 0 || eHour > 23) {
                Toast.makeText(ShiftCreationActivity.this, "Hour Must Be Between 0-23", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if ((sMinute != 0 && sMinute != 30) || (eMinute != 0 && eMinute != 30)) {
                Toast.makeText(ShiftCreationActivity.this, "Shifts Must Be on 30 Minute Intervals", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if ((eHour < sHour) || ((eHour == sHour) && (eMinute <= sMinute))) {
                Toast.makeText(ShiftCreationActivity.this, "End Time Must be After Start Time", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(ShiftCreationActivity.this, "Invalid Time", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkDate(String date){
        if (date == null){
            Toast.makeText(ShiftCreationActivity.this, "Select a Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void shiftOverlapCheck(Shift newShift) {
        //checks if new shift conflicts with an existing shifts time slot
        DatabaseReference shiftsReference = FirebaseDatabase.getInstance().getReference().child("Accounts").child("Doctor").child(userData.getKey()).child("shifts");

        shiftsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean overlap = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //looping through all existing shifts
                    Shift existingShift = snapshot.getValue(Shift.class);

                    if (existingShift != null && existingShift.getDate().equals(newShift.getDate())) {
                        //making sure its only checking for shifts on the specific date of the new shift

                        int scheduledStartHour = existingShift.getStartHour();
                        int scheduledStartMinute = existingShift.getStartMinute();
                        int scheduledEndHour = existingShift.getEndHour();
                        int scheduledEndMinute = existingShift.getEndMinute();

                        //checking if shifts starting time doesn't overlaps with any other shift or starts at same time as a shift ends
                        boolean startOverlap = (newShift.getStartHour() < scheduledEndHour || (newShift.getStartHour() == scheduledEndHour && newShift.getStartMinute() < scheduledEndMinute));

                        //checking if shifts ending time doesn't overlaps with any other shift or ends at the start of a new shift
                        boolean endOverlap = (newShift.getEndHour() > scheduledStartHour || (newShift.getEndHour() == scheduledStartHour && newShift.getEndMinute() > scheduledStartMinute));

                        if (startOverlap && endOverlap) {
                            overlap = true;
                            break;
                        }
                    }
                }

                if (overlap){

                    Toast.makeText(ShiftCreationActivity.this, "Shift overlaps with existing shift", Toast.LENGTH_SHORT).show();

                }
                else{
                    //Saves new shift
                    DatabaseReference shift = shiftsReference.push();
                    shift.setValue(newShift);

                    //Sets the key variable in the shift to the key in firebase
                    String shiftId = shift.getKey();
                    newShift.setID(shiftId);
                    newShift.setDoctorName(userData.getFirstName() + " " + userData.getLastName());
                    shift.setValue(newShift);


                    Intent intent = new Intent(ShiftCreationActivity.this, ListOfShiftsActivity.class);
                    intent.putExtra("userData", userData);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }
}