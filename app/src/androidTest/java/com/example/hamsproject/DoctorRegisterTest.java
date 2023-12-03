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

public class DoctorRegisterTest {

    @Rule
    public ActivityTestRule<DoctorRegisterActivity> activityTestRule = new ActivityTestRule<>(DoctorRegisterActivity.class);

    private DoctorRegisterActivity doctorRegisterActivity;

    @Before
    public void setUp() {
        doctorRegisterActivity = activityTestRule.getActivity();
    }

    @Test
    public void testGetDoctorInfo() throws InterruptedException {

        //Set the EditText fields with test data using Espresso
        onView(withId(R.id.doctorFirstNameField)).perform(typeText("John"), closeSoftKeyboard());
        onView(withId(R.id.doctorLastNameField)).perform(typeText("Doe"), closeSoftKeyboard());
        onView(withId(R.id.doctorEmailField)).perform(typeText("john.doe@email.com"), closeSoftKeyboard());
        onView(withId(R.id.doctorPasswordField)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.doctorPhoneField)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.doctorAddressField)).perform(typeText("123 Main St"), closeSoftKeyboard());
        onView(withId(R.id.doctorEmployeeNumberField)).perform(typeText("12345"), closeSoftKeyboard());

        Map<String, String> doctorInfo = doctorRegisterActivity.getDoctorInfo();

        //Checks the EditText filled values
        assertEquals("John", doctorInfo.get("FirstName"));
        assertEquals("Doe", doctorInfo.get("LastName"));
        assertEquals("john.doe@email.com", doctorInfo.get("Email"));
        assertEquals("12345", doctorInfo.get("Password"));
        assertEquals("1234567890", doctorInfo.get("Phone"));
        assertEquals("123 Main St", doctorInfo.get("Address"));
        assertEquals("12345", doctorInfo.get("EmployeeNumber"));
    }
}
