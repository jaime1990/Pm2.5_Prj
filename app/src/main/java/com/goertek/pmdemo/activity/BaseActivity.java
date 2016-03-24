package com.goertek.pmdemo.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-23
 */
public class BaseActivity extends Activity {

    protected static List<Activity> allActivities = new ArrayList<Activity>();


    public static void logout() {
        for (Activity ac : allActivities) {
            if (ac != null) {
                ac.finish();
            }
        }
        // 清理资源
        System.gc();
        Runtime.getRuntime().gc();
    }
}
