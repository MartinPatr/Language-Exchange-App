package com.example.hamsproject;

import android.content.Context;
import android.widget.Toast;
import java.util.Map;

public class ValidationUtils {

    /* 
     * This method checks if all the fields are filled in and valid
     * @param context: Context of the activity
     * @param patientInfo: Map<String, String> that contains all the patient information
     * @return boolean: true if all the fields are filled in, false otherwise
     */
    public static boolean isValidated(Context context, Map<String, String> info) {
        // Iterate through the Map
        for (String key : info.keySet()) {
            // This checks if the field is empty
            if (info.get(key).isEmpty()) {
                Toast.makeText(context, "Please fill the following fields", Toast.LENGTH_SHORT).show();
                return false;
            }
            // This checks if the email is valid
            else if (key.equals("Email") && !info.get(key).contains("@") && !info.get(key).contains(".")) {
                Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
