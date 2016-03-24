package com.goertek.pmdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.goertek.pmdemo.service.DataSaveService;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-23
 */
public class ServiceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.goertek.pmdemo.destroy")) {
            //TODO
            //在这里写重新启动service的相关操作
            context.startService(new Intent(context, DataSaveService.class));
        }
    }
}
