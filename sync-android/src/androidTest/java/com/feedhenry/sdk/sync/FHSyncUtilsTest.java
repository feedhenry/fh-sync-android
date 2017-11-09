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

import android.support.test.runner.AndroidJUnit4;
import com.feedhenry.sdk.android.AndroidUtilFactory;
import com.feedhenry.sdk.utils.UtilFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static com.feedhenry.sdk.utils.Logger.LOG_LEVEL_VERBOSE;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FHSyncUtilsTest {

    private UtilFactory utilFactory;

    @Before
    public void setUp() throws Exception {
        utilFactory = new AndroidUtilFactory(getContext());
        utilFactory.getLogger().setLogLevel(LOG_LEVEL_VERBOSE);
    }

    @Test
    public void testStringHash() throws Exception {
        String text = "test";
        String hashed = FHSyncUtils.generateHash(text);
        String expeted = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        System.out.println("hashed = " + hashed);
        assertEquals(expeted, hashed);
    }

    @Test
    public void testGenerateObjHash() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("testKey", "Test Data");
        obj.put("testBoolKey", true);
        obj.put("testNumKey", 10);
        JSONArray arr = new JSONArray();
        arr.put("obj1");
        arr.put("obj2");
        obj.put("testArrayKey", arr);
        JSONObject jsobj = new JSONObject();
        jsobj.put("obj3key", "obj3");
        jsobj.put("obj4key", "obj4");
        obj.put("testDictKey", jsobj);
        String hash = FHSyncUtils.generateObjectHash(obj);
        System.out.println("Generated hash = " + hash);
        String expected = "5f4675723d658919ede35fac62fade8c6397df1d";
        assertEquals(expected, hash);
    }

    @Test
    public void testHashConsistence() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("testKey", "Test Data");
        obj.put("testBoolKey", true);
        obj.put("testNumKey", 10);
        JSONArray arr = new JSONArray();
        arr.put("obj1");
        arr.put("obj2");
        obj.put("testArrayKey", arr);
        JSONObject jsobj = new JSONObject();
        jsobj.put("obj3key", "obj3");
        jsobj.put("obj4key", "obj4");
        obj.put("testDictKey", jsobj);
        System.out.println("/"+FHSyncUtils.orderedJSONArrayToString(FHSyncUtils.sortObj(obj))+"/");
    }

}
