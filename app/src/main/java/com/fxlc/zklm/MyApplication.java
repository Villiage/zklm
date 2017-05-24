package com.fxlc.zklm;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.fxlc.zklm.bean.DaoMaster;
import com.fxlc.zklm.bean.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by cyd on 2017/4/20.
 */

public class MyApplication extends Application {


    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        daoSession = master.newSession();

    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
}
