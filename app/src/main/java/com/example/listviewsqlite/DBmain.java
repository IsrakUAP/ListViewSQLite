package com.example.listviewsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBmain extends SQLiteOpenHelper {
    public DBmain(@Nullable Context context) {
        super(context, "namedb", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table thing(id integer primary key,name text,age text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     String sql="drop table if exists thing";
     db.execSQL(sql);
     onCreate(db);
    }
}
