package com.feedhenry.sdk.test;

import com.feedhenry.sdk.network.NetworkClient;
import com.feedhenry.sdk.storage.FileStorage;
import com.feedhenry.sdk.storage.Storage;
import com.feedhenry.sdk.utils.*;

/**
 * Created on 10/29/17.
 */
public class TestUtilFactory implements UtilFactory {

    public final static UtilFactory UTIL_FACTORY = new TestUtilFactory();
    private final TestLogger logger;
    private final FileStorage storage;

    private TestUtilFactory() {
        logger = new TestLogger();
        storage = new FileStorage();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public ClientIdGenerator getClientIdGenerator() {
        return null;
    }

    @Override
    public NetworkClient getNetworkClient() {
        return null;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public Scheduler getScheduler() {
        return null;
    }

    @Override
    public JsonWriter createJsonWriter() {
        return new JavaJsonWriter();
    }
}
