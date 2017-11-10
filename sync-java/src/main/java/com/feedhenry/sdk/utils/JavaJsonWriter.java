package com.feedhenry.sdk.utils;

import org.json.JSONStringer;

/**
 * JsonWriter that uses org.json library. For use in ordinary JVM only.
 */

public class JavaJsonWriter implements JsonWriter {

    private final JSONStringer stringer;

    public JavaJsonWriter() {
        stringer = new JSONStringer();
    }

    @Override
    public JsonWriter beginArray() {
        stringer.array();
        return this;
    }

    @Override
    public JsonWriter endArray() {
        stringer.endArray();
        return this;
    }

    @Override
    public JsonWriter beginObject() {
        stringer.object();
        return this;
    }

    @Override
    public JsonWriter endObject() {
        stringer.endObject();
        return this;
    }

    @Override
    public JsonWriter key(String name) {
        stringer.key(name);
        return this;
    }

    @Override
    public JsonWriter value(Object value) {
        stringer.value(value);
        return this;
    }

    @Override
    public String toString() {
        return stringer.toString();
    }
}
