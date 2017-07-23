package com.kshimauchi.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ProgressBar;

import com.kshimauchi.newsapp.data.Contract;
import com.kshimauchi.newsapp.data.DatabaseHelper;
import com.kshimauchi.newsapp.data.DatabaseUtils;
//line 81 and line 41
//implementing Loadermanager with loadercallbacks is part of the routine used in swapping out Asysntask with AsyntaskLoader
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>,
        NewsAdapter.ItemClickListener {

    static final String TAG = "MainActivity";
    private ProgressBar progress;
    private RecyclerView recView;
    /**Adapter, Cursor, SQLLite Database **/
    private NewsAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final int NEWS_LOADER =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        //search = (EditText) findViewById(R.id.searchQuery);  removed in homework4 as per request it is not needed
        recView = (RecyclerView) findViewById(R.id.recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(this));

        //allows one to store data in key/value pairs
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = preferences.getBoolean("isFirst", true);

        if(isFirst){
           load();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
        }
       ScheduleUtilities.scheduleRefresh(this);
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
            load();
        }
        return true;
    }

    public void load(){
        LoaderManager ldManager = getSupportLoaderManager();
        ldManager.restartLoader(NEWS_LOADER,null, this).forceLoad();
//        adapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
                return null;
            }
        };
    }
        @Override
        public void onLoadFinished (Loader < Void > loader, Void data){
            progress.setVisibility(View.GONE); //removes the progress bar from being seen after it has retrieved data
            db = new DatabaseHelper(MainActivity.this).getWritableDatabase();  //opens the datbase to used for reading and writing
            cursor = DatabaseUtils.getAll(db);
            adapter = new NewsAdapter(cursor, this);
            recView.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset (Loader < Void > loader) {    }

        @Override
        public void onItemClick (Cursor cursor,int clickedItemIndex){
            cursor.moveToPosition(clickedItemIndex);
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
            String.format("Url %s" + url);

            Log.d(TAG, url);
            //intent for the url
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(url));
            startActivity(browserIntent);
        }
    @Override
    protected void onStart(){
        super.onStart();
        db = new DatabaseHelper(MainActivity.this).getWritableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor, this);
        recView.setAdapter(adapter);
    }
        @Override
        protected void onStop(){
            super.onStop();
            db.close();
            cursor.close();
        }
    }
/**
 *     @Override
public Loader<ArrayList<NewsItem>> onCreateLoader(int id, final Bundle args) {
return new AsyncTaskLoader<ArrayList<NewsItem>>(this) {
@Override
protected void onStartLoading() {
super.onStartLoading();
//    if (args == null) return;
progress.setVisibility(View.VISIBLE);
}

@Override
public ArrayList<NewsItem> loadInBackground() {  //removing ArrayList<NewsItem>

String qry = args.getString(String.valueOf(""));
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
 *
 *
 *
 * */