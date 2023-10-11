package com.example.hamsproject;

import android.content.Context;
import android.widget.Toast;
import java.util.Map;
import java.util.ArrayList;

public class ValidationUtils {

    /* 
     * This method checks if all the fields are filled in and valid
     * @param context: Context of the activity
     * @param info: Map<String, String> that contains all the patient information
     * @return boolean: true if all the fields are filled in, false otherwise
     */
    public static boolean isValidated(Context context, Map<String, String> info) {
        ArrayList<String> requiredFields = new ArrayList<>();
        StringBuilder emptyFieldsMessage = new StringBuilder();

        // Iterate through the Map
        for (String key : info.keySet()) {
            // Adds the key to the requiredFields ArrayList if the value is empty
            if (info.get(key).isEmpty()) {
                requiredFields.add(key);
                emptyFieldsMessage.append(key).append(", ");
            }
        }
        // This checks if there are any empty fields
        if (!requiredFields.isEmpty()) {
            // Remove the trailing comma and space
            emptyFieldsMessage.deleteCharAt(emptyFieldsMessage.length() - 2);
            String message = "Please fill in the following fields: " + emptyFieldsMessage.toString();
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            return false;
        }

        // This checks if the email is valid
        if (!info.get("Email").contains("@") || !info.get("Email").contains(".")) {
            Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
}

