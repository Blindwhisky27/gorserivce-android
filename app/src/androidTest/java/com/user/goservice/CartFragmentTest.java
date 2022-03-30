package com.user.goservice;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.user.goservice.Navigation.NavigationActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CartFragmentTest {
    @Rule
    public ActivityScenarioRule<NavigationActivity> addUserActivityActivityScenarioRule
            = new ActivityScenarioRule<NavigationActivity>(NavigationActivity.class);


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testUserInputScenario() {
        Espresso.onView(withId(R.id.proceedButton)).perform(click());
        new BookingTest().testUserInputScenario();
    }


    @After
    public void tearDown() throws Exception {
    }
}
