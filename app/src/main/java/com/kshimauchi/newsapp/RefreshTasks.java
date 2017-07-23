package com.kshimauchi.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kshimauchi.newsapp.Model.NewsItem;
import com.kshimauchi.newsapp.data.DatabaseHelper;
import com.kshimauchi.newsapp.data.DatabaseUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kshim on 7/21/2017.
 */

public class RefreshTasks {
    public static final String refresh = "refresh";
    static final String TAG = "REFRESH TASK: url";

    //updates the database by bulkinsert  rebuilds the url
    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildURL();
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        try    {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpURL(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtils.bulkInsert(db, result);
            Log.d(TAG, "url :" + url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        db.close();
    }
}