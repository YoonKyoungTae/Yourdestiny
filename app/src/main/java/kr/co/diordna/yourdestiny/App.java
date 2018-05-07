package kr.co.diordna.yourdestiny;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;
import kr.co.diordna.simplesharedpreferences.SSP;

/**
 * Created by ryans on 2018-05-03.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, "ca-app-pub-5942895690703066~4728106018");
        Fabric.with(this, new Crashlytics());
        SSP.init(this);
    }
}
