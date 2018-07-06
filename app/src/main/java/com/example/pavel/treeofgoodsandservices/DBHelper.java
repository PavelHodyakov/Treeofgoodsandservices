package com.example.pavel.treeofgoodsandservices;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table categories ("
                + "id integer primary key autoincrement,"
                + "name text Not Null,"
                + "id_img integer" + ");");

        db.execSQL("create table goods ("
                + "id integer primary key,"
                + "name text Not Null,"
                + "id_category integer Not Null" + ");");

        db.execSQL("create table images ("
                + "id integer primary key autoincrement,"
                + "url text Not Null" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
