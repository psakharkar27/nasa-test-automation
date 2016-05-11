package com.nasa.rest.common;

/**
 * Contains the groups to be used in test groups as constants.
 * Created by prsakharkar on 5/9/16.
 */

public class TestGroups {

    //Mark your tests as Dev while the test is in development and to test them during tests development.
    public static final String DEV = "Dev";

    // Mark the tests that needs to be run as a part of regression suite.
    public static final String REGRESSION = "Regression";

    //Mark the tests that needs to be run as monitors.
    public static final String MONITOR = "Monitor";

    // Mark the tests that needs to be run as Feature suite.
    public static final String FEATURE = "Feature";

    //Mark the tests that needs to are large and takes more than 2 minutes to excecute.
    public static final String LARGE_TEST = "LargeTest";

    //Mark tests that are only for BETA with this group.
    public static final String BETA = "BetaTest";
    
  //Mark tests that are only for BROKEN with this group.
    public static final String BROKEN = "Broken";
}
