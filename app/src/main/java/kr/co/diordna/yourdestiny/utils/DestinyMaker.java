package kr.co.diordna.yourdestiny.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.diordna.simplesharedpreferences.SSP;

/**
 * Created by ryans on 2018-05-07.
 */

public class DestinyMaker {

    private Context mContext;
    private int lastDestinyIndex = -1;

    public DestinyMaker(Context c) {
        mContext = c;
    }

    public String getResult(String userName) {
        int destinyIndex = getDestinyIndex(userName);

        if (destinyIndex == -1) {
            destinyIndex = EtcUtil.getRandomInt();
            putDestinyIndex(userName, destinyIndex);
        }
        lastDestinyIndex = destinyIndex;
        return EtcUtil.getResultList(mContext).get(destinyIndex);
    }

    public int getLastDestinyIndex() {
        return lastDestinyIndex;
    }

    private int getDestinyIndex(String user) {
        int destinyIndex = -1;
        String destinyNote = SSP.getString(PrefKeys.DESTINY_NOTE_KEY, "");
        if (TextUtils.isEmpty(destinyNote)) {
            return destinyIndex;
        }

        try {
            JSONObject newObj = new JSONObject(destinyNote);

            user = user.toLowerCase().trim();
            user = user.replace(" ", "");

            destinyIndex = newObj.getInt(user);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("TEST", "destinyIndex: " + destinyIndex);
        Log.d("TEST", "destinyNote: " + destinyNote);
        return destinyIndex;
    }

    private void putDestinyIndex(String user, int destinyIndex) {
        String destinyNote = SSP.getString(PrefKeys.DESTINY_NOTE_KEY, "");
        JSONObject updateNode = null;

        try {

            if (TextUtils.isEmpty(destinyNote)) {
                updateNode = new JSONObject();
            } else {
                updateNode = new JSONObject(destinyNote);
            }

            user = user.toLowerCase().trim();
            user = user.replace(" ", "");

            updateNode.put(user, destinyIndex);
            SSP.openEdit().put(PrefKeys.DESTINY_NOTE_KEY, updateNode.toString()).apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
