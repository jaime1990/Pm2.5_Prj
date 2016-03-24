package com.goertek.pmdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.goertek.pmdemo.R;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-17
 */
public class CustomPgDialog extends Dialog{

    private Context context = null;
    private static CustomPgDialog cDialog = null;

    public CustomPgDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public static CustomPgDialog createDialog(Context context){
        cDialog = new CustomPgDialog(context, R.style.CustomProgressDialog);
        cDialog.setContentView(R.layout.layout_loading);
        cDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return cDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (cDialog == null){
            cDialog = createDialog(this.context);
        }
        ImageView imageView = (ImageView) cDialog.findViewById(R.id.img_loading);
        AnimationDrawable aDrawable = (AnimationDrawable) imageView.getBackground();
        if (aDrawable != null && !aDrawable.isRunning()) {
            aDrawable.start();
        }
    }

    //设置标题
   public CustomPgDialog setTitile(String strTitle){
        cDialog.setTitile(strTitle);
        return cDialog;
    }

    //提示内容
    public CustomPgDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)cDialog.findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return cDialog;
    }
}
