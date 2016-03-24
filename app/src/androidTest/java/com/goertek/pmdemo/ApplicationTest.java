package com.goertek.pmdemo;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.goertek.pmdemo.utils.Constants;
import com.goertek.pmdemo.utils.HttpUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "jaime";

    public ApplicationTest() {
        super(Application.class);
    }

    public void testGetData(){

        String res = HttpUtils.getDataFromNet(Constants.AQL_URL, Constants.API_KEY, "北京");
        Log.d(TAG, "res = "+res);
    }
}