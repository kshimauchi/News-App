package com.kshimauchi.newsapp;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kshimauchi.newsapp.Model.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.util.Log.*;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    private ProgressBar progress;

    private EditText search;

    private TextView textView;

    private RecyclerView rv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        search = (EditText) findViewById(R.id.searchQuery);
        textView = (TextView) findViewById(R.id.displayJSON);

        rv =(RecyclerView)findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.search) {
            /*Execute asynctask here */
            String s =   search.getText().toString();
            NetworkTask task = new NetworkTask(s);
            task.execute();

            Context context = MainActivity.this;

            String textToShow = "Search clicked";

            Toast.makeText(this, "Searching!", Toast.LENGTH_LONG).show();

            return true;
        }
        return true;
    }

    public class NetworkTask extends AsyncTask<URL, Void, ArrayList<NewsItem>>{
        String query;

        NetworkTask(String s){
            query = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.INVISIBLE);
        }

        @Override
         protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> data = null;

            URL url = com.kshimauchi.newsapp.NetworkUtils.buildURL();

            Log.d(TAG, "url: " + url.toString());

            try {
                //result = to the json string:  article = author/title/description/url/urlToImage/publishedAt
                //{"status":"oyk","source":"the-next-web","sortBy":"latest","articles" + the above...

               String  json = NetworkUtils.getResponseFromHttpURL(url);
                Log.d(TAG, "json "+json);
                //we need to parse the json  string... here data is an arrayList of type item
                 data = NetworkUtils.parseJSON(json);


            } catch (IOException e) {
                e.printStackTrace();
            } catch(JSONException e){
               e.printStackTrace();
             Log.e(TAG, "JSON parse failed");
     }
            return data;
        }
        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progress.setVisibility(View.GONE);

            if(data != null){
                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(int clickedItemIndex){
                        String url = data.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("URL %S", url));
                    }
                });
             rv.setAdapter(adapter);
            }
        }
    }
}

