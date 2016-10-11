package org.bitbucket.yuelvic.zumba.utils;

import com.shawnlin.preferencesmanager.PreferencesManager;

/**
 * Created by yuelvic on 10/11/16.
 */
public class Preference {

    public static void saveString(String key, String value) {
        PreferencesManager.putString(key, value);
    }

}
