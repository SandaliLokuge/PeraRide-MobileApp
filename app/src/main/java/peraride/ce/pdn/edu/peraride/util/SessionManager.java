package peraride.ce.pdn.edu.peraride.util;

import android.content.Context;
import android.content.SharedPreferences;

import peraride.ce.pdn.edu.peraride.RideApplication;


public class SessionManager {
    private static final String PREF_NAME = "peraride.ce.pdn.edu.peraride.session";
    private static final String IS_LOGIN = "IsLogin";
    private static final String KEY_JWT_TOKEN = "JWTToken";
    private static final String KEY_USER = "UserId";


    private final static SessionManager instance =
            new SessionManager(RideApplication.getInstance().getApplicationContext());
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    private SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public void clearPref() {
        editor.clear();
        editor.commit();
    }

    public void createLoginSession(String token, String userId) {
        editor.putString(KEY_USER, userId);
        editor.putString(KEY_JWT_TOKEN, token);
        editor.putBoolean(IS_LOGIN, true);
        // commit changes
        editor.commit();
    }

    public String getJWTToken() {
        return pref.getString(KEY_JWT_TOKEN, null);
    }

    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getUserId() {
        return pref.getString(KEY_USER, null);
    }

}
