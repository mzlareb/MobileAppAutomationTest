package com.mytaxi.util;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.adapters.DriverAdapter;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class Helpers {

    Context appContext;

    public Helpers(Context appContext) {
        this.appContext = appContext;
    }

    //Login to the App
    public void login(String username, String password) throws Exception {
        waitForElementUntilDisplayedAndEnterText(Espresso.onView((withId(R.id.edt_username))), username);
        waitForElementUntilDisplayedAndEnterText(Espresso.onView((withId(R.id.edt_password))), password);
        waitForElementUntilDisplayedAndClick(Espresso.onView((withId(R.id.btn_login))));
    }

    public String getDriverFromSuggestions(int index) {
        return DriverAdapter.suggestions.get(index).getName();
    }

    public static void waitForElementUntilDisplayedAndClick(ViewInteraction element) throws Exception {
        if (waitForElementUntilDisplayed(element)) element.perform(click());
        else throw new Exception("View not found " + element);
    }

    public static void waitForElementUntilDisplayedAndClick(ViewInteraction element, boolean isContainer) throws Exception {
        if (isContainer) Thread.sleep(1000);
        waitForElementUntilDisplayedAndClick(element);
    }

    public static void waitForElementUntilDisplayedAndEnterText(ViewInteraction element, String text) throws Exception {
        if (waitForElementUntilDisplayed(element)) element.perform(typeText(text));
        else throw new Exception("View not found " + element);
    }

    private static boolean waitForElementUntilDisplayed(ViewInteraction element) {

        try{
         Thread.sleep(2000);   
        } catch (Exception e2){
            e.printStackTrace();   
        }
        int i = 0;
        while (i++ < 10) {
            try {
                element.check(matches(isDisplayed()));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void resetUser() {
        SharedPrefStorage sharedPrefStorage = new SharedPrefStorage(appContext);
        sharedPrefStorage.resetUser();
    }
}
