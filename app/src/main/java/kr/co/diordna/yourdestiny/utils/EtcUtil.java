package kr.co.diordna.yourdestiny.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Random;

import kr.co.diordna.yourdestiny.R;

/**
 * Created by ryans on 2018-05-07.
 */

public class EtcUtil {

    public static int getRandomInt() {
        Random rand = new Random();
        return rand.nextInt(5);
    }

    public static ArrayList<String> getResultList(Context context) {
        ArrayList<String> results = new ArrayList<>();
        results.add(context.getString(R.string.end_destiny_die1));
        results.add(context.getString(R.string.end_destiny_die2));
        results.add(context.getString(R.string.end_destiny_die3));
        results.add(context.getString(R.string.end_destiny_live1));
        results.add(context.getString(R.string.end_destiny_live2));
        results.add(context.getString(R.string.end_destiny_live3));
        return results;
    }

    public static ArrayList<String> getShareTextList(Context context) {
        ArrayList<String> results = new ArrayList<>();
        results.add(context.getString(R.string.share_destiny_die1));
        results.add(context.getString(R.string.share_destiny_die2));
        results.add(context.getString(R.string.share_destiny_die3));
        results.add(context.getString(R.string.share_destiny_live1));
        results.add(context.getString(R.string.share_destiny_live2));
        results.add(context.getString(R.string.share_destiny_live3));
        return results;
    }

    public static void goToReview(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void shareToApp(Context context, String user, int shareIndex) {
        ArrayList<String> shareTexts = getShareTextList(context);
        String shareText = shareTexts.get(shareIndex);
        String result = String.format(shareText, user);

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, result);

        Intent chooser = Intent.createChooser(intent, context.getString(R.string.share_title));
        context.startActivity(chooser);
    }
}
