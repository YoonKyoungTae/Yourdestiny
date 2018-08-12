package kr.co.diordna.yourdestiny;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

import kr.co.diordna.simplesharedpreferences.SSP;
import kr.co.diordna.yourdestiny.utils.DestinyMaker;
import kr.co.diordna.yourdestiny.utils.EtcUtil;
import kr.co.diordna.yourdestiny.utils.PlayProcess;
import kr.co.diordna.yourdestiny.utils.PrefKeys;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private View li_end_btns;
    private View li_antman_btn;
    private View li_replay_btn;
    private View li_share_btn;
    private View li_rate_btn;

    private TextView tv_center;
    private EditText et_name;
    private View fl_confirm;
    private LottieAnimationView animation_view;

    private String mUserName;
    private int mShareIndex = -1;

    private InterstitialAd mInterstitialStartAd;
    private InterstitialAd mInterstitialButtonAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAdmob();
    }

    private void initView() {
        tv_center = findViewById(R.id.tv_center);
        et_name = findViewById(R.id.et_name);
        fl_confirm = findViewById(R.id.fl_confirm);
        animation_view = findViewById(R.id.animation_view);
        li_end_btns = findViewById(R.id.li_end_btns);
        li_antman_btn = findViewById(R.id.li_antman_btn);
        li_replay_btn = findViewById(R.id.li_replay_btn);
        li_share_btn = findViewById(R.id.li_share_btn);
        li_rate_btn = findViewById(R.id.li_rate_btn);

        li_antman_btn.setOnClickListener(this);
        li_replay_btn.setOnClickListener(this);
        li_share_btn.setOnClickListener(this);
        li_rate_btn.setOnClickListener(this);
    }

    private void play(int seen) {
        switch (seen) {
            case 0:
                playOne();
                break;
            case 1:
                playTwo();
                break;
            case 2:
                playThree();
                break;
            case 3:
                playFour();
                break;
        }

    }

    private void playOne() {
        li_end_btns.setVisibility(View.GONE);
        tv_center.setText(getString(R.string.intro));
        PlayProcess.one(this, tv_center, new ProcessFinishCallback() {
            @Override
            public void onFinish() {
                play(1);
            }
        });
    }

    private void playTwo() {
        et_name.setText(null);
        tv_center.setText(getString(R.string.your_name));
        et_name.setVisibility(View.VISIBLE);
        fl_confirm.setVisibility(View.VISIBLE);
        PlayProcess.two(this, tv_center, et_name, fl_confirm);
        fl_confirm.setClickable(true);
        fl_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserName = et_name.getText().toString();
                if (TextUtils.isEmpty(mUserName)) {
                    Toast.makeText(MainActivity.this, getString(R.string.your_name_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                fl_confirm.setClickable(false);
                hideKeyboard();

                boolean isFirstUser = SSP.getBoolean(PrefKeys.FIRST_USER, true);
                if (isFirstUser) {
                    // 노광고
                    play(2);
                    SSP.openEdit().put(PrefKeys.FIRST_USER, false).apply();
                } else {
                    // 광고
                    if (mInterstitialButtonAd.isLoaded()) {
                        mInterstitialButtonAd.show();
                    } else {
                        play(2);
                    }
                }
            }
        });
    }

    private void playThree() {
        PlayProcess.three(this, tv_center,
                et_name,
                fl_confirm,
                animation_view, new ProcessFinishCallback() {
                    @Override
                    public void onFinish() {
                        play(3);
                    }
                });
    }

    private void playFour() {
        DestinyMaker maker = new DestinyMaker(this);

        String yourDestiny = maker.getResult(mUserName);
        mShareIndex = maker.getLastDestinyIndex();

        yourDestiny = String.format(yourDestiny, mUserName);
        tv_center.setText(yourDestiny);

        PlayProcess.four(this, tv_center, new ProcessFinishCallback() {
            @Override
            public void onFinish() {
                li_end_btns.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initAdmob() {

        mInterstitialStartAd = new InterstitialAd(this);
        mInterstitialStartAd.setAdUnitId("ca-app-pub-5942895690703066/5623573996");
//        mInterstitialStartAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialStartAd.loadAd(new AdRequest.Builder().build());

        mInterstitialButtonAd = new InterstitialAd(this);
        mInterstitialButtonAd.setAdUnitId("ca-app-pub-5942895690703066/2624686853");
//        mInterstitialButtonAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialButtonAd.loadAd(new AdRequest.Builder().build());

        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d("TEST", "onAdFailedToLoad: " + i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("TEST", "onAdLoaded: ");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });

        mInterstitialButtonAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                play(2);
                mInterstitialButtonAd.loadAd(new AdRequest.Builder().build());
            }
        });

        mInterstitialStartAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialStartAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                play(0);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                play(0);
            }
        });

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_name.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_antman_btn:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app.mingming.antman"));
                startActivity(intent);
                FirebaseAnalytics.getInstance(this).logEvent("click_antman", new Bundle());
                break;
            case R.id.li_replay_btn:
                play(0);
                FirebaseAnalytics.getInstance(this).logEvent("click_replay", new Bundle());
                break;
            case R.id.li_share_btn:
                EtcUtil.shareToApp(MainActivity.this, mUserName, mShareIndex);
                FirebaseAnalytics.getInstance(this).logEvent("click_share", new Bundle());
                break;
            case R.id.li_rate_btn:
                EtcUtil.goToReview(MainActivity.this);
                FirebaseAnalytics.getInstance(this).logEvent("click_rate", new Bundle());
                break;
        }
    }
}
