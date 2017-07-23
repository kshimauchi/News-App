package com.kshimauchi.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kshimauchi.newsapp.Model.NewsItem;

import java.util.ArrayList;

import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_ABSTRACT;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_IMAGEURL;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_URL;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.TABLE_NAME;

/**
 * Created by kshim on 7/21/2017.
 */

public class DatabaseUtils {
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_DATE + " DESC"
        );
        return cursor;
    }
    //content providers bulkinsert method overide
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> articles){
        db.beginTransaction();  //begins a transaction in EXCLUSIVE MODE rollback occurs
        try{
            for(NewsItem item: articles){
                //gets values from an arraylist and puts them in the database
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_NAME_TITLE,item.getTitle());
                contentValues.put(COLUMN_NAME_ABSTRACT, item.getAbstractKeyword());
                contentValues.put(COLUMN_NAME_PUBLISHED_DATE, item.getPublished_date());
                contentValues.put(COLUMN_NAME_IMAGEURL, item.getImageURL());
                contentValues.put(COLUMN_NAME_URL, item.getUrl());
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            db.close();
        }
    }
    public static void deleteAll(SQLiteDatabase db){db.delete(TABLE_NAME,null,null);}
}