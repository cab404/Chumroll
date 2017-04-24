package com.cab404.chumroll.example;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestActivityTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<>(TestActivity.class);

    /**
     * Adds and removes some items.
     */
    @Test
    public void testActivityTest() {

        ViewInteraction container = onView(
                allOf(withParent(withId(R.id.content)),
                        isDisplayed()));

        onView(allOf(withText(R.string.tab_list), isDisplayed())).perform(click());

        addSomeItems(10);
        removeSomeItems(3);

        for (int i = 0; i < 2; i++) container.perform(swipeUp());
        for (int i = 0; i < 2; i++) container.perform(swipeDown());

        onView(allOf(withText(R.string.tab_pager), isDisplayed())).perform(click());

        addSomeItems(1);
        removeSomeItems(2);

        for (int i = 0; i < 5; i++) container.perform(swipeLeft());
        for (int i = 0; i < 5; i++) container.perform(swipeRight());

        onView(allOf(withText(R.string.tab_recycler), isDisplayed())).perform(click());

        addSomeItems(10);
        removeSomeItems(4);

        for (int i = 0; i < 2; i++) container.perform(swipeUp());
        for (int i = 0; i < 2; i++) container.perform(swipeDown());

    }

    private void removeSomeItems(int num) {
        ViewInteraction item = onView(
                allOf(
                        childAtPosition(allOf(withParent(withId(R.id.content))), 1),
                        isDisplayed()
                )
        );
        for (int i = 0; i < num; i++) {
            item.perform(click());
        }
    }

    private void addSomeItems(int rounds) {
        for (int i = 0; i < rounds; i++) {
            ViewInteraction a = onView(
                    allOf(withId(R.id.a), isDisplayed()));
            a.perform(doubleClick());
            ViewInteraction b = onView(
                    allOf(withId(R.id.b), isDisplayed()));
            b.perform(doubleClick());
            ViewInteraction c = onView(
                    allOf(withId(R.id.c), isDisplayed()));
            c.perform(doubleClick());
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
