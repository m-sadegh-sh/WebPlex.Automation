package ir.webplex.android.core;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.util.HashSet;

import ir.webplex.android.api.parameters.Permission;
import ir.webplex.android.api.responses.LoginResponse;
import ir.webplex.android.automation.AppController;
import ir.webplex.android.core.logging.Logger;

public class SessionManager implements Constants {
    public static final String TAG = SessionManager.class.getSimpleName();

    private static SessionManager mInstance;
    SharedPreferences mPreferences;
    Editor mEditor;
    private String mBaseUri;
    private HashSet<Permission> mPermissions;

    private SessionManager() {
        Logger.onInitiating(TAG);

        mPreferences = AppController.getInstance().getSharedPreferences(PREFERENCES_FILE_NAME, PREFERENCES_FILE_MODE);
        mEditor = mPreferences.edit();

        Logger.onInited(TAG);
    }

    public static synchronized SessionManager getInstance() {
        if (mInstance == null)
            mInstance = new SessionManager();

        return mInstance;
    }

    public String getBaseUri() {
        if (!TextUtils.isEmpty(mBaseUri))
            return mBaseUri;

        return mPreferences.getString(PREFERENCES_KEYS_BASE_URI, null);
    }

    public void setBaseUri(String baseUri, boolean doPersist) {
        if (doPersist) {
            mEditor.putString(PREFERENCES_KEYS_BASE_URI, baseUri);
            mEditor.commit();

            mBaseUri = null;
        } else
            mBaseUri = baseUri;
    }

    public String getToken() {
        return mPreferences.getString(PREFERENCES_KEYS_TOKEN, null);
    }

    public void setToken(String token) {
        mEditor.putString(PREFERENCES_KEYS_TOKEN, token);
        mEditor.commit();
    }

    public String getTitle() {
        return mPreferences.getString(PREFERENCES_KEYS_TITLE, null);
    }

    public void setTitle(String title) {
        mEditor.putString(PREFERENCES_KEYS_TITLE, title);
        mEditor.commit();
    }

    public String getEmail() {
        return mPreferences.getString(PREFERENCES_KEYS_EMAIL, null);
    }

    public void setEmail(String email) {
        mEditor.putString(PREFERENCES_KEYS_EMAIL, email);
        mEditor.commit();
    }

    public String getAvatar() {
        return mPreferences.getString(PREFERENCES_KEYS_AVATAR, null);
    }

    public void setAvatar(String avatar) {
        mEditor.putString(PREFERENCES_KEYS_AVATAR, avatar);
        mEditor.commit();
    }

    public void setPermissions(HashSet<Permission> permissions) {
        HashSet<String> names = new HashSet<>(permissions.size());

        for (Permission permission : permissions)
            names.add(permission.toString());

        mEditor.putStringSet(PREFERENCES_KEYS_PERMISSIONS, names);
        mEditor.commit();

        mPermissions = permissions;
    }

    public boolean hasPermission(Permission permission) {
        if (mPermissions == null) {
            HashSet<String> names = (HashSet) mPreferences.getStringSet(PREFERENCES_KEYS_PERMISSIONS, new HashSet<String>());

            mPermissions = new HashSet<>(names.size());

            for (String name : names)
                mPermissions.add(Enum.valueOf(Permission.class, name));
        }

        return mPermissions.contains(permission);
    }

    public void signIn(LoginResponse response) {
        setToken(response.getToken());
        setTitle(response.getTitle());
        setEmail(response.getEmail());
        setAvatar(response.getAvatar());
        setPermissions(response.getPermission());
    }

    public void signOut() {
        setToken(null);
    }

    public void applyNonPersisted() {
        if (!TextUtils.isEmpty(mBaseUri))
            setBaseUri(mBaseUri, true);
    }

    public void clearNonPersisted() {
        mBaseUri = null;
    }
}