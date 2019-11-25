package com.example.postergo;


import java.time.LocalTime;



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
import static org.hamcrest.core.StringContains.containsString;


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
                    containsString("Taro: Hello Jack!\n\nJack: Hey Taro!\n\nTaro: How's your holiday?\n\nTaro: Have you been anywhere?\n\nJack: Yes, in my dream."
                ))));

    }

    @Test
    public void newMessageTest(){

        onView(withId(R.id.chat_button))
                .perform(click());
        onView(withId(R.id.enter_message))
                .perform(typeText("Test Message"));
        onView(withId(R.id.send))
                .perform(click());
        onView(withId(R.id.messasge))
                .check(matches(withText("Test Message")));

    }

    /**
     * Test suite for RecommendationsNew activity
     */

    @Test
    public void getRecommendationsDescriptionTest(){
        onView(withId(R.id.recommendations_button))
                .perform(click());
        onView(withId(R.id.button))
                .perform(click());
        onView(withId(R.id.descriptionView))
                .check(matches(withText(containsString(" Forever alone in a crowd, failed comedian Arthur Fleck seeks connection as he walks the streets of Gotham City. Arthur wears two masks -- the one he paints for his day job as a clown, and the guise he projects in a futile attempt to feel like he's part of the world around him. Isolated, bullied and disregarded by society, Fleck begins a slow descent into madness as he transforms into the criminal mastermind known as the Joker."))));
    }


    /*
    non functional requirements test
     */

    @Test
    public void chatTimeTest(){
        double t1 = LocalTime.now().getNano();

        onView(withId(R.id.chat_button))
                .perform(click());
        onView(withId(R.id.enter_message))
                .perform(typeText("Test Message2"));
        onView(withId(R.id.send))
                .perform(click());
        onView(withId(R.id.messasge))
                .check(matches(withText("Test Message2")));

        double t2 = LocalTime.now().getNano();

        assert(t2-t1<500);
    }

}
