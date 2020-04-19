package com.bookexample.mynewdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper(@Nullable Context context){

        super(context, Constants.DB_NAME,null,Constants.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //실제 테이블을 지우고 재생하는 것임. 데이터 다 날라감

        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);

    }

    // insert 기능
    public long insertInfo(String name, String age, String phone, String image, String addTimeStamp, String updateTimeStamp) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_NAME, name);
        values.put(Constants.C_AGE, age);
        values.put(Constants.C_PHONE, phone);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_ADD_TIMESTEMP, addTimeStamp);
        values.put(Constants.C_UPDATE_TIMESTEMP, updateTimeStamp);

        long id = db.insert(Constants.TABLE_NAME,null, values);
        db.close();
        return id;
    }


    public ArrayList<Model> getAllData(String orderBy) {

        ArrayList<Model> arrayList = new ArrayList<>();

        //DB내 선택테이블에서 모든 정보를 선택하는 쿼리
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //when we select all info from database, new get the data from columns
        //db에서 모든정보를 가져와서, 컬럼에서 새 데이터를 가져온다.

        if (cursor.moveToNext()) {

            do {
                // do is used because first it gets the data from columns then move to next condition
                Model model = new Model(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_AGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PHONE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIMESTEMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTEMP))
                );

                arrayList.add(model);
            } while (cursor.moveToNext());

        }

        db.close();
        return arrayList;

    }

}
