package com.nasa.test.sound;

import javax.ws.rs.client.WebTarget;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.rest.common.BaseTest;
import com.nasa.rest.response.model.Results;
import com.nasa.rest.response.model.Sounds;
import com.nasa.rest.services.SoundServices;

import javax.ws.rs.core.Response;

/**
 * Created by prsakharkar on 5/9/16.
 */
public class TestSoundServices extends SoundServices {

	HashMap<String, String> queryParameters = new HashMap<String, String>();

	@Test(description = "Test for defualt limit it should be less than or equal to given count.", groups = {
			"Regression", "BetaTest" })
	public void testDefaultLimit() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());

		String value = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Sounds result = mapper.readValue(value, Sounds.class);

		assertTrue(result.getCount().intValue() <= 10, "Count mismatch");

	}

	@Test(description = "Test for checking total limit it should be less than or equal to given limit.", groups = {
			"Regression", "BetaTest" })
	public void testLimit() throws Exception {
		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));
		queryParameters.put("limit", "10");

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());

		String value = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Sounds result = mapper.readValue(value, Sounds.class);

		assertTrue(result.getCount().intValue() <= 10, "Count mismatch");

	}

	@Test(description = "Test for negative limit value, Should return default count", groups = { "Regression",
			"BetaTest" })
	public void testNegativeLimit() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));
		queryParameters.put("limit", "-1");

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());

		String value = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Sounds result = mapper.readValue(value, Sounds.class);

		assertTrue(result.getCount().intValue() <= 10, "Count mismatch");

	}

	@Test(description = "Test for non interger limit value, User should get default count", groups = { "Regression", "Dev" })
	public void testNonIntegerLimitVlaue() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));
		queryParameters.put("limit", "@#$");

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(),Response.Status.OK.getStatusCode());

		String value = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Sounds result = mapper.readValue(value, Sounds.class);

		assertTrue(result.getCount().intValue() <= 10, "Count mismatch");

	}

	@Test(description = "Test For missing api key", groups = { "BetaTest", "Regression" })
	public void testApiKeyMissing() throws Exception {

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(),Response.Status.FORBIDDEN.getStatusCode());
	}

	@Test(description = "Test For invalid api key", groups = { "BetaTest", "Regression" })
	public void testInvalidApiKey() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.invalidKey"));
		response = getSoundServices(true, queryParameters);

		assertEquals(response.getStatus(),Response.Status.FORBIDDEN.getStatusCode());
	}

	@Test(description = "Test For http request, USer should get bad request reponse.", groups = { "Feature", "Regression" })
	public void testHttpApiRequest() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));
		response = getSoundServices(false, queryParameters);

		assertEquals(response.getStatus(),Response.Status.BAD_REQUEST.getStatusCode());
	}
}