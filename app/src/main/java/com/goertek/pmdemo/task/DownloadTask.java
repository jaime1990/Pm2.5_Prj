package com.goertek.pmdemo.task;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.goertek.bean.CityAir;
import com.goertek.pmdemo.activity.LoadingActivity;
import com.goertek.pmdemo.utils.Constants;
import com.goertek.pmdemo.utils.HttpUtils;
import com.goertek.pmdemo.utils.JsonUtils;
import com.goertek.pmdemo.utils.WifiUtils;

/**
 * Description: task used to get data every time
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-18
 */
public class DownloadTask extends AsyncTask<String, Integer, CityAir> {

    private static final String TAG = DownloadTask.class.getSimpleName();
    private LoadingActivity mActivity;

    private WifiUtils wUtils;
    private WifiManager mWifiManager;
    private boolean connect = false;
    private boolean isConnect = false;
    private OnDataFinishedListener dataListener;

    public DownloadTask(Activity activity){
        this.mActivity = (LoadingActivity) activity;
        mWifiManager = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        wUtils = new WifiUtils(mWifiManager);
    }

    public void setOnDataFinishedListener(
            OnDataFinishedListener onDataFinishedListener) {
        this.dataListener = onDataFinishedListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.w(TAG, " onPreExecute...");
    }

    @Override
    protected CityAir doInBackground(String... params) {
        Log.w(TAG, "doInBackground....  ");
        connectWifi();
        if(isConnect && params[0] != null){
            String result =  HttpUtils.httpPostRequest(Constants.AQL_URL, params[0]);
            CityAir cityAir = JsonUtils.json2Entity(result);
            return cityAir;
        }
        return null;
    }


    @Override
    protected void onPostExecute(CityAir cityAir) {
        Log.w(TAG, " onPostExecute-->   json = " + cityAir);
        if(cityAir != null && cityAir.getId() != 0){
            dataListener.onDataSuccessed(cityAir);
        }else{
            dataListener.onDataFailed();
        }
    }

    //wifi连接
    private void connectWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
        ConnectWiFi();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (1 == wUtils.isNetworkAvailable(mActivity)) {
            isConnect = true;
        } else {
            wUtils.connectNet(Constants.wifi_SSID, Constants.wifi_PWD, WifiUtils.WifiCipherType.WIFICIPHER_WPA);
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (1 == wUtils.isNetworkAvailable(mActivity)) {
                    isConnect = true;
                    break;
                }
            }
        }
    }

    private void ConnectWiFi() {
        connect = wUtils.connectNet(Constants.wifi_SSID, Constants.wifi_PWD, WifiUtils.WifiCipherType.WIFICIPHER_WPA);
        Log.e(TAG, "connect state : " + connect);
        if (1 == wUtils.isNetworkAvailable(mActivity) && connect == true) {
            isConnect = connect;
        }
        Log.i(TAG, "isconnect : " + isConnect);
    }

    /**
     * 数据加载回调接口
     */
    public interface OnDataFinishedListener {
        void onDataSuccessed(Object data);
        void onDataFailed();
    }
}
