package com.example.hamsproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.util.Map;

public class PatientRegisterTest {

    @Rule
    public ActivityTestRule<PatientRegisterActivity> activityTestRule = new ActivityTestRule<>(PatientRegisterActivity.class);

    private PatientRegisterActivity patientRegisterActivity;

    @Before
    public void setUp() {
        patientRegisterActivity = activityTestRule.getActivity();
    }

    @Test
    public void testGetPatientInfo() throws InterruptedException {

            //Set the EditText fields with test data using Espresso
            onView(withId(R.id.patientNameField)).perform(typeText("John"), closeSoftKeyboard());
            onView(withId(R.id.patientLastNameField)).perform(typeText("Doe"), closeSoftKeyboard());
            onView(withId(R.id.patientEmailField)).perform(typeText("john.doe@email.com"), closeSoftKeyboard());
            onView(withId(R.id.patientPasswordField)).perform(typeText("12345"), closeSoftKeyboard());
            onView(withId(R.id.patientPhoneField)).perform(typeText("1234567890"), closeSoftKeyboard());
            onView(withId(R.id.patientAddressField)).perform(typeText("123 Main St"), closeSoftKeyboard());
            onView(withId(R.id.healthCardNumField)).perform(typeText("12345"), closeSoftKeyboard());

            Map<String, String> patientInfo = patientRegisterActivity.getPatientInfo();

            //Checks the EditText filled values
            assertEquals("John", patientInfo.get("FirstName"));
            assertEquals("Doe", patientInfo.get("LastName"));
            assertEquals("john.doe@email.com", patientInfo.get("Email"));
            assertEquals("12345", patientInfo.get("Password"));
            assertEquals("1234567890", patientInfo.get("PhoneNumber"));
            assertEquals("123 Main St", patientInfo.get("Address"));
            assertEquals("12345", patientInfo.get("HealthCard"));

    }
}