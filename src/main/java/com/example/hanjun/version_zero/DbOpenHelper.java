package com.example.hanjun.version_zero;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hanjun on 2016-02-11.
 */public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = "MainActivity";
    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블을 생성한다.
        // create table 테이블명 (컬럼명 타입 옵션);
        db.execSQL("CREATE TABLE Logindata( _id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, password TEXT, regid TEXT);");
        db.execSQL("CREATE TABLE Addr( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
       // Log.d(TAG, "Inserted before, query : " + _query);
        db.execSQL(_query);
       // Log.d(TAG, "Inserted After, query");
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        //Log.d(TAG, "Delete bef, query: "+_query);
        db.execSQL(_query);
        //Log.d(TAG, "Delete af, query");
        db.close();
    }

    public String getId() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select id from Logindata", null);
        cursor.moveToNext(); // 커서를 다음행으로 이동시킨다
        //Log.d(TAG,"curser.get :" + cursor.getString(0));
        return cursor.getString(0);
    }

    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        try {
            Cursor cursor = db.rawQuery("select * from Logindata", null);
            cursor.moveToNext();
            str += "ID : "
                    + cursor.getString(1)
                    + "\nPassword : "
                    + cursor.getString(2);
            Log.d(TAG, "Data 요청 : " + str);
            return str;
        }catch(Exception ex)
        {
            return "회원가입 하세요!";
        }
        /*
        while(cursor.moveToNext()) {
            str += "ID : "
                    + cursor.getString(1)
                    + "\nPassword : "
                    + cursor.getString(2);
        }
        */

    }
    public String PrintAddr() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from Addr", null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " : name "
                    + cursor.getString(1)
                    + ", number = "
                    + cursor.getString(2)
                    + "\n";
        }
        Log.d(TAG, "Addr 요청 : " + str);
        return str;
    }
    public String getnumber(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        try {
            Cursor cursor = db.rawQuery("select number from Addr where name = '" + name + "';", null);
            cursor.moveToNext(); // 커서를 다음행으로 이동시킨다
           // Log.d(TAG, "curser.get :" + cursor.getString(0));
            return cursor.getString(0);
        }catch(Exception ex)
        {
            Log.d(TAG,"Error : "+ex);
            return "Not exists";
        }
    }
    public String getname(String num) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        try {
            Cursor cursor = db.rawQuery("select name from Addr where number = '" + num + "';", null);
            //Cursor cursor = db.rawQuery("select name from Addr where number = '010-3545-2038';", null);
            Log.d("MainActivity", "here num: "+num);
            cursor.moveToNext(); // 커서를 다음행으로 이동시킨다
            // Log.d(TAG, "curser.get :" + cursor.getString(0));
            /*
            Cursor cursor = db.rawQuery("select * from Addr", null);
            while(cursor.moveToNext()) {
                str += "N:"
                        + cursor.getString(2) + "\n";
            }
            Log.d(TAG, "Data 요청 : " + str);
            */
            return cursor.getString(0);
        }catch(Exception ex)
        {
            Log.d(TAG,"Error : "+ex);
            return "없는 번호";
        }
    }
}
