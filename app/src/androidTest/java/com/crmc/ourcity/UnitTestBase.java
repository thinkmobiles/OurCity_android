package com.crmc.ourcity;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class UnitTestBase  {

    @Test
    public void emptyTest() throws Exception{
        Assert.assertNotNull(null);
        Assert.assertFalse(true);
    }

}