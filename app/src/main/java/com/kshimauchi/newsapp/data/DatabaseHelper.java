package com.kshimauchi.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kshim on 7/22/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "articles.db";
    private static final String TAG ="dbHELPER ";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //refelects the parsed data creates a table and a column for all fields being parsed
        //creates the database statement to execute a query
        String queryString = "CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME + " ("+
                Contract.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR + " TEXT NOT NULL, "+
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE + " TEXT, " +
                Contract.TABLE_ARTICLES. COLUMN_NAME_DESCRIPTION + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_URL + " TEXT" +
                "); ";

            Log.d(TAG, "Create table sql" + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table " + Contract.TABLE_ARTICLES.TABLE_NAME + " if exists; " );
    }
}
