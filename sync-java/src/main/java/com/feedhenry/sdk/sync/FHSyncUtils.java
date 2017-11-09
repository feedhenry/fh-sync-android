/**
 * Copyright Red Hat, Inc, and individual contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feedhenry.sdk.sync;

import com.feedhenry.sdk.exceptions.HashException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FHSyncUtils {

    public interface Action1<T> {

        void doAction(T obj);
    }

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static final String TAG = "FHSyncUtils";

    /**
     * Creates special representation used to calculate hash of supplied {@link JSONArray}
     * @param object
     * @return JSON array representation used to calculate hash
     * @throws JSONException something went wrong when creating the object
     */
    private static JSONArray sortObj(JSONArray object) throws JSONException {
        JSONArray results = new JSONArray();
        for (int i = 0, length = object.length(); i < length; i++) {
            JSONObject obj = new JSONObject();
            obj.put("key", i + "");
            Object value = object.get(i);
            if (value instanceof JSONObject || value instanceof JSONArray) {
                obj.put("value", sortObj(value));
            } else {
                obj.put("value", value);
            }
            results.put(obj);
        }

        return results;
    }

    /**
     * Creates special representation used to calculate hash of supplied {@link JSONObject}
     * @param object
     * @return JSON array representation used to calculate hash
     * @throws JSONException something went wrong when creating the object
     */
    static JSONArray sortObj(JSONObject object) throws JSONException {
        JSONArray results = new JSONArray();
        JSONArray keys = object.names();
        List<String> sortedKeys = sortNames(keys);
        for (String sortedKey : sortedKeys) {
            JSONObject obj = new JSONObject();
            Object value = object.get(sortedKey);
            obj.put("key", sortedKey);
            if (value instanceof JSONObject || value instanceof JSONArray) {
                obj.put("value", sortObj(value));
            } else {
                obj.put("value", value);
            }
            results.put(obj);
        }

        return results;
    }

    /**
     * Converts {@link JSONArray} to it's text representation. All keys in {@link JSONObject}s are naturally ordered.
     * @param array JSON array
     * @return JSON text representation
     */
    public static String orderedJSONArrayToString(JSONArray array) {
        StringBuilder sb = new StringBuilder("[");
        int len = array.length();

        for (int i = 0; i < len; i += 1) {
            if (i > 0) {
                sb.append(",");
            }
            Object objAtPos = array.get(i);
            if (objAtPos instanceof JSONObject) {
                sb.append(orderedJSONObjectToString((JSONObject) objAtPos));
            } else if (objAtPos instanceof JSONArray) {
                sb.append(orderedJSONArrayToString((JSONArray) objAtPos));
            } else {
                sb.append(JSONObject.valueToString(objAtPos));
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Converts {@link JSONObject} to it's text representation. All keys in {@link JSONObject}s are naturally ordered.
     * @param jsonAtPos JSON object
     * @return JSON text representation
     */
    public static StringBuilder orderedJSONObjectToString(JSONObject jsonAtPos) {
        Iterator keys = new TreeSet<>(jsonAtPos.keySet()).iterator();
        StringBuilder sb = new StringBuilder("{");

        while (keys.hasNext()) {
            if (sb.length() > 1) {
                sb.append(',');
            }
            Object o = keys.next();
            sb.append(JSONObject.quote(o.toString()));
            sb.append(':');
            Object objAtKey = jsonAtPos.get(o.toString());
            if (objAtKey instanceof JSONObject) {
                sb.append(orderedJSONObjectToString((JSONObject) objAtKey));
            } else if (objAtKey instanceof JSONArray) {
                sb.append(orderedJSONArrayToString((JSONArray) objAtKey));
            } else {
                sb.append(JSONObject.valueToString(objAtKey));
            }
        }
        sb.append('}');
        return sb;
    }

    /**
     * Calculates SHA-1 hash used for Sync from the supplied {@link JSONArray}
     * @param object object to calculate hash from
     * @return hash in form of hexadecimal number
     * @throws JSONException problem when parsing JSON or creating JSON
     * @throws HashException unable to calculate hash
     */
    public static String generateObjectHash(JSONArray object) throws JSONException, HashException {
        JSONArray sorted = sortObj(object);
        return generateHash(orderedJSONArrayToString(sorted));
    }

    /**
     * Calculates SHA-1 hash used for Sync from the supplied {@link JSONObject}
     * @param object object to calculate hash from
     * @return hash in form of hexadecimal number
     * @throws JSONException problem when parsing JSON or creating JSON
     * @throws HashException unable to calculate hash
     */
    public static String generateObjectHash(JSONObject object) throws JSONException, HashException {
        JSONArray sorted = sortObj(object);
        return generateHash(orderedJSONArrayToString(sorted));
    }

    /**
     * Calculates SHA-1 of the supplied text.
     *
     * @param text text
     *
     * @return SHA-1 of the text
     */
    public static String generateHash(String text) throws HashException {
        try {
            String hashValue;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(text.getBytes("ASCII"));
            hashValue = encodeHex(md.digest());
            return hashValue;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new HashException(e);
        }
    }

    /**
     * Converts data to hexadecimal number.
     *
     * @param data data in array
     *
     * @return hexadecimal number as {@link String} value, e.g. "5f4675723d658919ede35fac62fade8c6397df1d"
     */
    private static String encodeHex(byte[] data) {
        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return new String(out);
    }

    private static Object sortObj(Object value) throws JSONException {
        if (value instanceof JSONArray) {
            return sortObj((JSONArray) value);
        } else if (value instanceof JSONObject) {
            return sortObj((JSONObject) value);
        } else {
            throw new IllegalArgumentException(String.format("A object %s was snuck into a JSON tree", value.toString()));
        }
    }

    private static List<String> sortNames(JSONArray names) throws JSONException {
        if (names == null) {
            return Collections.emptyList();
        }

        int length = names.length();
        ArrayList<String> sortedNames = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            sortedNames.add(names.getString(i));
        }
        Collections.sort(sortedNames);
        return sortedNames;
    }

    /**
     * Executes action only if obj is not null
     *
     * @param obj    object to be tested for null
     * @param action action where object is passed when not null
     */
    public static <T> void notNullDo(T obj, Action1<T> action) {
        if (obj != null) {
            action.doAction(obj);
        }
    }

}
