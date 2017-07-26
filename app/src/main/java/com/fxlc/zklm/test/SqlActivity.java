package com.fxlc.zklm.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.Truck;
import com.fxlc.zklm.db.MySqliteHelper;
import com.fxlc.zklm.util.UriUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SqlActivity extends AppCompatActivity {

    MySqliteHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
//                it.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//设置类型
//                it.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(it, 1);
                readDB();
            }
        });
        helper = new MySqliteHelper(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String uri = data.getData().toString();
            Log.d("sql", uri);
            String path = UriUtil.getRealFilePath(this, data.getData());
            readExcel(path);
        }

    }

    public void readExcel(String path) {
        db = helper.getWritableDatabase();
        try {
            InputStream is = new FileInputStream(path);
            Workbook book = Workbook.getWorkbook(is);
            int num = book.getNumberOfSheets();

            Sheet sheet = book.getSheet(0);

            int rows = sheet.getRows();
            int cols = sheet.getColumns();
            // getCell(Col,Row)获得单元格的值
            for (int i = 1; i < rows; ++i) {
                Truck truck = new Truck();
                truck.setBrand(sheet.getCell(0, i).getContents());
                Log.d("excel", truck.getBrand());
                truck.setStyle(sheet.getCell(1, i).getContents());
                truck.setDrive(sheet.getCell(4, i).getContents());
                truck.setSoup(sheet.getCell(2, i).getContents());
                saveTruck(truck);

            }
            book.close();
            db.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveTruck(Truck truck) {

        String sql = "insert into truck(brand,style,drive,soup) values (?,?,?,?)";
        db.execSQL(sql, new String[]{truck.getBrand(), truck.getStyle(), truck.getDrive(), truck.getSoup()});

    }

    public void readDB() {
        db = helper.getWritableDatabase();

        String sql = "select * from truck";
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String brand = cursor.getString(1);
            String style = cursor.getString(2);
            String drive = cursor.getString(3);
            String soup = cursor.getString(4);
            Log.d("database", brand + "-" + style + "-" + drive + "-" + soup);

        }


    }
}
