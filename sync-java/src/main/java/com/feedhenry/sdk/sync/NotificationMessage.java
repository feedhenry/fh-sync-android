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

/**
 * The message object sent to the listener when an event happened.
 */
public class NotificationMessage {

    public static final String SYNC_STARTED_MESSAGE = "SYNC_STARTED";
    public static final String SYNC_COMPLETE_MESSAGE = "SYNC_COMPLETE";
    public static final String OFFLINE_UPDATE_MESSAGE = "OFFLINE_UPDATE";
    public static final String COLLISION_DETECTED_MESSAGE = "COLLISION_DETECTED";
    public static final String REMOTE_UPDATE_FAILED_MESSAGE = "REMOTE_UPDATE_FAILED";
    public static final String REMOTE_UPDATE_APPLIED_MESSAGE = "REMOTE_UPDATE_APPLIED";
    public static final String LOCAL_UPDATE_APPLIED_MESSAGE = "LOCAL_UPDATE_APPLIED";
    public static final String DELTA_RECEIVED_MESSAGE = "DELTA_RECEIVED";
    public static final String CLIENT_STORAGE_FAILED_MESSAGE = "CLIENT_STORAGE_FAILED";
    public static final String SYNC_FAILED_MESSAGE = "SYNC_FAILED";

    private String dataId;
    private String uid;
    private String codeMessage;
    private String extraMessage;

    public NotificationMessage(String dataId, String uid, String codeMessage, String extraMessage) {
        this.dataId = dataId;
        this.uid = uid;
        this.codeMessage = codeMessage;
        this.extraMessage = extraMessage;
    }

    /**
     * The id of the dataset associated with the event
     *
     * @return the id of the dataset associated with the event
     */
    public String getDataId() {
        return dataId;
    }

    /**
     * The id of the data record associated with the event
     *
     * @return the id of the data record associated with the event
     */
    public String getUID() {
        return uid;
    }

    /**
     * The code message associated with the event
     *
     * @return the code message associated with the event
     */
    public String getCodeMessage() {
        return codeMessage;
    }

    /**
     * Extra message associated with the event
     *
     * @return the extra message associated with the event
     */
    public String getMessage() {
        return extraMessage;
    }

    public String toString() {
        return "DataId:" + dataId + "-UID:" + uid + "-Code:" + codeMessage + "-Message:" + extraMessage;
    }
}
