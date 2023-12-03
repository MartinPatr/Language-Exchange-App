package com.example.hamsproject;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class DoctorAcceptAllAppointmentsTest {

    @Rule
    public IntentsTestRule<LogInActivity> activityTestRule = new IntentsTestRule<>(LogInActivity.class);

    @Test
    public void testDoctorAcceptAllAppointments() {
        // testing accept all appointments button from requested appointments
        // login
        String doctorEmail = "q@q.q";
        String doctorPassword = "12345";


        onView(withId(R.id.usernameField)).perform(typeText(doctorEmail), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText(doctorPassword), closeSoftKeyboard());


        onView(withId(R.id.logInButton)).perform(click());

        // Delay
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // view dr requests
        onView(withId(R.id.viewRequestedAppointmentsButton)).perform(click());

        // Delay
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // making sure the dr request activity is launched
        intended(hasComponent(AppointmentListRequestsActivity.class.getName()));

        // accept all appointment requests
        onView(withId(R.id.acceptAllAppointmentsButton)).perform(click());

    }
}
