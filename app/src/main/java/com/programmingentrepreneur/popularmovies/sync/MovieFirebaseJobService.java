//package com.programmingentrepreneur.popularmovies.sync;
//
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import com.firebase.jobdispatcher.JobParameters;
//import com.firebase.jobdispatcher.JobService;
//
///**
// * Created by phili on 5/10/2017.
// */
//
//public class MovieFirebaseJobService extends JobService {
//
//    private AsyncTask<Void, Void, Void> mFetchMoviesTask;
//
//    @Override
//    public boolean onStartJob(final JobParameters jobParameters) {
//
//        mFetchMoviesTask = new AsyncTask<Void, Void, Void>(){
//            @Override
//            protected Void doInBackground(Void... voids) {
//                Context context = getApplicationContext();
//                MovieSyncTask.syncMovies(context);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                //  COMPLETED (6) Once the weather data is sync'd, call jobFinished with the appropriate arguements
//                jobFinished(jobParameters, false);
//            }
//        };
//
//        mFetchMoviesTask.execute();
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters job) {
//        if (mFetchMoviesTask != null) {
//            mFetchMoviesTask.cancel(true);
//        }
//        return true;
//    }
//}
