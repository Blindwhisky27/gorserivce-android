package com.user.goservice;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.user.goservice.Services.GeneralServiceActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AddServiceTest {
    @Rule
    public ActivityScenarioRule<GeneralServiceActivity> addUserActivityActivityScenarioRule
            = new ActivityScenarioRule<GeneralServiceActivity>(GeneralServiceActivity.class);


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testUserInputScenario() {
        Espresso.onView(withId(R.id.addToCartButton)).perform(click());
        Espresso.onView(withId(R.id.proceedToCartButton)).perform(click());
        new CartFragmentTest().testUserInputScenario();

    }


    @After
    public void tearDown() throws Exception {
    }
}
