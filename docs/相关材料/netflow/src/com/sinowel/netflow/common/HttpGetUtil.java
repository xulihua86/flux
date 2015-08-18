package com.sinowel.netflow.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpGetUtil {

    @SuppressWarnings("unchecked")
    public static Map<String, Object> httpGet(String url) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(url);
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
                throw new Exception("HttpStatus is" + statusCode);
            }
            byte[] responseBody = getMethod.getResponseBody();
            String result = new String(responseBody);
            resultMap = (Map<String, Object>) JsonUtil.getInstance().toMap(result);
            getMethod.releaseConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultMap;
    }
    
    public static boolean httpGetBl(String url) {
        boolean bl = false;
        try {
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(url);
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }else{
                getMethod.releaseConnection();
                bl = true;
            }
        } catch (Exception e) {
            bl = false;
            throw new RuntimeException(e);
        }
        return bl;
    }
}
