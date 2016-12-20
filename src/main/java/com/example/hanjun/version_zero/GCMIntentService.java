package com.example.hanjun.version_zero;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GCMIntentService extends IntentService {
    private static final String TAG = "GCMIntentService";
    /**
     * 생성자
     */
    public GCMIntentService() {
        super(TAG);

        Log.d(TAG, "GCMIntentService() called.");
    }

    /*
     * 전달받은 인텐트 처리
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        Log.d(TAG, "action : " + action);

    }
}
