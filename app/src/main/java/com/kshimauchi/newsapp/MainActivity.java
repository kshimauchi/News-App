package com.kshimauchi.newsapp;

import android.content.Context;
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
import com.kshimauchi.newsapp.data.DatabaseUtilities;
//55 //ScheduleUtilities.scheduleRefresh(this);
// 85 //           RefreshTask.refreshArticle(MainActivity.this);
//Loadercallbacks where added in a previous commit!  commit name:  Loader, LoaderCallbacks

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>,
        NewsAdapter.ItemClickListener{
    static final String TAG = "MainActivity";
    private ProgressBar progress;
    //private EditText search; removed we are not modifying a search
    private RecyclerView recView;

     private NewsAdapter adapter;
    private Context context;  //added a context to the constructor of the NewsAdapter
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final int NEWS_LOADER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check this
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        recView = (RecyclerView) findViewById(R.id.recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(this));

        //Shared Preference will be added
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = preferences.getBoolean("isFirst", true);

        if(isFirst){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();  //recomended over commit
        }
        ScheduleUtilites.scheduleRefresh(this);
    }
    public void load(){
        //LoaderManager can handle more then one Loaders
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
          adapter.notifyDataSetChanged();
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


    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            public Void loadInBackground() {
     //           RefreshTask.refreshArticle(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE);  //invisible and does not take up layout space
        db = new DatabaseHelper(MainActivity.this).getWritableDatabase();
        cursor = DatabaseUtilities.getALL(db);
        adapter = new NewsAdapter(context,cursor, this);  //cursor will work between the database and the adapter for the view
        recView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) { //not be used right now
    }
    @Override
    public void onStart(){
        super.onStart();
        db = new DatabaseHelper(MainActivity.this).getWritableDatabase();
        cursor = DatabaseUtilities.getALL(db);
        adapter = new NewsAdapter(context,cursor, this);
        recView.setAdapter(adapter);
    }
    @Override
    public void onStop(){
        super.onStop();
        db.close();   //close the database if open
        cursor.close();
    }
   @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        //this gets the cursor position and assigns it a string from the database column
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_URL));
        Log.d(TAG, String.format("Url %s ",url));
        //the intent launches a new browser for the use of the url
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));//this may need to be adjusted
        startActivity(browserIntent);
    }
}
/*          old version
*
    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
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
                //String qry = args.getString(String.valueOf(search));
                //if (qry == null || TextUtils.isEmpty(qry)) return
                ArrayList<NewsItem> result = null;
                //not passing in a query here because I have completely built the url a better approach
                //would be to pass the API KEY
                //44f7c25a59fc4280b594c6c32743cca1
                //URL url = NetworkUtils.buildURL(qry);
               // Log.d(TAG, "url :" + url.toString());
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
*
*
* @Override
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
 */