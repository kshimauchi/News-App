package com.kshimauchi.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.kshimauchi.newsapp.Model.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.kshimauchi.newsapp.NewsAdapter.ItemClickListener;

//

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>> {
    static final String TAG = "MainActivity";
    private ProgressBar progress;
    private EditText search;
    private RecyclerView recView;
    public final static String URLKEY = "url";
    //maybe unnecessary in subsequent builds here it may not be implemented
    private static final int Search_Loader = 1;
    public final static String SEARCH_QUERY_EXTRA = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check this
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        search = (EditText) findViewById(R.id.searchQuery);
        recView = (RecyclerView) findViewById(R.id.recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();
        //search is the id in menu.xml
        if (itemNumber == R.id.search) {
            String query = search.getText().toString();
            //bundle creation bundles have 3 parameters
            Bundle bundleQuery = new Bundle();
            bundleQuery.putString(SEARCH_QUERY_EXTRA, query);
            android.support.v4.app.LoaderManager loaderManager = getSupportLoaderManager();
            android.support.v4.content.Loader<Object> loader = loaderManager.getLoader(Search_Loader);

            if (loader == null) {
                loaderManager.initLoader(Search_Loader, bundleQuery, this).forceLoad();

            } else {
                loaderManager.restartLoader(Search_Loader, bundleQuery, this);
            }
        }
        return true;
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<NewsItem>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) return;
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public ArrayList<NewsItem> loadInBackground() {
                //
                String qry = args.getString(String.valueOf(search));
                //if (qry == null || TextUtils.isEmpty(qry)) return
                ArrayList<NewsItem> result = null;
                //not passing in a query here because I have completely built the url a better approach
                //would be to pass the API KEY
                //44f7c25a59fc4280b594c6c32743cca1
                URL url = NetworkUtils.buildURL(qry);
                Log.d(TAG, "url :" + url.toString());
                try {
                    String json = NetworkUtils.getResponseFromHttpURL(url);
                    result = NetworkUtils.parseJSON(json);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
                return result;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, final ArrayList<NewsItem> data) {
        progress.setVisibility(View.GONE);
        if (data != null) {
            NewsAdapter adapter = new NewsAdapter(data, new ItemClickListener() {

                @Override
                public void onItemClick(int clickedItemIndex) {
                    String url = data.get(clickedItemIndex).getUrl();
                    String.format("Url%s", url);
                    Log.d("formated: url", url);
                    //makes an intent without URI parse
                    Intent browserIntent = new Intent(MainActivity.this, Web.class);
                    browserIntent.putExtra(URLKEY, url);
                    startActivity(browserIntent);
                }
            });
            recView.setAdapter(adapter);
        }
    }
    //OnLoader Reset is not being used right now!
    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {
    }
}