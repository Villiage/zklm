package com.fxlc.zklm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fxlc.zklm.bean.Contact;

/**
 * Created by cyd on 2017/6/27.
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NAME = "zklm";
    public MySqliteHelper (Context context){
        super(context,NAME,null,VERSION);

    }

    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TRUCK (id integer primary key autoincrement, brand varchar(20), style varchar(20),drive varchar(10),soup varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
