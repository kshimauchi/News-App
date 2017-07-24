package com.kshimauchi.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

//firebase wow never used

/**
 * Created by kshim on 7/22/2017.
 */

public class ScheduleUtilites  {
    private static final int  SCHEDULE_INTERVAL_MINUTES =1;
    private static final int SCHEDULE_INTERVAL_SECONDS =(int) TimeUnit.MINUTES.toSeconds(SCHEDULE_INTERVAL_MINUTES);
    private static final int SYNC_FLEXTIME_SECONDS = SCHEDULE_INTERVAL_SECONDS;
    private static final String NEWS_JOB_TAG = "ScheduleUtilities class #";
    private static boolean sInitialized;

    synchronized  public static void scheduleRefresh(@NonNull final Context context)
    {
        if(sInitialized)return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,SCHEDULE_INTERVAL_MINUTES
                + SYNC_FLEXTIME_SECONDS
                ))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}
