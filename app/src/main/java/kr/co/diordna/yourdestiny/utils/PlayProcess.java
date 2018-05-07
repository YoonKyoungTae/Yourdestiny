package kr.co.diordna.yourdestiny.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import kr.co.diordna.yourdestiny.MainActivity;
import kr.co.diordna.yourdestiny.ProcessFinishCallback;
import kr.co.diordna.yourdestiny.R;

/**
 * Created by ryans on 2018-05-03.
 */

public class PlayProcess {

    public static void one(Context c, final TextView v, final ProcessFinishCallback processFinishCallback) {
        Animation fadeIn = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.fade_out);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.startAnimation(fadeOut);
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                processFinishCallback.onFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        v.startAnimation(fadeIn);
    }

    public static void two(Context c, TextView tv_center, EditText et_name, View fl_confirm) {
        Animation fadeIn = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.fade_in);

        tv_center.startAnimation(fadeIn);
        et_name.startAnimation(fadeIn);
        fl_confirm.startAnimation(fadeIn);
    }

    public static void three(Context c, final TextView tv_center, final EditText et_name, final View fl_confirm, final View lottie, final ProcessFinishCallback processFinishCallback) {
        final Animation fadeIn = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.fade_out);
        final Animation lottieFadeOut = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.fade_out);

        tv_center.startAnimation(fadeOut);
        et_name.startAnimation(fadeOut);
        fl_confirm.startAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_center.setVisibility(View.GONE);
                et_name.setVisibility(View.GONE);
                fl_confirm.setVisibility(View.GONE);

                lottie.setVisibility(View.VISIBLE);
                lottie.startAnimation(fadeIn);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottie.startAnimation(lottieFadeOut);
                    }
                }, 3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        lottieFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lottie.setVisibility(View.GONE);
                processFinishCallback.onFinish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static void four(MainActivity c, TextView tv_center, final ProcessFinishCallback processFinishCallback) {
        Animation fadeIn = AnimationUtils.loadAnimation(c.getApplicationContext(), R.anim.fade_in);

        tv_center.startAnimation(fadeIn);
        tv_center.setVisibility(View.VISIBLE);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                processFinishCallback.onFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
