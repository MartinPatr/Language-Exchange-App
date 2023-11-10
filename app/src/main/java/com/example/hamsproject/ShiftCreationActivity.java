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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ShiftCreationActivity extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;
    EditText startHourText;
    EditText startMinuteText;
    EditText endHourText;
    EditText endMinuteText;
    Button saveShiftButton;
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
        userData = (Account)getPreviousIntent.getSerializableExtra("userData");
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
                }
                else{
                    date = day + "/" + (month+1) + "/" + year;
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
                    Shift newShift = new Shift(date, Integer.parseInt(startHour), Integer.parseInt(startMinute), Integer.parseInt(endHour), Integer.parseInt(endMinute));

                    DatabaseReference shift = FirebaseDatabase.getInstance().getReference().child("Accounts").child("Doctor").child(userData.getKey()).child("shifts").push();;
                    shift.setValue(newShift);

                    //Sets the key variable in the shift to the key in firebase
                    String shiftId = shift.getKey();
                    newShift.setID(shiftId);
                    shift.setValue(newShift);

                    Intent intent = new Intent(ShiftCreationActivity.this, ListOfShiftsActivity.class);
                    intent.putExtra("userData", userData);
                    startActivity(intent);
                }
            }
        });
    }



    private boolean checkTime(String startHour, String endHour, String startMinute, String endMinute) {
        try {

            int sHour = Integer.parseInt(startHour);
            int eHour = Integer.parseInt(endHour);
            int sMinute = Integer.parseInt(startMinute);
            int eMinute = Integer.parseInt(endMinute);

            //Checks if the entered time is valid
            if (sHour < 0 || sHour > 23 || eHour < 0 || eHour > 23) {
                Toast.makeText(ShiftCreationActivity.this, "Hour Must Be Between 0-23", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if((sMinute != 0 && sMinute != 30) || (eMinute != 0 && eMinute != 30)){
                Toast.makeText(ShiftCreationActivity.this, "Shifts Must Be on 30 Minute Intervals", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if((eHour<sHour) || ((eHour == sHour) && (eMinute <= sMinute))){
                Toast.makeText(ShiftCreationActivity.this, "End Time Must be After Start Time", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(ShiftCreationActivity.this, "Invalid Time", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean checkDate(String date){
        if(date == null){
            Toast.makeText(ShiftCreationActivity.this, "Select a Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}