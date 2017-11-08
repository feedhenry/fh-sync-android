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
package com.feedhenry.sdk.storage;

import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * Storage using files in filesystem.
 */
public class FileStorage implements Storage {

    private static final String STORAGE_FILE_EXT = ".sync.json";
    private static final int BUFFER_SIZE = 1024;
    private static final String DEFAULT_STORAGE_DIR = System.getProperty("user.home") + File.separator + ".fh-sync-java";
    private final String storagePath;

    private static void writeStream(InputStream input, OutputStream output) throws IOException {
        if (input != null && output != null) {
            BufferedInputStream bis = new BufferedInputStream(input);
            BufferedOutputStream bos = new BufferedOutputStream(output);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            bis.close();
        }
    }

    /**
     * Creates file storage in default directory, e.g. <em>$HOME/.fh-sync-java</em>.
     */
    public FileStorage() {
        this(DEFAULT_STORAGE_DIR);
    }

    /**
     * Creates file storage in specified directory.
     *
     * @param path directory where data will be saved
     */
    public FileStorage(@NotNull String path) {
        this.storagePath = path;
    }

    /**
     * Returns path to the file stored in proper directory.
     *
     * @param contentId content identifier
     *
     * @return
     */
    protected String getFilePath(@NotNull String contentId) {
        return storagePath + File.separator + contentId + STORAGE_FILE_EXT;
    }

    /**
     * Opens file for input.
     *
     * @param path path to the file
     *
     * @return input stream
     *
     * @throws FileNotFoundException in case file was not found
     */
    protected InputStream openFileInput(@NotNull String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    /**
     * Opens file for input.
     *
     * @param path path to the file
     *
     * @return output stream
     *
     * @throws FileNotFoundException in case file path is not proper for the file to be opened
     */
    protected OutputStream openFileOutput(@NotNull String path) throws FileNotFoundException {
        return new FileOutputStream(path);
    }

    @Override
    public byte[] getContent(@NotNull String contentId) throws IOException {
        String filePath = getFilePath(contentId);
        InputStream fis = openFileInput(filePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileStorage.writeStream(fis, bos);
        return bos.toByteArray();
    }

    @Override
    public void putContent(@NotNull String contentId, @NotNull byte[] content) throws IOException {
        String filePath = getFilePath(contentId);
        OutputStream fos = openFileOutput(filePath);
        ByteArrayInputStream bis = new ByteArrayInputStream(content);
        FileStorage.writeStream(bis, fos);
    }

}
