package ru.mirea.elkinasa.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPrefStorage {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_EMAIL = "user_email";

    private final SharedPreferences prefs;

    public UserSharedPrefStorage(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean saveEmail(String email) {
        prefs.edit().putString(KEY_EMAIL, email).apply();
        return true;
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "guest");
    }

    public void clear() {
        prefs.edit().remove(KEY_EMAIL).apply();
    }
}
