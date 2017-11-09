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

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class FHSyncUtilsTest {

    @Test
    public void testStringHash() throws Exception {
        String text = "test";
        String hashed = FHSyncUtils.generateHash(text);
        String expected = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";
        System.out.println("hashed = " + hashed);
        assertEquals(expected, hashed);
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
        obj.put("testNumKey", 10);
        obj.put("testBoolKey", true);
        obj.put("testKey", "Test Data");
        JSONArray arr = new JSONArray();
        arr.put("obj1");
        obj.put("testArrayKey", arr);
        arr.put("obj2");
        JSONObject jsobj = new JSONObject();
        obj.put("testDictKey", jsobj);
        jsobj.put("obj4key", "obj4");
        jsobj.put("obj3key", "obj3");
        String hash = FHSyncUtils.generateObjectHash(obj);
        System.out.println("Generated hash = " + hash);
        String expected = "5f4675723d658919ede35fac62fade8c6397df1d";
        assertEquals(expected, hash);
    }

    private class SomethingToBeTested {
        public int a;
    }

    @Test
    public void testNotNullDoNotNull() {
        SomethingToBeTested obj = new SomethingToBeTested();
        obj.a = 1;
        FHSyncUtils.notNullDo(obj, o -> o.a = o.a + 1);
        assertEquals(2, obj.a);
    }

    @Test
    public void testNotNullDoNull() {
        SomethingToBeTested obj = null;
        FHSyncUtils.notNullDo(obj, o -> o.a = o.a + 1);
    }

}
