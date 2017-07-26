package com.ujjwalkumargupta.assignment.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ujjwal Kumar Gupta on 25-Jul-17.
 */

public class TaskUtils {
    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        }
        if (string.isEmpty()) {
            return true;
        }
        if (string.equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String string) {
        return !TaskUtils.isEmpty(string);
    }

    public static boolean checkingSuccess(String response) {
        boolean success = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            success = jsonObject.getBoolean("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (success) {
            return true;
        } else return false;
    }
}
