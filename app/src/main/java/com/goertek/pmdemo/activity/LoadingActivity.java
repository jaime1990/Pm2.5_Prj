package com.goertek.pmdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.goertek.bean.CityAir;
import com.goertek.pmdemo.R;
import com.goertek.pmdemo.service.DataSaveService;
import com.goertek.pmdemo.task.DownloadTask;
import com.goertek.pmdemo.utils.Constants;

import java.net.URLEncoder;

/**
 * Description: loading to main activity, here excute async task to connect wifi and get data
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-17
 */
public class LoadingActivity extends Activity {

    private static final String TAG = LoadingActivity.class.getSimpleName();
    private DownloadTask mTask = null;
    private ImageView imgLoading;
    private AnimationDrawable mAnim;
    private String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        imgLoading = (ImageView) findViewById(R.id.img_loading);
        mAnim = (AnimationDrawable) imgLoading.getBackground();
        excuteTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, DataSaveService.class));
    }

    private void excuteTask() {
        try {
            param = "key=" + URLEncoder.encode(Constants.API_KEY, "UTF-8");
            param +="&city=" + URLEncoder.encode("北京", "UTF-8"); //默认获取北京市的空气信息
        }catch (Exception e){
            e.printStackTrace();
        }
        mTask = new DownloadTask(this);
        mTask.setOnDataFinishedListener(new DownloadTask.OnDataFinishedListener() {
            @Override
            public void onDataSuccessed(Object data) {
                Log.w(TAG, "onDataSuccessed  data(pm2.5) = " + ((CityAir) data).getPm2_5());
                CityAir cityAir = (CityAir) data;
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                intent.putExtra("cityair", cityAir);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                LoadingActivity.this.finish();
            }
            @Override
            public void onDataFailed() {
                Toast.makeText(LoadingActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        mTask.execute(param);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (mAnim != null && !mAnim.isRunning()) {
                mAnim.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mAnim != null && mAnim.isRunning()) {
            mAnim.stop();
        }
        super.onDestroy();
    }
}
