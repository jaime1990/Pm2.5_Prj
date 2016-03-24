package com.goertek.pmdemo.service;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.goertek.bean.CityAir;
import com.goertek.pmdemo.R;
import com.goertek.pmdemo.activity.LoadingActivity;
import com.goertek.pmdemo.task.DownloadTask;
import com.goertek.pmdemo.utils.DbUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-23
 */
public class DataSaveService extends Service {

    private static final String TAG = DataSaveService.class.getSimpleName();
    private static final long mTaskTime = 6 * 60 * 60 * 1000; //每四个小时执行一次保存空气数据
    private Activity mActivity;
    private Timer updateTimer;
    private DownloadTask mTask;

    @Override
    public void onCreate() {
        super.onCreate();
        mActivity = (Activity) getApplicationContext();
        updateTimer = new Timer("airSave");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //定时执行任务
        if(updateTimer != null){
            updateTimer.scheduleAtFixedRate(doRefresh, 0, mTaskTime);
        }

        //保证service不被杀死
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LoadingActivity.class), 0);
        Notification nf = new Notification.Builder(mActivity).
                setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("TickerText:" + "您有新短消息，请注意查收！")
                .setContentTitle(getString(R.string.app_name))
                .setContentText("This is the notification message")
                .setContentIntent(pendingIntent).setNumber(1).build();

        nf.flags |= Notification.FLAG_AUTO_CANCEL;
        startForeground(0x111, nf);
        return START_STICKY;
    }

    /**
     * 定时保存数据
     */
    private TimerTask doRefresh = new TimerTask() {
        @Override
        public void run() {
            mTask = new DownloadTask(mActivity);
            mTask.setOnDataFinishedListener(new DownloadTask.OnDataFinishedListener() {
                @Override
                public void onDataSuccessed(Object data) {
                    CityAir cityAir = (CityAir) data;
                    long rowId = DbUtil.getInstance(mActivity).saveAir(cityAir);
                    Log.w(TAG, " save successed -----> rowId = "+rowId);
                }
                @Override
                public void onDataFailed() {
                    Log.w(TAG, " data get failed......");
                }
            });
            mTask.execute();
            updateTimer.scheduleAtFixedRate(doRefresh, 0, mTaskTime);
            Log.i(TAG, "get data in service!the time is " + mTaskTime);
        }
    };

    @Override
    public void onDestroy() {
        stopForeground(true);
        if(updateTimer != null){
            updateTimer.cancel();
            updateTimer = null;
        }
        if(doRefresh != null){
            doRefresh.cancel();
            doRefresh = null;
        }
        Intent intent = new Intent("com.goertek.pmdemo.destroy");
        sendBroadcast(intent);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
