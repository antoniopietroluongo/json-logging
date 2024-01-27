package org.example;

import org.apache.commons.lang3.ClassUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class for JSON logging.
 * @author Antonio Pietroluongo
 */
public class JsonLoggingHelper {
    private final JSONObject jo = new JSONObject();


    /**
     * Put a key/value pair in the JsonLoggingHelper.
     * @param key A key string
     * @param obj An object
     * @return The current object instance
     */
    public JsonLoggingHelper add(final String key, Object obj) {
        if (key == null)
            return this;
        if (obj == null) {
            jo.put(key, JSONObject.NULL);
            return this;
        }
        try {
            if (ClassUtils.isPrimitiveOrWrapper(obj.getClass())
                    || obj instanceof List<?>
                    || obj instanceof JSONObject)
                jo.put(key, obj);
            else if (obj instanceof  String) {
                var text = (String) obj;
                if (text.isEmpty())
                    jo.put(key, text);
                else if (text.charAt(0) == '[' && text.charAt(text.length() - 1) == ']')
                    jo.put(key, new JSONArray(text));
                else if (text.charAt(0) == '{' && text.charAt(text.length() - 1) == '}')
                    jo.put(key, new JSONObject(text));
                else
                    jo.put(key, text);
            }
            else
                jo.put(key, new JSONObject(obj));
        } catch (JSONException e) {
            jo.put(key, obj);
        }
        return this;
    }

    /**
     * Log a message at the INFO level.
     * @param logger the Logger instance in use
     */
    public void info(final Logger logger) {
        logger.log(Level.INFO, jo.toString());
        jo.clear();
    }

    /**
     * Log a message at the CONFIG level.
     * @param logger the Logger instance in use
     */
    public void config(final Logger logger) {
        logger.log(Level.CONFIG, jo.toString());
        jo.clear();
    }

    /**
     * Log a message at the SEVERE level.
     * @param logger the Logger instance in use
     */
    public void severe(final Logger logger) {
        logger.log(Level.SEVERE, jo.toString());
        jo.clear();
    }
}