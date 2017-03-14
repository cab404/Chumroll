package com.cab404.chumroll.test;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cab404.chumroll.example.R;
import com.cab404.chumroll.example.TestActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created at 17:21 on 14/03/17
 *
 * @author cab404
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Tester {

    @Rule
    public ActivityTestRule<TestActivity> activity = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void pushSomeButtons() {
        for (int i = 0; i < 10; i++) {
            onView(withText("A")).perform(click());
            onView(withText("B")).perform(click());
            onView(withText("C")).perform(click());
        }
        onView(ViewMatchers.withId(R.id.list)).perform(ViewActions.swipeDown());

        for (int i = 0; i < 10; i++) {
            onView(ViewMatchers.withId(R.id.list)).perform(ViewActions.click());
        }
    }

}
