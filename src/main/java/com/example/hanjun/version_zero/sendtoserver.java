package com.example.hanjun.version_zero;

import android.app.ProgressDialog;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by HANJUN on 2016-01-31.
 */

public class sendtoserver extends AsyncHttpResponseHandler {

    ProgressDialog dialog;

    /**통신 시작시에 실행된다.*/
    @Override
    public void onStart() {
            /*
            dialog = new ProgressDialog(MyReceiver.this);
            dialog.setMessage("잠시만 기다려주세요...");
            dialog.setCancelable(false);
            dialog.show();
            */
    }

    /**통신 접속 실패시 호출된다.
     * @param stateCode     상태코드
     * @param header        HTTP Header
     * @param body          HTTP body
     * @param error         에러정보 객체
     */
    @Override
    public void onFailure(int stateCode, Header[] header, byte[] body, Throwable error) {
            /*
            String errMsg = "State Code :" + stateCode + "\n";
            errMsg += "Error Message :" + error.getMessage();
            textView.setText(errMsg);
            */
    }

    /**통신 접속 성공시 호출된다.
     * @param stateCode     상태코드. (정상결과일 경우 200)
     * @param header        HTTP Header
     * @param body          HTTP Body (브라우저에 보여지는 내용)
     */
    @Override
    public void onSuccess(int stateCode, Header[] header, byte[] body) {
            /*
            try {
                String result = new String(body, "UTF-8");
                if (result.equals("OK")) {
                    textView.setText("로그인 성공");
                } else {
                       textView.setText("로그인 실패");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            */
    }

    /**성공, 실패 여부에 상관 없이 통신이 종료되면 실행된다.*/
    @Override
    public void onFinish() {
        //dialog.dismiss();
        //dialog = null;
    }
} //end class