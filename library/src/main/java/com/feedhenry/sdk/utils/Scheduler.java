package com.feedhenry.sdk.utils;

import com.feedhenry.sdk.sync.FHSyncNotificationHandler;
import com.feedhenry.sdk.sync.NotificationMessage;

import java.util.concurrent.Future;

/**
 * Created on 10/9/17.
 */
public interface Scheduler {

    /**
     * Schedule given code to repeat every specified number of milliseconds.
     *
     * @param r    code to run
     * @param rate repeat rate in milliseconds
     */
    Future<?> scheduleWithRate(Runnable r, long rate);

    /**
     * Schedule given code to execute immediately
     *
     * @param r code to run
     */
    Future<?> schedule(Runnable r);

    /**
     * Sets notification handler to call sync events from background thread.
     *
     * @param notificationHandler handler
     */
    void setNotificationHandler(FHSyncNotificationHandler notificationHandler);

    /**
     * Sends notification message to notification handler.
     *
     * @param message message to be sent
     */
    void sendNotificationMessage(NotificationMessage message);
}
