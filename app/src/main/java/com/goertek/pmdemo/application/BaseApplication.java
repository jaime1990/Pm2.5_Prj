package com.goertek.pmdemo.application;

import android.app.Application;
import android.content.Context;

import com.goertek.dao.DaoMaster;
import com.goertek.dao.DaoMaster.OpenHelper;
import com.goertek.dao.DaoSession;
import com.goertek.pmdemo.utils.Constants;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-23
 */
public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public BaseApplication(){}
    public static synchronized BaseApplication getInstance(){
        if(mInstance == null){
            mInstance = new BaseApplication();
        }
        return mInstance;
    }

    //app创建时执行
    @Override
    public void onCreate() {
        super.onCreate();
        getInstance();
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    //结束时执行，一般不执行，只在模拟环境中执行
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //低内存时执行
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    //清理内存时执行
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
