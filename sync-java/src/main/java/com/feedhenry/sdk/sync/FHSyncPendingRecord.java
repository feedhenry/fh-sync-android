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

import com.feedhenry.sdk.exceptions.HashException;
import com.feedhenry.sdk.utils.UtilFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class FHSyncPendingRecord {

    private final UtilFactory utilFactory;
    private boolean inFight;

    private Date inFlightDate;

    private boolean crashed;

    private boolean delayed = false;

    private String action;

    private long timestamp;

    private String uid;

    private FHSyncDataRecord preData;

    private FHSyncDataRecord postData;

    private String hashValue;

    private int crashedCount;

    private static final String KEY_INFLIGHT = "inFlight";

    private static final String KEY_ACTION = "action";

    private static final String KEY_TIMESTAMP = "timestamp";

    private static final String KEY_UID = "uid";

    private static final String KEY_PRE = "pre";

    private static final String KEY_PRE_HASH = "preHash";

    private static final String KEY_POST = "post";

    private static final String KEY_POST_HASH = "postHash";

    private static final String KEY_INFLIGHT_DATE = "inFlightDate";

    private static final String KEY_CRASHED = "crashed";
    private static final String KEY_DELAYED = "delayed";
    private static final String KEY_WAITING_FOR = "waitingFor";

    private static final String KEY_HASH = "hash";
    private String waitingFor;

    public FHSyncPendingRecord(UtilFactory utilFactory) {
        this.timestamp = new Date().getTime();
        this.utilFactory = utilFactory;
    }

    public JSONObject getJSON() throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put(KEY_INFLIGHT, this.inFight);
        ret.put(KEY_CRASHED, this.crashed);
        ret.put(KEY_DELAYED, this.delayed);
        ret.put(KEY_TIMESTAMP, this.timestamp);
        if (this.inFlightDate != null) {
            ret.put(KEY_INFLIGHT_DATE, this.inFlightDate.getTime());
        }
        if (this.action != null) {
            ret.put(KEY_ACTION, this.action);
        }
        if (this.uid != null) {
            ret.put(KEY_UID, this.uid);
        }
        if (this.preData != null) {
            ret.put(KEY_PRE, this.preData.getData());
            ret.put(KEY_PRE_HASH, this.preData.getHashValue());
        }
        if (this.postData != null) {
            ret.put(KEY_POST, this.postData.getData());
            ret.put(KEY_POST_HASH, this.postData.getHashValue());
        }

        if (this.waitingFor != null) {
            ret.put(KEY_WAITING_FOR, waitingFor);
        }

        return ret;
    }

    public static FHSyncPendingRecord fromJSON(UtilFactory utilFactory, JSONObject obj) throws JSONException {
        FHSyncPendingRecord record = new FHSyncPendingRecord(utilFactory);
        if (obj.has(KEY_INFLIGHT)) {
            record.setInFlight(obj.getBoolean(KEY_INFLIGHT));
        }
        if (obj.has(KEY_INFLIGHT_DATE)) {
            record.setInFlightDate(new Date(obj.getLong(KEY_INFLIGHT_DATE)));
        }
        if (obj.has(KEY_CRASHED)) {
            record.setCrashed(obj.getBoolean(KEY_CRASHED));
        }
        if (obj.has(KEY_TIMESTAMP)) {
            record.setTimestamp(obj.getLong(KEY_TIMESTAMP));
        }
        if (obj.has(KEY_ACTION)) {
            record.setAction(obj.getString(KEY_ACTION));
        }
        if (obj.has(KEY_UID)) {
            record.setUid(obj.getString(KEY_UID));
        }
        if (obj.has(KEY_PRE)) {
            FHSyncDataRecord preData = new FHSyncDataRecord(utilFactory);
            preData.setData(obj.getJSONObject(KEY_PRE));
            preData.setHashValue(obj.getString(KEY_PRE_HASH));
            record.setPreData(preData);
        }
        if (obj.has(KEY_POST)) {
            FHSyncDataRecord postData = new FHSyncDataRecord(utilFactory);
            postData.setData(obj.getJSONObject(KEY_POST));
            postData.setHashValue(obj.getString(KEY_POST_HASH));
            record.setPostData(postData);
        }

        if (obj.has(KEY_DELAYED)) {
            record.delayed = obj.getBoolean(KEY_DELAYED);
        }

        if (obj.has(KEY_WAITING_FOR)) {
            record.waitingFor = obj.getString(KEY_WAITING_FOR);
        }

        return record;
    }

    public boolean equals(Object that) {
        try {
            return this == that || that instanceof FHSyncPendingRecord && this.getHashValue().equals(((FHSyncPendingRecord) that).getHashValue());
        } catch (JSONException e) {
            return false;
        }
    }

    public String toString() {
        try {
            return this.getJSON().toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInFlight() {
        return inFight;
    }

    public void setInFlight(boolean inFight) {
        this.inFight = inFight;
    }

    public Date getInFlightDate() {
        return inFlightDate;
    }

    public void setInFlightDate(Date inFlightDate) {
        this.inFlightDate = inFlightDate;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public FHSyncDataRecord getPreData() {
        return preData;
    }

    public void setPreData(FHSyncDataRecord preData) {
        this.preData = preData;
    }

    public FHSyncDataRecord getPostData() {
        return postData;
    }

    public void setPostData(FHSyncDataRecord postData) {
        this.postData = postData;
    }

    public String getHashValue() throws JSONException {
        if (this.hashValue == null) {
            JSONObject jsonobj = this.getJSON();
            try {
                this.hashValue = FHSyncUtils.generateObjectHash(jsonobj);
            } catch (HashException e) {
                throw new RuntimeException(e);
            }
        }
        return this.hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    public int getCrashedCount() {
        return crashedCount;
    }

    public void incrementCrashCount() {
        crashedCount++;
    }

    public void setCrashedCount(int crashedCount) {
        this.crashedCount = crashedCount;
    }

    public boolean isDelayed() {
        return this.delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    public String getWaitingFor() {
        return waitingFor;
    }

    public void setWaitingFor(String waitingFor) {
        this.waitingFor = waitingFor;
    }

}
