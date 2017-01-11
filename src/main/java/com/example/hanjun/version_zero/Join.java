package com.example.hanjun.version_zero;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.apache.http.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Join extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private final String URL_POST = "http://gorapaduk.dothome.co.kr/project/id.php";
    private final String URL_POST2 = "http://gorapaduk.dothome.co.kr/project/Istheresameid.php";
    AsyncHttpClient client;
    sendtoserver httpResponse;
    idcheck idcheckhttp;
    Bundle extraBundle1;
    Bundle extraBundle;
    public String regid;
    int repeatbtnclicked = 0;
    DbOpenHelper dbManager;

    private static String OAUTH_CLIENT_ID = "ID";
    private static String OAUTH_CLIENT_SECRET = "Secret key";
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";

    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;
    private TextView mApiResultText;
    private OAuthLoginButton mOAuthLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbManager = new DbOpenHelper(getApplicationContext(), "Data.db", null, 1);
        repeatbtnclicked = 0;
        Button submitbtn = (Button)findViewById(R.id.submit);
        Button retbtn = (Button)findViewById(R.id.returnbtn);
        Button infobtn = (Button)findViewById(R.id.info);
        Button repeatbtn = (Button)findViewById(R.id.repeatbtn);
        repeatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    client = new AsyncHttpClient();
                    idcheckhttp = new idcheck();
                    // 단말 등록하고 등록 ID 받기
                    EditText id = (EditText) findViewById(R.id.id1);
                    RequestParams params = new RequestParams();
                    params.put("id", id.getText().toString().trim());
                    client.post(URL_POST2, params, idcheckhttp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), dbManager.PrintData(), Toast.LENGTH_SHORT).show();
                final TextView resulttext = (TextView) findViewById(R.id.sec1);
                resulttext.setText(""+dbManager.PrintData());

            }
        });
        Intent intent = getIntent();
        regid = intent.getStringExtra("regid");
        retbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText pw1 = (EditText) findViewById(R.id.pw1);
                EditText pw2 = (EditText) findViewById(R.id.pw2);
                String passwd1 = pw1.getText().toString().trim();
                String passwd2 = pw2.getText().toString().trim();
                if (repeatbtnclicked != 1) {
                    Toast.makeText(getApplicationContext(), "ID 중복확인을 먼저 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!passwd1.equals(passwd2)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    client = new AsyncHttpClient();
                    httpResponse = new sendtoserver();
                    // 단말 등록하고 등록 ID 받기
                    regist();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void regist() {
                RegisterThread registerObj = new RegisterThread();
                registerObj.start();

            }

            class RegisterThread extends Thread {
                public void run() {
                    try {
                        EditText id = (EditText) findViewById(R.id.id1);
                        EditText pw = (EditText) findViewById(R.id.pw1);
                        Calendar calendar = Calendar.getInstance();
                        String str = calendar.getTime().toString();
                        RequestParams params = new RequestParams();
                        params.put("id", id.getText().toString().trim());
                        params.put("password", pw.getText().toString().trim());
                        params.put("regid", regid);
                        params.put("date", str);
                        client.post(URL_POST, params, httpResponse);
                        // DB

                        dbManager.insert("insert into Logindata values(null, '" + id.getText().toString().trim() + "', '" + pw.getText().toString().trim() + "', '"+regid+"');");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    finish();
                }
            }
        });

        extraBundle = new Bundle();
        //번들을 만든다. GET, POST와 비슷한 형태로 인자과 값을 지정한다.
        extraBundle.putString("key1", "회원가입 완료");
        extraBundle.putString("key2", "key2");



        //날 호출한 Activity 에게 번들을 돌려준다.
        this.setResult(RESULT_OK, intent);//RESULT_OK를 돌려주면 MainActivity 에서 받는다.

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        OAuthLoginDefine.DEVELOPER_VERSION = true;
        //mContext = this;
        mContext = getApplicationContext();
        initData();
        initView();
        Button logoutbtn = (Button) findViewById(R.id.buttonOAuthLogout);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginInstance.logout(mContext);
            }
        });

        Button apicall = (Button) findViewById(R.id.buttonVerifier);
        apicall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RequestApiTask().execute();
            }
        });
    }

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }

    private void initView() {
        mApiResultText = (TextView) findViewById(R.id.api_result_text);

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            mApiResultText.setText((String) "");
        }
        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }
        protected void onPostExecute(String content) {
            client = new AsyncHttpClient();
            httpResponse = new sendtoserver();
            dbManager = new DbOpenHelper(getApplicationContext(), "Data.db", null, 1);
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document =  builder.parse(new InputSource(new StringReader(content)));
                NodeList nodelist     =  document.getElementsByTagName("email");
                NodeList nodenicname     =  document.getElementsByTagName("nickname");
                NodeList nodeid   =  document.getElementsByTagName("enc_id");

                Node node = nodelist.item(0);
                Node node1 = nodenicname.item(0);
                Node node2 = nodeid.item(0);
                Node textNode = nodelist.item(0).getChildNodes().item(0);
                Node textNode1 = nodenicname.item(0).getChildNodes().item(0);
                Node textNode2 = nodeid.item(0).getChildNodes().item(0);
                /*
                Log.d(TAG, "email: " + textNode.getNodeValue());
                Log.d(TAG,"nicname: " + textNode1.getNodeValue());
                Log.d(TAG, "ENC_ID: " + textNode2.getNodeValue());
                */
                Calendar calendar = Calendar.getInstance();
                String str = calendar.getTime().toString();
                RequestParams params = new RequestParams();
                params.put("id", textNode.getNodeValue());
                params.put("password", textNode2.getNodeValue());
                params.put("regid", regid);
                params.put("date", str);
                client.post(URL_POST, params, httpResponse);
                dbManager.insert("insert into Logindata values(null, '" + textNode.getNodeValue() + "', '" + " Naver 가입입니다" + "', '" + regid + "');");
            }catch(Exception e){
                e.printStackTrace();
            }
            mApiResultText.setText((String) content);
        }
    }

    public class idcheck extends AsyncHttpResponseHandler {

        ProgressDialog dialog;

        /**통신 시작시에 실행.*/
        @Override
        public void onStart() {
        }

        /**통신 접속 실패시 호출.
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

            try {
                String result = new String(body, "UTF-8");
                if (result.equals("true")) {
                    Toast.makeText(getApplicationContext(), "동일한 ID가 존재합니다", Toast.LENGTH_SHORT).show();
                    repeatbtnclicked = 0;
                } else {
                    Toast.makeText(getApplicationContext(), "사용 가능한 ID 입니다", Toast.LENGTH_SHORT).show();
                    EditText id = (EditText) findViewById(R.id.id1);
                    id.setFocusable(false);
                    repeatbtnclicked = 1;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        /**성공, 실패 여부에 상관 없이 통신이 종료되면 실행된다.*/
        @Override
        public void onFinish() {
            //dialog.dismiss();
            //dialog = null;
        }
    } //end class
}