package com.kshimauchi.newsapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.io.IOException;

/**
 * Created by kshim on 7/22/2017.
 */

public class NewsJob extends JobService{
    AsyncTask mBackgroundTask;
    static final String TAG = "NewsJob";
    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                //Does a toast for the Refresh Task per minutes
                Toast.makeText(NewsJob.this, "Articles Refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    RefreshTasks.refreshArticles(NewsJob.this);
                    Log.d( TAG, "Articles Refreshed"   );
                } catch (IOException e) {
                    Log.d(TAG, "exception with refreshTask probably parsing error ");
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Object obj) {
                jobFinished(job, false);
                super.onPostExecute(obj);
            }
        };
        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        if (mBackgroundTask != null) mBackgroundTask.cancel(false);

        return true;
    }
}
