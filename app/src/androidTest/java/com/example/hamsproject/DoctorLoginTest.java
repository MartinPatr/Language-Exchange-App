package com.example.hamsproject;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.hamsproject.R;
import com.example.hamsproject.LogInActivity;
import com.example.hamsproject.DoctorPageActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DoctorLoginTest {

    @Rule
    public IntentsTestRule<LogInActivity> activityTestRule = new IntentsTestRule<>(LogInActivity.class);

    @Test
    // testing login for doctor user type
    public void testDoctorLogin() {
        String doctorEmail = "q@q.q";
        String doctorPassword = "12345";


        onView(withId(R.id.usernameField)).perform(typeText(doctorEmail), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText(doctorPassword), closeSoftKeyboard());


        onView(withId(R.id.logInButton)).perform(click());

        // delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(DoctorPageActivity.class.getName()));
    }
}
