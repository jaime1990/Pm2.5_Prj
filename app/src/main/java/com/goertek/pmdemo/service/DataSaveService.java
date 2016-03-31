package com.goertek.pmdemo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.goertek.bean.CityAir;
import com.goertek.pmdemo.R;
import com.goertek.pmdemo.activity.LoadingActivity;
import com.goertek.pmdemo.task.DownloadTask;
import com.goertek.pmdemo.utils.CommonUtils;
import com.goertek.pmdemo.utils.Constants;
import com.goertek.pmdemo.utils.DbUtil;

import java.net.URLEncoder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-23
 */
public class DataSaveService extends Service {

    private static final String TAG = DataSaveService.class.getSimpleName();
    private static final long mTaskTime = 8 * 60 * 60 * 1000; //每8个小时执行一次保存空气数据
    private static long initDelay = 0;
    //    private static final long mTaskTime = 10 * 1000; //10s测试
    private Context mContext;
    private ScheduledExecutorService updateExcutor;
    private DownloadTask mTask;
    private String param;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        updateExcutor = Executors.newScheduledThreadPool(1);
        /**
         * 规定每天执行三次任务，分别在凌晨2:00, 上午10：00和下午18:00
         */
        initDelay = CommonUtils.getTimeMillis("02:00:00") - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : mTaskTime + initDelay;
        if(initDelay < 0){
            initDelay = CommonUtils.getTimeMillis("10:00:00") - System.currentTimeMillis();
            initDelay = initDelay > 0 ? initDelay : mTaskTime + initDelay;
        }
        if(initDelay < 0){
            initDelay = CommonUtils.getTimeMillis("18:00:00") - System.currentTimeMillis();
            initDelay = initDelay > 0 ? initDelay : mTaskTime + initDelay;
        }
        //定时执行任务
        if (updateExcutor != null) {
            updateExcutor.scheduleAtFixedRate(doRefresh, initDelay, mTaskTime, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //保证service不被杀死
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LoadingActivity.class), 0);
        Notification nf = new Notification.Builder(mContext).
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
    private Runnable doRefresh = new Runnable() {
        @Override
        public void run() {
            try {
                param = "key=" + URLEncoder.encode(Constants.API_KEY, "UTF-8");
                param += "&city=" + URLEncoder.encode("北京", "UTF-8"); //默认获取北京市的空气信息
            } catch (Exception e) {
                e.printStackTrace();
            }
            mTask = new DownloadTask(mContext);
            mTask.setOnDataFinishedListener(new DownloadTask.OnDataFinishedListener() {
                @Override
                public void onDataSuccessed(Object data) {
                    CityAir cityAir = (CityAir) data;
                    long rowId = DbUtil.getInstance(mContext).saveAir(cityAir);
                    Log.w(TAG, " save successed -----> rowId = " + rowId);
                }

                @Override
                public void onDataFailed() {
                    Log.w(TAG, " data get failed......");
                }
            });
            mTask.execute(param);
            Log.i(TAG, "get data in service!the time is " + mTaskTime);
        }
    };

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (updateExcutor != null && !updateExcutor.isTerminated()) {
            updateExcutor.shutdownNow();
            updateExcutor = null;
        }
        if (doRefresh != null) {
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
