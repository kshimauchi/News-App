package com.kshimauchi.newsapp;
// for nytimes api-key:     354968bf26be4ebea9cda2a00d974e90
//for newsapp api-key:
import android.net.Uri;
import android.util.Log;

import com.kshimauchi.newsapp.Model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by kshim on 6/18/2017.  <p>modified 7/21 rebuilding the url for another api</p>
 */
//the url for the ny times is https://api.nytimes.com/svc/mostpopular/v2/mostviewed/U.S./30.json"
public class NetworkUtils {
public static final String TAG="NetworkUtils";
    public static final String BASE_URL ="https://api.nytimes.com/svc/mostpopular/v2/mostviewed/U.S./30.json";
    public static final String PARAM_QUERY = "q";
    public static final String PARAM_API_KEY = "api-key";

    //method to build the URL using URI builder
    public static URL buildURL() {
        Uri builtURi = Uri.parse(BASE_URL).buildUpon()
                //this api is registered to me so go find your own
                .appendQueryParameter(PARAM_API_KEY, "a1b462f40fff4aada162b5113341c760").build();
        //a1b462f40fff4aada162b5113341c760 for nytimes
        //44f7c25a59fc4280b594c6c32743cca1 newsapi
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
            //disconnect or close
            urlConn.disconnect();
        }
        return null;
    }
    //parsing a string and returning an arraylist of type NewsItem
   public static ArrayList<NewsItem> parseJSON(String json) throws JSONException{
       //Collections Holder
        ArrayList<NewsItem> result= new ArrayList<>();
        //JSON object passing json is a String
        JSONObject main = new JSONObject(json);
        //articles is an
        JSONArray items = main.getJSONArray("results");
       //String representation for the imageURL
       String imageURL = null;
       /**
         * @param title
         * @param published_date:
         * @param abstract:
         * @param url
         * There are more see www.nytimes.com/svc/mostpopular/v2/mostviewed/U.S./30.json
        **/
        for(int i =0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);
            //we need to create a new item
            String title = item.getString("title");
            String published_date = item.getString("published_date");
            String abstractKeyword = item.getString("abstract");
            String url = item.getString("url");
            //returns the value mapped by name if it exists and is a JSONArray or null otherwise
            JSONArray mediaObjects = item.optJSONArray("media");

            //checks the case when optJsonArray method returns null if it is not null then there is data
            //that data has several fields "type":"image":"subtype":"photo":caption and more
            if (mediaObjects != null){
                JSONObject image = mediaObjects.getJSONObject(0);  //gets the type of object which is of type image
                JSONArray metaData = image.getJSONArray("media-metadata"); //gets properties associated with the image
                JSONObject pictureMetaData = metaData.getJSONObject(0);
                imageURL = pictureMetaData.getString("url");
            }
            //have to adjust my plain old java object to create the item of interest
            result.add(new NewsItem(title, published_date,abstractKeyword,imageURL, url));
        }
        return result;


}

}//End of NetworkUtils
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

 final static String BASE_URL = "http://newsapi.org/V1/articles";

 final static String SOURCE = "source";
 final static String source_value = "the-next-web";

 final static String SORT_BY = "sortBy";
 final static String sortBy_value = "latest";

 final static String API_KEY = "apiKey";
 //Get your own key
 final static String api_value = "44f7c25a59fc4280b594c6c32743cca1";
 //44f7c25a59fc4280b594c6c32743cca1
 final static String TAG = "NetworkUtils url:";
 */
/**  ***/