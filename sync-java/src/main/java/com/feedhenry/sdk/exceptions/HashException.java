package com.feedhenry.sdk.exceptions;

/**
 * Exception related to generating a hash.
 */
public class HashException extends Exception {

    public HashException(Throwable throwable) {
        super("Unable to calculate object's hash", throwable);
    }
}
