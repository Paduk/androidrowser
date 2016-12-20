package com.example.hanjun.version_zero;


import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by HANJUN on 2016-01-30.
 */

public class GCMInfo {
    private final String URL_POST = "http://gorapaduk.dothome.co.kr/project/regid.php";

    AsyncHttpClient clie;
    sendtoserver httpRes;

    /**
     * Project Id registered to use GCM.
     * 단말 등록을 위한 필요한 ID
     */
    public static final String PROJECT_ID = "435114984007";

    /**
     * Google API Key generated for service access
     * 서버 : 푸시 메시지 전송을 위해 필요한 KEY
     */
    public static final String GOOGLE_API_KEY = "AIzaSyD9GMBc6WkIpooY-XaoDF5VvTOpr7HlTYc";

    /**
     * Registration ID for this device
     * 단말 등록 후 수신한 등록 ID
     */
    public static String RegistrationId = "";
/*
    public void sendId(String regId) {
        clie = new AsyncHttpClient();
        httpRes = new sendtoserver();

        Log.d("GCMBroadcastReceiver", "Mainactivity before");
        RequestParams params = new RequestParams();
        params.put("regidid", regId);
        clie.post("http://gorapaduk.dothome.co.kr/project/regid.php", params, httpRes);
        Log.d("GCMBroadcastReceiver", "Mainactivity");
    }
*/
}

