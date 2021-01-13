package com.taozen.quithabit;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirstScreenActivityTest {

    @Rule
    public ActivityTestRule<FirstScreenActivity> activityActivityTestRule
            = new ActivityTestRule<>(FirstScreenActivity.class);
    private FirstScreenActivity firstScreenActivity = null;

    @Before
    public void setUp() throws Exception {
        firstScreenActivity = activityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){

        View view = firstScreenActivity.findViewById(R.id.tvTitleId);
        assertNotNull(view);

    }

    @After
    public void tearDown() throws Exception {
        firstScreenActivity = null;
    }


}