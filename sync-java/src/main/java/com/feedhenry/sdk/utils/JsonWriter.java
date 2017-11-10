package com.feedhenry.sdk.utils;

/**
 * Interface for stringyfing JSON.
 */
public interface JsonWriter {

    /**
     * Begins encoding of JSON array.
     *
     * @return self
     */
    JsonWriter beginArray();

    /**
     * Ends encoding of JSON array.
     *
     * @return self
     */
    JsonWriter endArray();

    /**
     * Begins encoding of JSON object.
     *
     * @return self
     */
    JsonWriter beginObject();

    /**
     * Ends encoding of JSON object.
     *
     * @return self
     */
    JsonWriter endObject();

    /**
     * Encodes key of the key-value pair in JSON object.
     *
     * @param name key
     *
     * @return self
     */
    JsonWriter key(String name);

    /**
     * Encodes value of the key-value pair in JSON object.
     *
     * @param value value
     *
     * @return self
     */
    JsonWriter value(Object value);

}
