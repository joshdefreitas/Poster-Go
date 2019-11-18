package com.example.postergo;

import android.os.SystemClock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


/**
 * UI test with Espresso
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PosterGoEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void usernameInputTest() {
        inputUsername();

        onView(withId(R.id.username_prompt))
                .check(matches(withText("Username: Espresso")));
    }

    @Test
    public void openARViewTest() {
        onView(withId(R.id.AR_button))
                .perform(click());

        onView(withId(R.id.ux_fragment))
                .check(matches(isDisplayed()));
    }

    private void inputUsername() {
        onView(withId(R.id.username_input))
                .perform(typeText("Espresso"));

        onView((withId(R.id.username_ok)))
                .perform(click());

        onView(isRoot()).perform(pressBack());
    }


    /**
     * Test suite for Chatroom activity
     */

    @Test
    public void updateChatTest() {
        onView(withId(R.id.chat_button))
                .perform(click());
        onView(withId(R.id.update_button))
                .perform(click());
        onView(withId(R.id.mainMessageView))
                .check(matches(withText(
                    "Taro: Hello Jack!\n\nJack: Hey Taro!\n\nTaro: How's your holiday?\n\nTaro: Have you been anywhere?\n\nJack: Yes, in my dream.\n\nJosh: hi how are you\n\nJosh: hello\n\nJosh: hi\n\n"
                )));

    }

}
