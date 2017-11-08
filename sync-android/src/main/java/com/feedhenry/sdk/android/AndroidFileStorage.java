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
package com.feedhenry.sdk.android;

import android.content.Context;
import com.feedhenry.sdk.storage.FileStorage;
import com.feedhenry.sdk.storage.Storage;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

/**
 * Storage in Android local data store using files.
 */
public class AndroidFileStorage extends FileStorage implements Storage {

    private static final String STORAGE_FILE_EXT = ".sync.json";
    private static final int BUFFER_SIZE = 1024;
    private final WeakReference<Context> weakContext;

    public AndroidFileStorage(Context ctx) {
        weakContext = new WeakReference<>(ctx);
    }

    @Override
    protected String getFilePath(@NotNull String contentId) {
        return contentId + STORAGE_FILE_EXT;
    }

    @Override
    protected InputStream openFileInput(@NotNull String path) throws FileNotFoundException {
        Context ctx = weakContext.get();
        if (ctx != null) {
            return ctx.openFileInput(path);
        } else {
            throw new IllegalStateException("Con't open file when Context is destroyed");
        }
    }

    @Override
    protected OutputStream openFileOutput(@NotNull String path) throws FileNotFoundException {
        Context ctx = weakContext.get();
        if (ctx != null) {
            return ctx.openFileOutput(path, Context.MODE_PRIVATE);
        } else {
            throw new IllegalStateException("Con't open file when Context is destroyed");
        }
    }

}
