package com.kimsmik.kimsmikweddingapp;

import android.test.InstrumentationTestCase;

/**
 * Created by Kim on 2015/6/11.
 */
public class WebAPITest extends InstrumentationTestCase {
    public void test() throws Exception{
        int des = 1;
        int target = 1;

        assertEquals(des,target);
    }

    public void rrtest2() throws Exception{
        int des = 1;
        int target = 2;

        assertEquals(des,target);
    }
}
