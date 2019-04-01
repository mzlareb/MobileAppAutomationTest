package com.mytaxi.android_demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.mytaxi.android_demo.activities.MainActivity;

import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

import android.support.test.rule.ActivityTestRule;

import com.mytaxi.util.Helpers;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UITests {

    //Data can be moved to Test Data File eg. JSON. Not done here to avoid complexity
    private String username = "crazydog335";
    private String password = "venture";
    private String searchKeyword = "sa";

    private static Helpers helpers;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    private MainActivity mActivity = null;
    static Context appContext = InstrumentationRegistry.getTargetContext();

    @BeforeClass
    public static void setup() {
        helpers = new Helpers(appContext);
    }

    @Before
    public void setUserData() {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void searchDriverFromSuggestionsAndCall() {
        try {
            helpers.login(this.username, this.password);

            //Search the Driver
            helpers.waitForElementUntilDisplayedAndEnterText(Espresso.onView((withId(R.id.textSearch))), searchKeyword);

            String secondDriverName = helpers.getDriverFromSuggestions(1);

            //Click on Second Driver
            helpers.waitForElementUntilDisplayedAndClick(Espresso.onView(withText(secondDriverName))
                    .inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                    .perform(scrollTo()), true);

            //Match if correct Driver Profile has been opened
            onView(withId(R.id.textViewDriverName)).check(matches(withText(secondDriverName)));

            //Call the Driver
            helpers.waitForElementUntilDisplayedAndClick(onView(withId(R.id.fab)));
        } catch (Exception e) {
            e.printStackTrace();
            helpers.resetUser();
            Assert.fail();
        }
    }

    @After
    public void teardown() throws InterruptedException {
        helpers.resetUser();
    }
}
