package com.user.goservice;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.user.goservice.Booking.BookingActivity;
import com.user.goservice.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class BookingTest {

    @Rule
    public ActivityScenarioRule<BookingActivity> bookingActivityActivityScenarioRule
            = new ActivityScenarioRule<BookingActivity>(BookingActivity.class);


    @Before
    public void setUp() throws Exception {
    }
    String address = "#443, 4th cross, 9th main, vijayanagar, Bangalore-560040";
    @Test
    public void testUserInputScenario() {
        Espresso.onView(withId(R.id.addressEditText)).perform(typeText(address));
        Espresso.onView(withId(R.id.placeOrderButton)).perform(click());
    }


    @After
    public void tearDown() throws Exception {
    }
}
