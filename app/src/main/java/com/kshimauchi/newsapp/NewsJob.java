package com.kshimauchi.newsapp;

import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by kshim on 7/21/2017.
 */

public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackgroundTask = new AsyncTask(){
         @Override
            protected void onPreExecute(){
             Toast.makeText(NewsJob.this, "New Refreshed", Toast.LENGTH_SHORT).show();
             super.onPreExecute();
         }
         @Override
            protected Object doInBackground(Object[] params){
             RefreshTasks.refreshArticles(NewsJob.this); //
            return null;
         }
         @Override
            protected void onPostExecute(Object obj){
             jobFinished(jobParameters, false);
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
