/**
 * ExtensionTest.java    2011-07-30
 *
 * Copyright 2011, Adam Lesperance
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */



package com.lespea.cef.utils.tests;

//~--- non-JDK imports --------------------------------------------------------

import com.lespea.cef.utils.StringUtils;

import junit.framework.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


//~--- classes ----------------------------------------------------------------

/**
 * Class description
 *
 *
 * @version 1.0, 2011-07-30
 * @author Adam Lesperance
 */
public class ExtensionTest {

    /**
     * List of strings that should throw an InvalidField exception
     *
     * @return array of bad strings to process
     */
    @DataProvider
    public Object[][] badKeyStrings() {
        return new Object[][] {
            { "blahblah\r", "" }, { "blahblah\n", "" }, { "blahblah\t", "" }, { "blahblah ", "" }
        };
    }


    /**
     * List of strings that shouldn't be escaped at all.
     *
     * @return the grouping of strings to process
     */
    @DataProvider
    public Object[][] normalKeyStrins() {
        return new Object[][] {
            { TestHelpers.UNICODE_STRING + "blahblahblah" + TestHelpers.UNICODE_STRING,
              TestHelpers.UNICODE_STRING + "blahblahblah" + TestHelpers.UNICODE_STRING }
        };
    }


    /**
     * Verify that an invalid extension key is marked as such
     *
     * @param badKey
     *            string that should be an invalid extension key
     * @param ignoreString
     *            *not used
     */
    @Test(dataProvider = "badKeyStrings")
    public void testInvalidExtensionKey( final String badKey, final String ignoreString ) {
        Assert.assertFalse( StringUtils.isValidExtensionKey( badKey ) );
    }


    /**
     * Makes sure the escape method is thread safe.
     *
     * @param okKey
     *            string that should be a valid extension key
     * @param ignoreString
     *            *not used
     */
    @Test(
        dataProvider    = "normalKeyStrins",
        threadPoolSize  = 100,
        invocationCount = 50
    )
    public void testKeyThreads( final String okKey, final String ignoreString ) {
        Assert.assertTrue( StringUtils.isValidExtensionKey( okKey ) );
    }


    /**
     * Verify that a null extension key is processed correctly
     */
    @Test
    public void testNullExtensionKey() {
        Assert.assertFalse( StringUtils.isValidExtensionKey( null ) );
    }


    /**
     * Verify that a valid field is marked as such
     *
     * @param okKey
     *            string that should be a valid extension key
     * @param ignoreString
     *            *not used
     */
    @Test(dataProvider = "normalKeyStrins")
    public void testValidExtensionKey( final String okKey, final String ignoreString ) {
        Assert.assertTrue( StringUtils.isValidExtensionKey( okKey ) );
    }
}