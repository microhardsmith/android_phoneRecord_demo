package cn.hust.atry;


import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 71084 on 2018/12/28.
 */




public class SendThread {

    public static String getRemoteInfo(String mobileCode, String userID) throws Exception {

        try {
            URL url = new URL("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode=" + mobileCode + "&userID=" + userID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 结果码=200
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    boas.write(buffer, 0, len);
                }
                String result = boas.toString();
                Log.d("communication","the city result is :"+result);
                boas.close();
                is.close();
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Internet Error";
    }


}
