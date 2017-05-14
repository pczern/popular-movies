package com.programmingentrepreneur.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by phili on 4/29/2017.
 */

public class MainApplication extends Application {
    public void onCreate(){
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build());

    }
}
