package com.goertek.pmdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goertek.bean.CityAir;
import com.goertek.pmdemo.R;
import com.goertek.pmdemo.utils.Constants;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_PARAM = "CITY_AIR";
    private RelativeLayout pmLayout;
    private TextView txtProvince, txtPm2_5, txtPlevel, txtUpdatetime;
    private CityAir cityAir;
    private Handler handler;
    private updateRunnable mRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allActivities.add(this);
        setContentView(R.layout.activity_main);
        handler  = new Handler();
        cityAir = (CityAir) getIntent().getSerializableExtra(EXTRA_PARAM);
        initViews();
    }

    private void initViews() {
        pmLayout = (RelativeLayout) findViewById(R.id.pm_main);
        txtProvince = (TextView) findViewById(R.id.id_txt_province);
        txtPm2_5 = (TextView) findViewById(R.id.id_txt_pm2_5);
        txtPlevel = (TextView) findViewById(R.id.id_txt_pollution_level);
        txtUpdatetime = (TextView) findViewById(R.id.id_txt_update_time);
        if(cityAir != null && cityAir.getRanking() > 0){
            setPmBackground(cityAir.getQuality());
            String pm = cityAir.getPm2_5().substring(0, cityAir.getPm2_5().lastIndexOf("μ"));
            Log.w(TAG, "pm = " + pm);
            if("空气质量pm2.5".equals(cityAir.getProvinceName())){
                txtProvince.setText(cityAir.getCityName());
            }else{
                txtProvince.setText(cityAir.getProvinceName()+"."+cityAir.getCityName());
            }
            txtPm2_5.setText(cityAir.getPm2_5().substring(0, cityAir.getPm2_5().lastIndexOf("μ")));
            txtPlevel.setText("AQ："+cityAir.getQuality());
            txtUpdatetime.setText(cityAir.getUpdateTime());
        }
        txtPm2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setPmBackground(String quality) {
        switch (quality){
            case "优":
                updatePmBG(); //定时更换背景图片
                break;
            case "良":
                pmLayout.setBackgroundResource(R.mipmap.ic_weather_haze_good);
                break;
            case "轻度污染":
                pmLayout.setBackgroundResource(R.mipmap.ic_weather_haze_mild);
                break;
            case "中度污染":
                pmLayout.setBackgroundResource(R.mipmap.ic_weather_haze_medium);
                break;
            case "重度污染":
                pmLayout.setBackgroundResource(R.mipmap.ic_weather_haze_serious);
                break;
            case "严重污染":
                pmLayout.setBackgroundResource(R.mipmap.ic_weather_haze_sever);
                break;
        }
    }

    /**
     * 定时更新背景图片
     */
    private void updatePmBG() {
        mRunnable = new updateRunnable(Constants.resId);
        handler.postDelayed(mRunnable , 3000);
    }

    class updateRunnable implements Runnable{

        int[] resid;
        int id = 0;
        public updateRunnable(int[] resId) {
            this.resid = resId;
        }
        @Override
        public void run() {
            if(id == 6){
                id = 0;
            }else{
                id ++;
            }
            pmLayout.setBackgroundResource(resid[id]);
            handler.postDelayed(this, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mRunnable);
    }
}
