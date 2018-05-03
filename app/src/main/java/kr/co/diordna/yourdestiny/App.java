package kr.co.diordna.yourdestiny;

import android.app.Application;

import kr.co.diordna.simplesharedpreferences.SSP;

/**
 * Created by ryans on 2018-05-03.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SSP.init(this);
    }
}
