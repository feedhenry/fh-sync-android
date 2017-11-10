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
package com.feedhenry.sdk.utils;

import com.feedhenry.sdk.network.NetworkClient;
import com.feedhenry.sdk.storage.Storage;

/**
 * Class provides implementation abstraction for utility classes.
 */
public interface UtilFactory {

    /**
     * Provides logging functionality.
     * Single instance.
     *
     * @return logger
     */
    Logger getLogger();

    /**
     * Provides client id generation functionality.
     * Single instance.
     *
     * @return client id generator
     */
    ClientIdGenerator getClientIdGenerator();

    /**
     * Provides network client to execute network requests
     * Single instance.
     *
     * @return network client
     */
    NetworkClient getNetworkClient();

    /**
     * Provides persistent storage of data.
     * Single instance.
     *
     * @return storage object
     */
    Storage getStorage();

    /**
     * Provides capability of scheduling asynchronous task.
     * Single instance.
     *
     * @return scheuler
     */
    Scheduler getScheduler();

    /**
     * Creates new instance of JsonWriter, you can stringify your JSON objects with it.
     * @return new instance of JsonWriter
     */
    JsonWriter createJsonWriter();

}
