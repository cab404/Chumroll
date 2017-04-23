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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
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

    @Test
    public void testActivityTest() {

        onView(allOf(withText("ListView"), isDisplayed())).perform(click());

        addSomeItems();
        removeSomeItems();

        onView(allOf(withText("PagerView"), isDisplayed())).perform(click());

        addSomeItems();
        removeSomeItems();

        ViewInteraction pager = onView(
                allOf(withParent(withId(R.id.content)),
                        isDisplayed()));

        for (int i = 0; i < 5; i++) pager.perform(swipeLeft());
        for (int i = 0; i < 5; i++) pager.perform(swipeRight());

        onView(allOf(withText("RecyclerView"), isDisplayed())).perform(click());

        addSomeItems();
        removeSomeItems();
    }

    private void removeSomeItems() {
        ViewInteraction item = onView(
                allOf(
                        childAtPosition(allOf(withParent(withId(R.id.content))), 1),
                        isDisplayed()
                )
        );
        for (int i = 0; i < 5; i++) {
            item.perform(click());
        }
    }

    private void addSomeItems() {
        for (int i = 0; i < 4; i++) {
            ViewInteraction a = onView(
                    allOf(withId(R.id.a), isDisplayed()));
            a.perform(click());
            ViewInteraction b = onView(
                    allOf(withId(R.id.b), isDisplayed()));
            b.perform(click());
            ViewInteraction c = onView(
                    allOf(withId(R.id.c), isDisplayed()));
            c.perform(click());
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
