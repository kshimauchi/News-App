package com.kshimauchi.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kshimauchi.newsapp.Model.NewsItem;

import java.util.ArrayList;

import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_AT;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_URL;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_URL_TO_IMAGE;
import static com.kshimauchi.newsapp.data.Contract.TABLE_ARTICLES.TABLE_NAME;

/**
 * Created by kshim on 7/22/2017.
 */

public class DatabaseUtilities {
    //We are going to be use a Cursor for the database
    public static Cursor getALL(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_AT );
        return cursor;
    }
    //over rides content provider
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> articles)
    {
        db.beginTransaction();  //Begins a transaction in Exclusive Mode allows rollback
    try{
            //This reflects the contract and pojo hence all of the data from the arraylist populates the database
            for(NewsItem item: articles){
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_AUTHOR, item.getAuthor());  //get the author name from arraylist and puts its in the contentvalues
                cv.put(COLUMN_NAME_TITLE, item.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, item.getDescription());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, item.getUrlToImage());
                cv.put(COLUMN_NAME_PUBLISHED_AT, item.getPublishedAt());
                cv.put(COLUMN_NAME_URL, item.getUrl());

                db.insert(TABLE_NAME, null, cv);  //insert everything into db
            }
            db.setTransactionSuccessful();  //marks the transaction of successful
    }finally {
        db.endTransaction();
        db.close();
        }
    }
    public static void deleteAll(SQLiteDatabase db)
     {
        db.delete(TABLE_NAME,null, null);
    }
}
