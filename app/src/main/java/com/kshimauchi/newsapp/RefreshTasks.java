package com.kshimauchi.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kshimauchi.newsapp.Model.NewsItem;
import com.kshimauchi.newsapp.data.DatabaseHelper;
import com.kshimauchi.newsapp.data.DatabaseUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kshim on 7/22/2017.
 */

public class RefreshTasks  {
//Refreshes the article list to be populating and shown in the views
    public static void refreshArticles(Context context) throws IOException {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildURL();
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        try{
            DatabaseUtilities.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpURL(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtilities.bulkInsert(db, result);

        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        db.close();
    }
}
