package com.example.hanjun.version_zero;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.server.Sender;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    Button btn;
    private final static int SECOND_ACTIVITY = 2;
    DbOpenHelper dbManager;

    /**
     * 서버 : Sender 객체 선언
     */
    Sender sender;
    Handler handler = new Handler();
    private GPSInform gps;


    //
    /**
     * collapseKey 설정을 위한 Random 객체
     */
    private Random random ;
    /**
     * 구글 서버에 메시지 보관하는 기간(초단위로 4주까지 가능)
     */
    private int TTLTime = 60;
    /**
     * 단말기에 메시지 전송 재시도 횟수
     */
    private int RETRY = 3;
    /*
     * 등록된 ID 저장
     */
    ArrayList<String> idList = new ArrayList<String>();

    // 주소록 변수
    public ContactItem[] item = new ContactItem[300];       // 정적할당. 넉넉하게 잡아야한다. 주소록 갯수가 300개 넘어가면 에러날듯?
    int i = 0;      // 반복문 돌리는 용
    public int allnum=0;        // 주소록 실제 갯수

    public static Context mContext;         // 다른 클래스에서 참조하게 하는 용도. 여러가지 방법이 있는데 이거저거 하다가 계속 에러나서 이 방법을 씀

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new (this, Splash.class)); // Loading 화면
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // LoadContacts(); // 전화번호부 저장
        mContext = this; // 전화번호부
        dbManager = new DbOpenHelper(getApplicationContext(), "Data.db", null, 1);

        // 알림 접근 창 켜줌
        if (!isContainedInNotificationListeners(getApplicationContext()))
        {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivityForResult(intent, 2222);
        }
        // 수동 알림창 켜기
        Button button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivityForResult(intent, 3333);
            }
        });

        // 브로드캐스트 발생, 일반 푸쉬알람용
        LocalBroadcastManager.getInstance(this).registerReceiver(new Broadcast(), new IntentFilter("Msg"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // 서버 : GOOGLE_API_KEY를 이용해 Sender 초기화
        sender = new Sender(GCMInfo.GOOGLE_API_KEY);
        // 단말 등록하기 버튼
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 단말 등록하고 등록 ID 받기
                    registerDevice();

                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        Button btnShowLocation = (Button) findViewById(R.id.btn_start);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                gps = new GPSInform(MainActivity.this);

                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });

        Button addr = (Button) findViewById(R.id.addr); // 주소록 디비저장
        final TextView resulttext = (TextView) findViewById(R.id.result); // 주소록 디비저장

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resulttext.setText("저장 중..");
                LoadContacts();
                //dbManager.PrintAddr();
            }
        });

        Button delete = (Button) findViewById(R.id.delete1); // 주소록 디비저장
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.delete("delete from Addr");
                Toast.makeText(getApplicationContext(), "저장 되어 있던 전화번호부가 삭제 되었습니다", Toast.LENGTH_SHORT).show();//토스트로 튕겨준다.
                resulttext.setText("저장 되어 있던 전화번호부가 삭제 되었습니다");
                Button addr = (Button) findViewById(R.id.addr); // 주소록 디비저장
                Button delete = (Button) findViewById(R.id.delete1); // 주소록 디비저장
                addr.setEnabled(true);
                delete.setEnabled(false);
            }
        });
    }

    /**
     * 단말 등록
     */
    private void registerDevice() {
        RegisterThread registerObj = new RegisterThread();
        registerObj.start();

    }

    class RegisterThread extends Thread {
        public void run() {

            try {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                String regId = gcm.register(GCMInfo.PROJECT_ID);
                //println("푸시 서비스를 위해 단말을 등록했습니다.");
                //println("등록 ID : " + regId);
                //   Log.d(TAG, regId);
                // 등록 id 서버 전송
                /*
                GCMInfo Sendregid = new GCMInfo();
                Sendregid.sendId(regId);

                RequestParams params = new RequestParams();
                params.put("regidid", regId);
                cli.post(URL_POST, params, httpRe);

                // 등록 ID 리스트에 추가 (현재는 1개만)
                idList.clear();
                idList.add(regId);
                */
                Intent intent = new Intent(MainActivity.this, Join.class);
                intent.putExtra("regid", regId);
                startActivityForResult(intent, SECOND_ACTIVITY);
            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }
    }
    private void println(String msg) {
        final String output = msg;
        handler.post(new Runnable() {
            public void run() {
                Log.d(TAG, output);
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
            }
        });
    }
///////////////////////////////////// 주소록 가져오기 //////////////////////////////////////////////////

    private void LoadContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER };

        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(2) == 1)
                    LoadPhoneNumbers(cursor.getString(0));
            }
        }
        allnum=i;           // 주소록 갯수 대입하고
        i=0;                // i는 초기화 해준다
        final TextView resulttext = (TextView) findViewById(R.id.result); // 주소록 디비저장
        Button addr = (Button) findViewById(R.id.addr); // 주소록 디비저장
        Button delete = (Button) findViewById(R.id.delete1); // 주소록 디비저장

        Toast.makeText(this, "전화번호부가 저장 되었습니다", Toast.LENGTH_SHORT).show();//토스트로 튕겨준다.
        resulttext.setText("전화번호부가 저장 되었습니다");

        addr.setEnabled(false);
        delete.setEnabled(true);
    }
    private void LoadPhoneNumbers(String id) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        Cursor cursor = managedQuery(uri, projection, selection, new String[] {id}, null);



        while (cursor.moveToNext()) {
            //item[i] = new ContactItem(cursor.getString(0), cursor.getString(1));        // 하나하나 클래스에 넣음
            dbManager.insert("insert into Addr values(null, '" + new ContactItem(cursor.getString(0), cursor.getString(1)).mName + "', '" + new ContactItem(cursor.getString(0), cursor.getString(1)).mNumber +"');");
            i++;
        }
    }

    public class ContactItem {                  // 주소록을 저장할 클래스. 이름이랑 번호만 저장
        public String mName;
        public String mNumber;

        public ContactItem(String name, String number) {
            mName = name;
            mNumber = number.replace("-", "");          // 전화번호가 이상한 형식으로 저장되 있는게 많더라.
            mNumber = mNumber.replace("//", "");            // -, // 이런게 들어가있길래 다 빼버렷음.
        }
    }


    ///////////////////////////////////// 주소록 가져오기 끝 //////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static boolean isContainedInNotificationListeners(Context $context)
    {
        String enabledListeners = Settings.Secure.getString($context.getContentResolver(), "enabled_notification_listeners");
        return !TextUtils.isEmpty(enabledListeners) && enabledListeners.contains($context.getPackageName());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extraBundle;
        //MainActivity에서 부여한 번호표를 비교

        /*
        if(requestCode == SECOND_ACTIVITY){

            Log.d("SECOND_ACTIVITY_LOG", "THIS CLOSE !!");//로그기록

            //번호표를 부여한 Activity의 실행 여뷰, 켄슬, 오케이, 등등 실행에 관련된 행위 구분
            if(resultCode == RESULT_OK){//세컨드 액티비티에서 이 값을 반환하는 코드가 동작 됐을때
                extraBundle = intent.getExtras();//번들로 반환됐으므로 번들을 불러오면 셋팅된 값이 있다.
                String str = extraBundle.getString("key1");//인자로 구분된 값을 불러오는 행위를 하고
                //println(str);
                Log.d(TAG,str);
                //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();//토스트로 튕겨준다.
            }
        }
        */
    }
}
