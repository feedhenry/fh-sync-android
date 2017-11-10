package com.feedhenry.sdk.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static com.feedhenry.sdk.test.TestUtilFactory.UTIL_FACTORY;
import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link JavaJsonWriter}
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class JavaJsonWriterTest {

    private final static String TEST_JSON = "{\"this is boolean\":true,\"this is a string\":\"Žluťoučký kůň úpěl ďábelské ódy\",\"this is a number\":31212,\"this is a long\":9007199254740992,\"this is null\":null,\"this is a decimal\":0.2546787,\"this is an object\":{\"this is boolean\":true,\"this is a string\":\"Žluťoučký kůň úpěl ďábelské ódy\",\"this is a number\":31212,\"this is a long\":9007199254740992,\"this is null\":null,\"this is a decimal\":0.2546787,\"this is an array\":[false,\"Druhý Žluťoučký kůň úpěl ďábelské ódy\",454545,0.21246568,9007199254740992,null,[\"a\",\"b\",\"c\",null,{\"first\":1,\"second\":2,\"third\":3}]]},\"this is an array\":[false,\"Druhý Žluťoučký kůň úpěl ďábelské ódy\",454545,0.21246568,9007199254740992,null,[\"a\",\"b\",\"c\",null,{\"first\":1,\"second\":2,\"third\":3}]]}";

    private static final long JSON_MAX_INT = 9007199254740992L;

    @Test
    public void testJsonStringifierObject() {
        JsonWriter jw = UTIL_FACTORY.createJsonWriter();

        jw.beginObject();
        {
            jw.key("this is boolean");
            jw.value(true);
            jw.key("this is a string");
            jw.value("Žluťoučký kůň úpěl ďábelské ódy");
            jw.key("this is a number");
            jw.value(31212);
            jw.key("this is a long");
            jw.value(JSON_MAX_INT);
            jw.key("this is null");
            jw.value(null);
            jw.key("this is a decimal");
            jw.value(0.2546787);
            jw.key("this is an object");
            jw.beginObject();
            {
                jw.key("this is boolean");
                jw.value(true);
                jw.key("this is a string");
                jw.value("Žluťoučký kůň úpěl ďábelské ódy");
                jw.key("this is a number");
                jw.value(31212);
                jw.key("this is a long");
                jw.value(JSON_MAX_INT);
                jw.key("this is null");
                jw.value(null);
                jw.key("this is a decimal");
                jw.value(0.2546787);
                jw.key("this is an array");
                jw.beginArray();
                {
                    jw.value(false);
                    jw.value("Druhý Žluťoučký kůň úpěl ďábelské ódy");
                    jw.value(454545);
                    jw.value(0.21246568);
                    jw.value(JSON_MAX_INT);
                    jw.value(null);
                    jw.beginArray();
                    {
                        jw.value("a");
                        jw.value("b");
                        jw.value("c");
                        jw.value(null);
                        jw.beginObject();
                        {
                            jw.key("first");
                            jw.value(1);
                            jw.key("second");
                            jw.value(2);
                            jw.key("third");
                            jw.value(3);
                        }
                        jw.endObject();
                    }
                    jw.endArray();
                }
                jw.endArray();
            }
            jw.endObject();
            jw.key("this is an array");
            jw.beginArray();
            {
                jw.value(false);
                jw.value("Druhý Žluťoučký kůň úpěl ďábelské ódy");
                jw.value(454545);
                jw.value(0.21246568);
                jw.value(JSON_MAX_INT);
                jw.value(null);
                jw.beginArray();
                {
                    jw.value("a");
                    jw.value("b");
                    jw.value("c");
                    jw.value(null);
                    jw.beginObject();
                    {
                        jw.key("first");
                        jw.value(1);
                        jw.key("second");
                        jw.value(2);
                        jw.key("third");
                        jw.value(3);
                    }
                    jw.endObject();
                }
                jw.endArray();
            }
            jw.endArray();
            jw.endObject();
        }
        assertEquals(TEST_JSON, jw.toString());
    }

}
