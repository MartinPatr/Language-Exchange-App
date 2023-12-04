package com.example.hamsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import android.widget.RatingBar;
public class PatientPastAppInfoActivity extends AppCompatActivity {
    String appointmentId;
    String doctorKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_past_app_info);

        Intent intent = getIntent();

        appointmentId = intent.getStringExtra("appointmentId");
        Account userData = (Account)getIntent().getSerializableExtra("userData");

        getUserInfo(appointmentId);

        //Sends the user back to the previous page
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PatientPastAppInfoActivity.this, PatientPastAppsActivity.class);                
                intent.putExtra("userData", userData);
                startActivity(intent);
            }
        });
    }

    //Gets the doctor key given the appointment ID
    private void getUserInfo(String appointmentId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments/PastAppointments").child(appointmentId);
        databaseReference.addValueEventListener(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot appointmentSnapshot) {
                if (appointmentSnapshot.exists() && appointmentSnapshot.hasChildren()) {
                    doctorKey = appointmentSnapshot.child("doctorKey").getValue(String.class);
                    if (doctorKey != null){
                        Log.d("Zelo", doctorKey);
                        getPatientInfo(doctorKey);
                    }
                    else{
                        Log.e("AppointmentPastInfoActivity", "doctorKey is null");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AppointmentPastInfoActivity", "Database error: " + databaseError.getMessage());

            };
        });
    }

    //Gets the doctor's details given the doctor key found previously.
    private void getPatientInfo(String doctorKey){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Accounts/Doctor");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (doctorKey != null) {
                        DataSnapshot doctorSnapshot = dataSnapshot.child(doctorKey);

                        String firstName = doctorSnapshot.child("firstName").getValue(String.class);
                        String lastName = doctorSnapshot.child("lastName").getValue(String.class);


                        TextView firstNameField = findViewById(R.id.firstNameField);
                        TextView lastNameField = findViewById(R.id.lastNameField);

                        firstNameField.setText(firstName);
                        lastNameField.setText(lastName);
                        getAppointmentInfo();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }

    private void getAppointmentInfo(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments/PastAppointments").child(appointmentId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    if (appointmentId != null) {
                        String date = dataSnapshot.child("date").getValue(String.class);

                        String startHour = String.valueOf(dataSnapshot.child("startHour").getValue(Long.class));
                        String startMinute = String.valueOf(dataSnapshot.child("startMinute").getValue(Long.class));
                        String endHour = String.valueOf(dataSnapshot.child("endHour").getValue(Long.class));
                        String endMinute = String.valueOf(dataSnapshot.child("endMinute").getValue(Long.class));

                        if(startMinute.equals("0")){
                            startMinute = "00";
                        }
                        if(endMinute.equals("0")){
                            endMinute = "00";
                        }
                        

                        String time = startHour + ":" + startMinute + " - " + endHour + ":" + endMinute;


                        TextView dateField = findViewById(R.id.dateField);
                        dateField.setText(date);

                        TextView timeField = findViewById(R.id.timeField);
                        timeField.setText(time);
                        Log.i("Test", String.valueOf(dataSnapshot.child("isRated").getValue(Boolean.class)));
                        TextView ratingText = findViewById(R.id.rateText);
                        Button rateButton =  findViewById(R.id.rateButton);
                        RatingBar ratingBar = findViewById(R.id.ratingBar);
                        if (dataSnapshot.child("isRated").getValue(Boolean.class) != null && dataSnapshot.child("isRated").getValue(Boolean.class)) {
                            //Sets button and widgets invisible if the user has already submitted a review
                            ratingText.setText("You have already rated this appointment");
                            rateButton.setVisibility(View.INVISIBLE);
                            ratingBar.setVisibility(View.INVISIBLE);
                        } else {
                            if (dataSnapshot.child("isRated").getValue(Boolean.class) == null) {
                                dataSnapshot.child("isRated").getRef().setValue(false);
                            }
                            ratingText.setText("Give your appointment a score from 1 to 5");
                            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                    rateButton.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v){
                                            Double doubleRating = Double.valueOf(rating);
                                            updateRating(doubleRating);
                                            Toast.makeText(PatientPastAppInfoActivity.this, "You have given this appointment a " + rating, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                        } 
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());

            }
        });
    }

    public void updateRating(Double rating) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointments/PastAppointments").child(appointmentId);
        databaseReference.child("isRated").setValue(true);
    
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Accounts/Doctor").child(doctorKey);

        DatabaseReference numRatingsRef = databaseReference2.child("numRatings");
        numRatingsRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Integer numRatings = mutableData.getValue(Integer.class);
                if (numRatings == null) {
                    //Sets to 1 if null
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue(numRatings + 1);
                }
                return Transaction.success(mutableData);
            }
    
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot currentData) {
                if (committed && databaseError == null) {
                    Toast.makeText(PatientPastAppInfoActivity.this, "Rating updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PatientPastAppInfoActivity.this, "Failed to update rating", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DatabaseReference RatingRef = databaseReference2.child("rating");
        RatingRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Double ratingDB = mutableData.getValue(Double.class);
                if (ratingDB == null) {
                    mutableData.setValue(rating);
                } else {
                    mutableData.setValue(ratingDB + rating);
                }
                return Transaction.success(mutableData);
            }
    
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot currentData) {
                if (committed && databaseError == null) {
                    Toast.makeText(PatientPastAppInfoActivity.this, "Rating updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PatientPastAppInfoActivity.this, "Failed to update rating", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    

}
