package com.kshimauchi.newsapp;

import android.net.Uri;
import android.util.Log;

import com.kshimauchi.newsapp.Model.NewsItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kshim on 6/18/2017.
 */

public class NetworkUtils {
    /**
     * Create a url using uri builder
     * https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=44f7c25a59fc4280b594c6c32743cca1
     * <p>
     * source=the-next-web (required)
     * <p>
     * sortBy= latest, for additional information see newsapi.org (sortBy is optional: Top, latest, ...and some others)
     * <p>
     * apiKey = 44f7c25a59fc4280b594c6c32743cca1 (required)
     * *
     */
    final static String BASE_URL = "http://newsapi.org/V1/articles";

    final static String SOURCE = "source";
    final static String source_value = "the-next-web";

    final static String SORT_BY = "sortBy";
    final static String sortBy_value = "latest";

    final static String API_KEY = "apiKey";
    //Get your own key
    final static String api_value = "44f7c25a59fc4280b594c6c32743cca1";

    final static String TAG = "NetworkUtils url:";

    //method to build the URL using URI builder
    public static URL buildURL() {
        Uri builtURi = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE, source_value)
                .appendQueryParameter(SORT_BY, sortBy_value)
                .appendQueryParameter(API_KEY, api_value)
                .build();
        URL url = null;
        try {
            String urlString = builtURi.toString();

            url = new URL(builtURi.toString());

            Log.d(TAG, urlString.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpURL(URL url) throws IOException {
        Log.d(TAG, "starting conection");
        HttpURLConnection urlConn = null;

        try {
            //System err.
            Log.d(TAG, "trying to receive input");
            urlConn= (HttpURLConnection) url.openConnection();
            if(urlConn == null) Log.d(TAG, "urlConn null");
            InputStream in = urlConn.getInputStream();


            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");

            boolean hasInput = input.hasNext();

            if (hasInput) {
                Log.d(TAG, "received input");
                return input.next();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } finally {
            //disconnect
            urlConn.disconnect();
        }
        return null;
    }
    //parsing a string and returning an arraylist of type NewsItem
   public static ArrayList<NewsItem> parseJSON(String json) throws JSONException{


       //Collections Holder
        ArrayList<NewsItem> result= new ArrayList<>();
        //JSON object passing json is a String
        JSONObject response = new JSONObject(json);
        //articles is an Array which is
        JSONArray articles = response.getJSONArray("articles");
//        /**
//         * @param author
//         * @param title
//         * @param description
//         * @param url
//         * @param urlToImage
//         * @param publishedAt
//         **/
        for(int i =0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);
            String author = article.getString("author");
            String title = article.getString("title");
            String description = article.getString("description");
            String url = article.getString("url");
            String urlToImage = article.getString("urlToImage");
            String publishedAt = article.getString("publishedAt");
           Log.d(TAG, " parsing author " + author );
            Log.d(TAG, "parsing title " + title );
            Log.d(TAG, " description " + description);
            Log.d(TAG, " url " + url);
            Log.d(TAG, " url to image " + urlToImage);
            Log.d(TAG, " publishedAT " + publishedAt );
           //Add the article to the Arraylist to create articles
            result.add(new NewsItem(author, title, description, url, urlToImage, publishedAt));
        }
        return result;


}

}//End of NetworkUtils
