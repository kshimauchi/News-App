package com.kshimauchi.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kshim on 7/21/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "articles.db";
    private static final String TAG = "DB Helper class : ";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Here we build the query string to create the database
        String executeQuery = " CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME +
                " ("+
                Contract.TABLE_ARTICLES._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE + " DATE, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_ABSTRACT +" TEXT, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_IMAGEURL+" TEXT, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL + " TEXT" + "); ";
        Log.i(TAG, "Create table SQL " + executeQuery);
        db.execSQL(executeQuery);
    }
//come back to this if we have time
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   }
}
