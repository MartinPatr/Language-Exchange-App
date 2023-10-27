package com.example.hamsproject;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateRequestUtils {
    /*
     * This method adds a request to the database
     * @param context: Context object to display toasts
     * @param account: Account object that contains the account information
     * @param accountId: String that contains the account ID
     */
    public static void addPendingRequest(Context context,Account account, String accountId) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference requestsRef = firebaseDatabase.getReference("Requests/PendingRequests");
        String requestDetails = account.getFirstName() + " " + account.getLastName() + " - " + account.getType();


        // Create a unique request ID
        String requestId = requestsRef.push().getKey();

        // Create a map to store the request details
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("accountId", accountId);
        requestMap.put("requestDetails", requestDetails);

        // Push the request to the appropriate location
        requestsRef.child(accountId).setValue(requestMap)
                .addOnSuccessListener(voidCallback -> {
                    Toast.makeText(context, "Request added successfully.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(context, "Failed to add request: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
