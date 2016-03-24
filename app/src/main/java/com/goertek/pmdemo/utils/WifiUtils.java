package com.goertek.pmdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: wifi utils
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-17
 */
public class WifiUtils {


    private static final String TAG = WifiUtils.class.getSimpleName();
    private static final int WIFI_CONNECTING = 0;
    private static final int WIFI_CONNECTED = 1;
    private static final int WIFI_CONNECT_FAILED = -1;
    private WifiManager wifiManager;
    private List<WifiConfiguration> existingConfigs = new ArrayList<>();

    //定义几种加密方式，一种是WEP，一种是WPA，还有没有密码的情况
    public enum WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    public WifiUtils(WifiManager manager) {
        this.wifiManager = manager;
    }


    public boolean connectNet(String SSID, String Password, WifiCipherType Type) {
        WifiConfiguration wifiConfig = this.CreateWifiInfo(SSID, Password, Type);
        if (wifiConfig == null) {
            Log.e(TAG, "the wifiConfig is null");
            return false;
        }
        while (true) {
            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (wifiManager.getWifiState() == 3) {
                break;
            }
        }
        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null) {
            wifiManager.enableNetwork(tempConfig.networkId, true);
        }
        int netID = wifiManager.addNetwork(wifiConfig);
        boolean bRet = wifiManager.enableNetwork(netID, true);
        Log.d(TAG, "enableNetwork status enable=" + bRet);
        boolean connected = wifiManager.reconnect();
        Log.d(TAG, "enableNetwork connected=" + connected);
        return bRet;
    }

    //查看以前是否也配置过这个网络
    private WifiConfiguration IsExsits(String SSID) {
        existingConfigs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (("\"" + SSID + "\"").equals(existingConfig.SSID)) {
                return existingConfig;
            }
        }
        return null;
    }

    public int isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm!=null){
            NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info.getDetailedState() == DetailedState.OBTAINING_IPADDR
                    || info.getDetailedState() == DetailedState.CONNECTING) {
                return WIFI_CONNECTING;
            } else if (info.getDetailedState() == DetailedState.CONNECTED) {
                return WIFI_CONNECTED;
            } else {
                Log.d(TAG, "getDetailedState() == " + info.getDetailedState());
                return WIFI_CONNECT_FAILED;
            }
        }
        return -1;
    }

    private WifiConfiguration CreateWifiInfo(String SSID, String Password, WifiCipherType Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";
        if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
            config.wepKeys[0] = "\"" + "\"";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == WifiCipherType.WIFICIPHER_WEP) {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == WifiCipherType.WIFICIPHER_WPA) {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            // 此处需要修改否则不能自动重联
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }
}
