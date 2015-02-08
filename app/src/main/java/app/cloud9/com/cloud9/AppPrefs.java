package app.cloud9.com.cloud9;

/**
 * Created by chirag on 23/11/14.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {
    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String json_got = "json_got";
    private String sSc = "stars_collected";
    private String usn_saved = "usn";

    public AppPrefs(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS,
                Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    //Levels
    public int getjson_got() {
        return appSharedPrefs.getInt(json_got, 0);
    }

    public void setjson_got(int _level) {
        prefsEditor.putInt(json_got, _level).commit();
    }

    public String getUsn_saved() {
        return appSharedPrefs.getString(usn_saved, "");
    }

    public void setUsn_saved(String _usn_saved) {
        prefsEditor.putString(usn_saved, _usn_saved).commit();
    }


}