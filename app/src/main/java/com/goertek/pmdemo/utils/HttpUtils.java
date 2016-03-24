package com.goertek.pmdemo.utils;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Description: okhttp needed to get data from net
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-17
 */
public class HttpUtils {

    private static final String TAG = HttpUtils.class.getSimpleName();
    private static String result = "";
    private static final OkHttpClient mClient = new OkHttpClient();

    /**
     * http post request
     *
     * @param uri
     * @param param
     * @return
     */
    public static String httpPostRequest(String uri, String param) {
        URL url;
        try {
            url = new URL(uri);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true);   //自动执行HTTP重定向
            urlConn.setRequestProperty("Constants-Type",
                    "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();    //输出缓存
            out.close();    //关闭数据输出流
            // 判断是否响应成功
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream()); // 获得读取的内容
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine;
                while ((inputLine = buffer.readLine()) != null) {
                    result += inputLine;
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();   //断开连接
            Log.w(TAG, "httpPostRequest --> result = " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * http get request
     *
     * @param uri
     * @return
     */
    public static String httpGetRequest(String uri) {
        URL url;
        try {
            url = new URL(uri);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();  //创建一个HTTP连接
            InputStreamReader in = new InputStreamReader(urlConn.getInputStream()); // 获得读取的内容
            BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
            String inputLine;
            //通过循环逐行读取输入流中的内容
            while ((inputLine = buffer.readLine()) != null) {
                result += inputLine + "\n";
            }
            in.close(); //关闭字符输入流对象
            urlConn.disconnect();   //断开连接
            Log.w(TAG, "httpPostRequest --> result = " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 可惜的是okhttp暂时无法与AsyncTask一起使用 fuck
     *
     * @param url
     * @param apikey
     * @param city
     * @return
     */
    public static String getDataFromNet(String url, String apikey, String city) {

        mClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mClient.setReadTimeout(30, TimeUnit.SECONDS);
        FormEncodingBuilder mBuilder = new FormEncodingBuilder();
        mBuilder.add("key", apikey);
        mBuilder.add("city", city);

        Request request = new Request.Builder().url(url)
                .post(mBuilder.build())
                .build();

        Call mCall = mClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "request error..." + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                result = response.body().string();
                Log.w(TAG, "response --> result = " + result);
            }
        });
        return result;
    }
}
