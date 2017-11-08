package com.feedhenry.sdk.android;

import android.content.Context;
import android.os.Handler;
import com.feedhenry.sdk.sync.FHSyncNotificationHandler;
import com.feedhenry.sdk.sync.NotificationMessage;
import com.feedhenry.sdk.utils.Scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  Schedules task on the background thread and handles messages from the tasks.
 */
public class AndroidScheduler implements Scheduler {

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private FHSyncNotificationHandler notificationHandler;
    private Handler handler;

    public AndroidScheduler(Context ctx) {
        handler = new Handler(ctx.getMainLooper());
    }

    @Override
    public Future<?> scheduleWithRate(Runnable r, long rate) {
        return executorService.scheduleWithFixedDelay(r, rate, rate, TimeUnit.MILLISECONDS);
    }

    @Override
    public Future<?> schedule(Runnable r) {
        return executorService.schedule(r, 0, TimeUnit.SECONDS);
    }

    @Override
    public void setNotificationHandler(FHSyncNotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @Override
    public void sendNotificationMessage(NotificationMessage message) {
        handler.post(() -> notificationHandler.handleMessage(message));
    }
}
