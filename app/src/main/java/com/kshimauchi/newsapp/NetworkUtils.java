package com.kshimauchi.newsapp;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by kshim on 6/18/2017.
 */

public class NetworkUtils {
    /**
     * Create a url using uri builder
     * https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=44f7c25a59fc4280b594c6c32743cca1
     *
     * source=the-next-web (required)
     *
     * sortBy= latest, for additional information see newsapi.org (sortBy is optional: Top, latest, ...and some others)
     *
     * apiKey = 44f7c25a59fc4280b594c6c32743cca1 (required)
     * * */
    final static String BASE_URL="https://newsapi.org/V1/articles";

    final static String  SOURCE="source";
    final static String source_value="the-next-web";

    final static String SORT_BY= "sortBy";
    final static String sortBy_value = "latest";

    final static String API_KEY="apiKey";

    //Get your own key
    final static String api_value="";

    final static String TAG = "NetworkUtils";

    //method to build the URL using URI builder
    public static URL buildURL(){
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

        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpURL(URL url) throws IOException{
        HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
        try {
            InputStream in = urlConn.getInputStream();

            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");

            boolean hasInput = input.hasNext();

            if (hasInput) {

                return input.next();

            } else {

                return null;
            }
        }finally{
            urlConn.disconnect();
        }
    }

}//End of NetworkUtils
