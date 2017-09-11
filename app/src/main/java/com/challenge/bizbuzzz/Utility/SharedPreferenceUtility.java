package com.challenge.bizbuzzz.Utility;

import android.content.Context;

/**
 * Created by Guidezie on 11-09-2017.
 */

public class SharedPreferenceUtility {

    private static final String APP_PREF = "BIZBUZZZ";
    public static final String PASSWORD = "password";

    public static String getPasswordList(Context context) {
        return context.getSharedPreferences(APP_PREF,Context.MODE_PRIVATE).getString(PASSWORD, null);
    }

    public static void setPasswordList(Context context,String passwordList) {
        context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE).edit().putString(PASSWORD, passwordList).commit();
    }
}
