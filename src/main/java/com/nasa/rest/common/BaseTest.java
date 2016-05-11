package com.nasa.rest.common;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;


/**
 * Created by prsakharkar on 5/9/16.
 * BaseTest for all tests that includes the call to create and close the
 * rest instance.
 */
public class BaseTest {

    protected Client client;
    protected Response response;

    @BeforeClass(groups = {TestGroups.DEV,
            TestGroups.FEATURE, TestGroups.LARGE_TEST, TestGroups.MONITOR,
            TestGroups.REGRESSION, TestGroups.BETA})
    public void setup() {

        client = ClientBuilder.newClient();
    }

    @AfterMethod(groups = {TestGroups.DEV,
            TestGroups.FEATURE, TestGroups.LARGE_TEST, TestGroups.MONITOR,
            TestGroups.REGRESSION, TestGroups.BETA})
    public void closeClient() {
        // You should close connections!
        response.close();
    }
    
    @BeforeClass(groups = {TestGroups.DEV,
            TestGroups.FEATURE, TestGroups.LARGE_TEST, TestGroups.MONITOR,
            TestGroups.REGRESSION, TestGroups.BETA})
    public Properties getPropValues() throws IOException {

		String env = System.getProperty("env");
		if (env == null) {
			env = "dev";
		}
		String config = env + ".properties";
		Properties configProps = new Properties();
		InputStream inStream = null;
		try {

			inStream = getClass().getClassLoader().getResourceAsStream(config);
			if (inStream != null) {
				configProps.load(inStream);
			} else {
				throw new FileNotFoundException("property file '" + config + "' not found in the classpath");
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inStream != null)
				inStream.close();
		}

		return configProps;
	}

}
