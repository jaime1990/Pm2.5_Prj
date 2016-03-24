package com.goertek.pmdemo.utils;

import android.content.Context;

import org.apache.http.util.EncodingUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: operate file
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-18
 */
public class FileUtils {


    //写数据到/data/data/package/file/下
    public static void writeFileInApp(Context context, String fileName,String writestr) {
        try{
            FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //读数据从/data/data/package/file/
    public static String readFileFromApp(Context context, String fileName){
        String res="";

        try{
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;

    }


    //写数据到SD中的文件
    public void writeFileSdcardFile(String fileName,String write_str) {

        try{
            FileOutputStream fout = new FileOutputStream(fileName);
            byte [] bytes = write_str.getBytes();
            fout.write(bytes);
            fout.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    //读SD中的文件
    public String readFileSdcardFile(String fileName) {
        String res="";
        try{
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 把输入流的内容转换成字符串
     * @param is
     * @return null解析失败， string读取成功
     */
    public static String readStream2Str(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len ;
            while( (len = is.read(buffer))!=-1){
                baos.write(buffer, 0, len);
            }
            is.close();
            String temptext = new String(baos.toByteArray());
            if(temptext.contains("charset=gb2312")){//解析meta标签
                return new String(baos.toByteArray(),"gb2312");
            }else{
                return new String(baos.toByteArray(),"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
