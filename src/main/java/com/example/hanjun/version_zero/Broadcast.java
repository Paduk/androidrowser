package com.example.hanjun.version_zero;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.net.URLDecoder;
import java.util.Date;

public class Broadcast extends WakefulBroadcastReceiver {
    private static final String TAG = "MainActivity";
    private GPSInform gps;

    private NotificationManager GCMnMM;
    private Notification GCMnNoti;
    private NotificationManager LisnMM;
    private Notification LisnNoti;

    private NotificationManager nMM;
    private NotificationManager nMM1;
    private Notification nNoti;
    private Notification nNoti1;

   // private Makeconstant myApp;
    DbOpenHelper dbManager;
    private static String mLastState;
    private static String Listenstate="true";
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private final String URL_POST = "http://gorapaduk.dothome.co.kr/project/";
    AsyncHttpClient client;
    sendtoserver httpResponse;

    public Broadcast() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //myApp.getid();
        dbManager = new DbOpenHelper(context, "Data.db", null, 1);

        String packListener = intent.getStringExtra("package");
        Log.d(TAG, "onReceive() 호출 됨, getaction : " + intent.getAction() +" packListener: "+packListener);
        String action = intent.getAction();
        String flag=""; // 카톡중복방지
        //myApp = getApplication();
        // 서버 전송 함수
        client = new AsyncHttpClient();
        httpResponse = new sendtoserver();
        // 전화왔을때
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if(state != null && state.equals("IDLE")){
            mLastState = "false"; // 전화변수 초기화 함으로써 오류 해결
        }

        if (action.equals("com.google.android.c2dm.intent.RECEIVE")) { // 푸시 메시지 수신 시
            String rawData = intent.getStringExtra("data");        // 서버에서 보낸 data 라는 키의 value 값
            String title = intent.getStringExtra("title");        // 서버에서 보낸 data 라는 키의 value 값
            String content = intent.getStringExtra("content");        // 서버에서 보낸 data 라는 키의 value 값
            String rawname = intent.getStringExtra("name");
            String data = "";
            String name = "";
            try {
                data = URLDecoder.decode(rawData, "UTF-8");
                name = URLDecoder.decode(rawname, "UTF-8");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if(data.equals("map")) // GPS 정보 관리
            {
                gps = new GPSInform(context); // context에 대한 공부가 필요하다
                RequestParams params = new RequestParams();
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Log.d(TAG,"in broad  lat: "+latitude);
                    //파라미터 정보 추가 (key, value)
                    params.put("id", dbManager.getId());
                    params.put("latitude", latitude);
                    params.put("longitude", longitude);
                    client.post(URL_POST + "gps.php", params, httpResponse);
                } else {
                    params.put("id", dbManager.getId());
                    params.put("onoff", 1);
                    client.post(URL_POST + "gps.php", params, httpResponse);
                    // GPS 를 사용할수 없으므로
                    // gps.showSettingsAlert();
                }
            }
            else if(data.equals("push")) {
                GCMnMM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                GCMnNoti = new NotificationCompat.Builder(context.getApplicationContext())
                        .setContentTitle(title)
                        .setContentText(content)
                        .setSmallIcon(R.drawable.hereat)
                        .setTicker(title)
                        .build();

                GCMnMM.notify(5555, GCMnNoti);
                // 액티비티로 전달
            }
            else if(data.equals("phoneaddr")) // 전화번호 정보 관리
            {
                String num = dbManager.getnumber(name);
                //String num = findName(name);
                //Log.d(TAG,"come here : "+num);
                RequestParams params = new RequestParams();
                //파라미터 정보 추가 (key, value)
                params.put("id", dbManager.getId());
                params.put("name", name);
                params.put("number", num);
                client.post(URL_POST + "getaddr.php", params, httpResponse);
                // name으로 비교

            }
        }
        if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction()))
        {
            // SMS 메시지를 파싱합니다.
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[])bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            for(int i = 0; i < messages.length; i++) {
                // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
            }
            // SMS 수신 시간 확인
            Date curDate = new Date(smsMessage[0].getTimestampMillis());
            Log.d(TAG, curDate.toString());
            // SMS 발신 번호 확인
            String origNumber = smsMessage[0].getOriginatingAddress();
            // SMS 메시지 확인
            String message = smsMessage[0].getMessageBody().toString();
            String sendername = dbManager.getname(origNumber);
            Log.d(TAG, "발신자 : "+origNumber+", 내용 : " + message);


            nMM1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nNoti1 = new NotificationCompat.Builder(context.getApplicationContext())
                    .setContentTitle(origNumber)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.hello)
                    .setTicker(message)
                    .build();

            nMM1.notify(7777, nNoti1);
            //파라미터 정보를 저장할 수 있는 객체
            //--> import com.loopj.android.http.RequestParams;
            RequestParams params = new RequestParams();
            //파라미터 정보 추가 (key, value)
           // params.put("id", myApp.getid());
            params.put("id", dbManager.getId());
            params.put("type", "문자메시지");
            params.put("number", origNumber);
            params.put("name", sendername);
            params.put("text", message);
            client.post(URL_POST+"test.php", params, httpResponse);
        }
        if(TelephonyManager.EXTRA_STATE_RINGING.equals(state))
        {

            if (state.equals(mLastState)) {
                return;

            } else {
                mLastState = state;
            }

            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            final String phone_number = PhoneNumberUtils.formatNumber(incomingNumber);
            String sendername = dbManager.getname(incomingNumber);
            nMM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nNoti = new NotificationCompat.Builder(context.getApplicationContext())
                    .setContentTitle("Call from : "+phone_number)
                    .setContentText(phone_number)
                    .setSmallIcon(R.drawable.hello)
                    .setTicker(phone_number)
                    .build();

            nMM.notify(8888, nNoti);
            RequestParams params = new RequestParams();
            //파라미터 정보 추가 (key, value)
           // params.put("id", myApp.getid());
            params.put("id", dbManager.getId());
            params.put("type", "전화");
            params.put("number", incomingNumber);
            params.put("name", sendername);
            params.put("text", "Calling");
            client.post(URL_POST+"test.php", params, httpResponse);

        }

        if(packListener != null && packListener.equals("com.kakao.talk"))
        {
            Log.d(TAG,"kakao come");
            String titleListener = intent.getStringExtra("title");
            String textListener = intent.getStringExtra("text");
            String num = dbManager.getnumber(titleListener);

            if(num.equals("Not exists"))
            {
                num = "없는 번호";
            }
            if(textListener.equals(flag)) return;
            else
                flag = textListener;
            Log.d(TAG, packListener + "," + titleListener + "," + textListener);

            RequestParams params = new RequestParams();
            //파라미터 정보 추가 (key, value)
          //  params.put("id", myApp.getid());
            params.put("id", dbManager.getId());
            params.put("type", "카카오톡");
            params.put("number", num);
            params.put("name", titleListener);
            params.put("text", textListener);
            client.post(URL_POST+"test.php", params, httpResponse);
        }

    }
}
