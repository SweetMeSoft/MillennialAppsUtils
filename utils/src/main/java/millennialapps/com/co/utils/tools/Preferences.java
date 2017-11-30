package millennialapps.com.co.utils.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by erick on 1/7/2017.
 */

public class Preferences {

    public static final String PF_DEFAULT = "";
    public static final String PF_SUGGESTIONS = "SuggestionsFile";
    public static final String PF_APP = "appFile";
    public static final String PF_USER = "userFile";

    public static final String K_FIRST_TIME = "firstTime";
    public static final String K_VERSION = "version";
    public static final String K_AVOID_ELEVATORS = "avoid_elevators";
    public static final String K_AVOID_STAIRS = "avoid_stairs";
    public static final String K_AVOID_ELECTRIC_STAIRS = "avoid_electric_stairs";
    public static final String K_USER = "user";

    public static void saveObjectAsJson(Context context, String pfFile, String key, Object object) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        SharedPreferences.Editor editor = pf.edit();

        editor.putString(key, new Gson().toJson(object));
        editor.apply();
    }

    public static Object loadObject(Context context, String pfFile, String key, Class myClass) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        String object = pf.getString(key, "");
        return new Gson().fromJson(object, myClass);
    }

    public static LinkedList<String> getSuggestions(Context context) {
        LinkedList<String> suggestions = new LinkedList<>();
        SharedPreferences pf = context.getSharedPreferences(
                PF_SUGGESTIONS, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = pf.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            suggestions.add(entry.getValue().toString());
        }
        return suggestions;
    }

    public static void saveSuggestion(Context context, String value) {
        put(context, PF_SUGGESTIONS, value, value);
    }

    public static void clearPreferences(Context context, String pfFile) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        pf.edit().clear().apply();
    }

    public static void put(Context context, String pfFile, String key, String value) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        SharedPreferences.Editor editor = pf.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void put(Context context, String pfFile, String key, int value) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        SharedPreferences.Editor editor = pf.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static SharedPreferences getPreferenceFile(Context context, String pfFile) {
        SharedPreferences pf;
        if (pfFile.equals(PF_DEFAULT)) {
            pf = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            pf = context.getSharedPreferences(pfFile, Context.MODE_PRIVATE);
        }
        return pf;
    }

    public static void put(Context context, String pfFile, String key, boolean value) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        SharedPreferences.Editor editor = pf.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(Context context, String pfFile, String key) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        return pf.getString(key, "");
    }

    public static int getInt(Context context, String pfFile, String key) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        return pf.getInt(key, 0);
    }

    public static boolean getBoolean(Context context, String pfFile, String key) {
        SharedPreferences pf = getPreferenceFile(context, pfFile);
        return pf.getBoolean(key, false);
    }
}