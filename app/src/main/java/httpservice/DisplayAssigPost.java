package httpservice;

/**
 * Created by ASUS on 2016/12/4.
 */

import com.example.delitto.myapplication.Bean.MainListData;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import gson.displayAssig;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


import gson.Gsonanalyze;

public class DisplayAssigPost
{
    // 请求服务器端的url
    private static String PATH = "http://123.207.237.45/webtt/DisplayAssigAction";
    private static URL url;

    public DisplayAssigPost() {
        // TODO Auto-generated constructor stub
    }

    static {
        try {
            url = new URL(PATH);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static String executeHttpPost(String assigS) {

        try {
            // 发送指令和信息
            Map<String, String> params = new HashMap<String, String>();
            params.put("assigS", assigS);
            /*params.put("page",page);*/
            return sendPostMessage(params, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param params
     *            填写的url的参数
     * @param encode
     *            字节编码
     * @return
     */
    public static String sendPostMessage(Map<String, String> params,
                                         String encode) {
        // 作为StringBuffer初始化的字符串
        StringBuffer buffer = new StringBuffer();
        try {
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    // 完成转码操作
                    buffer.append(entry.getKey()).append("=").append(
                            URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
                }
                buffer.deleteCharAt(buffer.length() - 1);
            }
            // System.out.println(buffer.toString());
            // 删除掉最有一个&

           /* System.out.println("-->>"+buffer.toString());*/
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);// 表示从服务器获取数据
            urlConnection.setDoOutput(true);// 表示向服务器写数据
            // 获得上传信息的字节大小以及长度
            byte[] mydata = buffer.toString().getBytes();
            // 表示设置请求体的类型是文本类型
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(mydata.length));
            // 获得输出流,向服务器输出数据
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(mydata,0,mydata.length);
            outputStream.close();
            // 获得服务器响应的结果和状态码
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                return changeInputStream(urlConnection.getInputStream(), encode);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将一个输入流转换成指定编码的字符串
     *
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputStream(InputStream inputStream,
                                            String encode) {
        // TODO Auto-generated method stub
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }
   public static void main(String[] args)
    {
        DisplayAssigPost po=new DisplayAssigPost();
        displayAssig field=new displayAssig();
        Gson gson=new Gson();
       /*JsonArray jsonArray=new Gsonanalyze(). getarray(po.executeHttpPost("可接"));*/
        ArrayList<MainListData> list = gson.fromJson(po.executeHttpPost("可接"), new
                TypeToken<ArrayList<MainListData>>() {
                }.getType());
        System.out.print(list);

        /*System.out.print(jsonArray);*/
        /*Gson gson = new Gson();
        while (it.hasNext()) {
            JsonElement ee = (JsonElement) it.next();
            field = gson.fromJson(ee, gson.displayAssig.class);
            //表示服务器端返回的结果
              System.out.print(field.getuID());
            System.out.print(field.getuN());
            System.out.println(field.getAssigID());
            System.out.println(field.getAssigT());
            System.out.println(field.getAssigRM());
            System.out.print(field.getAssigTi());
            System.out.print(field.getAssigCPN());
        }
           /* System.out.print(field.getuID());
            System.out.print(field.getuN());
            System.out.println(field.getAssigID());
            System.out.println(field.getAssigT());
            System.out.println(field.getAssigRM());
            System.out.print(field.getAssigTi());
            System.out.print(field.getAssigCPN());
        System.out.print(po.executeHttpPost("可接","1"));*/

    }
}
