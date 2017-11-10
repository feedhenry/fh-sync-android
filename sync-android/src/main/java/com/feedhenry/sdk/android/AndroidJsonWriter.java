package com.feedhenry.sdk.android;

import com.feedhenry.sdk.utils.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

/**
 * JsonWriter that uses {@link android.util.JsonWriter}. For use in ordinary Android only.
 */
public class AndroidJsonWriter implements JsonWriter {

    private final android.util.JsonWriter stringer;
    private final StringWriter stringWriter;
    private boolean failedCreation = false;
    private String failureReason;
    private int opCount = 0;

    public AndroidJsonWriter() {
        stringWriter = new StringWriter();
        stringer = new android.util.JsonWriter(stringWriter);
    }

    @Override
    public JsonWriter beginArray() {
        opCount++;
        try {
            stringer.beginArray();
        } catch (IOException e) {
            logFailure(e.getMessage());
        }
        return this;
    }

    @Override
    public JsonWriter endArray() {
        opCount++;
        try {
            stringer.endArray();
        } catch (IOException e) {
            logFailure(e.getMessage());
        }
        return this;
    }

    @Override
    public JsonWriter beginObject() {
        opCount++;
        try {
            stringer.beginObject();
        } catch (IOException e) {
            logFailure(e.getMessage());
        }
        return this;
    }

    @Override
    public JsonWriter endObject() {
        opCount++;
        try {
            stringer.endObject();
        } catch (IOException e) {
            logFailure(e.getMessage());
        }
        return this;
    }

    @Override
    public JsonWriter key(String name) {
        opCount++;
        try {
            stringer.name(name);
        } catch (IOException e) {
            logFailure(e.getMessage());
        }
        return this;
    }

    @Override
    public JsonWriter value(Object value) {
        opCount++;
        try {
            if (value == null) {
                stringer.nullValue();
            } else if (value instanceof Number) {
                stringer.value((Number) value);
            } else if (value instanceof Boolean) {
                stringer.value((Boolean) value);
            } else if (value instanceof String) {
                stringer.value((String) value);
            } else {
                failedCreation = true;
            }
        } catch (IOException e) {
            logFailure(e.getMessage());
        }
        return this;
    }

    private void logFailure(String message) {
        if (!failedCreation) {
            failureReason = String.format(Locale.getDefault(), "%d: %s", opCount, message);
        }
        failedCreation = true;
    }

    /**
     * Return reason of the failure.
     *
     * @return
     */
    public String getFailureReason() {
        return failureReason;
    }

    @Override
    public String toString() {
        if (!failedCreation) {
            return stringWriter.toString();
        } else {
            throw new RuntimeException(failureReason);
        }
    }
}
