package com.udacity.politcalpreparedness.Launch

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.politcalpreparedness.MainActivity
import com.udacity.politcalpreparedness.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchFragmentTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun activityLaunches() {
        onView(withText(R.string.upcoming_elections)).check(matches(isDisplayed()))
        onView(withText(R.string.find_my_representatives)).check(matches(isDisplayed()))
    }
}