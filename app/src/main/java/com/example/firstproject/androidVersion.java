package com.example.firstproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class androidVersion  extends SQLiteOpenHelper {
    String database="room";
    static int VERSION=1;
    static String NAME1="name1";
    static String ANDROID_DB="android_db";
    static String ANDROID_VERSION="android_version";
    static String ANDROIDDB="androidDB";
    static String ANDROIDVERSION="androidVersion";
    public androidVersion(@Nullable Context context) {
        super(context, ANDROID_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + NAME1 + "(id INTEGER PRIMARY KEY AUTOINCREMENT,androidDB VARCHAR(25)," +
                    "androidVersion INTEGER)";
            sqLiteDatabase.execSQL(query);

        } catch (Exception e) {

        }
    }
    public void insert(String androidDB,int Version)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ANDROIDDB,androidDB);
        contentValues.put(ANDROIDVERSION,Version);
        sqLiteDatabase.insert(NAME1,null,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor getversions(){
        SQLiteDatabase sq1=this.getReadableDatabase();
        Cursor queued=null;
        try {
            String query=("SELECT* FROM "+NAME1);
            queued=sq1.rawQuery(query,null);
        }catch (SQLException e){
            System.out.println();
        }
        return queued;

    }
}
