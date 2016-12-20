package com.example.hanjun.version_zero;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by hanjun on 2016-02-10.
 */
public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Log.d("GCMBroadcastReceiver","1");
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {

                finish();       // 3 초후 이미지를 닫아버림
            }
        }, 3000);
        Log.d("GCMBroadcastReceiver", "2");
    }
}
