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

public class FHSyncNotificationHandler {

    private FHSyncListener syncListener;

    public FHSyncNotificationHandler(FHSyncListener listener) {
        super();
        syncListener = listener;
    }
    public void setSyncListener(FHSyncListener listener) {
        syncListener = listener;
    }

    public void handleMessage(NotificationMessage msg) {
        if (syncListener != null) {
            switch (msg.getCodeMessage()) {
                case NotificationMessage.SYNC_STARTED_MESSAGE:
                    syncListener.onSyncStarted(msg);
                    break;
                case NotificationMessage.SYNC_COMPLETE_MESSAGE:
                    syncListener.onSyncCompleted(msg);
                    break;
                case NotificationMessage.OFFLINE_UPDATE_MESSAGE:
                    syncListener.onUpdateOffline(msg);
                    break;
                case NotificationMessage.COLLISION_DETECTED_MESSAGE:
                    syncListener.onCollisionDetected(msg);
                    break;
                case NotificationMessage.REMOTE_UPDATE_FAILED_MESSAGE:
                    syncListener.onRemoteUpdateFailed(msg);
                    break;
                case NotificationMessage.REMOTE_UPDATE_APPLIED_MESSAGE:
                    syncListener.onRemoteUpdateApplied(msg);
                    break;
                case NotificationMessage.LOCAL_UPDATE_APPLIED_MESSAGE:
                    syncListener.onLocalUpdateApplied(msg);
                    break;
                case NotificationMessage.DELTA_RECEIVED_MESSAGE:
                    syncListener.onDeltaReceived(msg);
                    break;
                case NotificationMessage.SYNC_FAILED_MESSAGE:
                    syncListener.onSyncFailed(msg);
                    break;
                case NotificationMessage.CLIENT_STORAGE_FAILED_MESSAGE:
                    syncListener.onClientStorageFailed(msg);
                default:
                    break;
            }
        }
    }
}
