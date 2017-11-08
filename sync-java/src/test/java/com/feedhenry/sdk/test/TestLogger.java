package com.feedhenry.sdk.test;

import com.feedhenry.sdk.utils.Logger;

/**
 * Created on 11/8/17.
 */

public class TestLogger implements Logger {

    @Override
    public void v(String tag, String message) {
        System.out.println("V/"+tag+"/"+message);
    }

    @Override
    public void d(String tag, String message) {
        System.out.println("D/"+tag+"/"+message);
    }

    @Override
    public void i(String tag, String message) {
        System.out.println("I/"+tag+"/"+message);
    }

    @Override
    public void w(String tag, String message) {
        System.out.println("W/"+tag+"/"+message);
    }

    @Override
    public void e(String tag, String message, Throwable pThrowable) {
        System.err.println("E/"+tag+"/"+message);
    }

    @Override
    public void setLogLevel(int logLevel) {

    }
}
