package com.nasa.test.sound;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import javax.ws.rs.core.Response;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.rest.response.model.Sounds;
import com.nasa.rest.services.SoundServices;

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
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

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
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

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
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		String value = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Sounds result = mapper.readValue(value, Sounds.class);

		assertTrue(result.getCount().intValue() <= 10, "Count mismatch");

	}

	@Test(description = "Test for non interger limit value, User should get default count", groups = { "Regression",
			"Dev" })
	public void testNonIntegerLimitVlaue() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));
		queryParameters.put("limit", "@#$");

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		String value = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Sounds result = mapper.readValue(value, Sounds.class);

		assertTrue(result.getCount().intValue() <= 10, "Count mismatch");

	}

	@Test(description = "Test For missing api key", groups = { "BetaTest", "Regression" })
	public void testApiKeyMissing() throws Exception {

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
	}

	@Test(description = "Test For invalid api key", groups = { "BetaTest", "Regression" })
	public void testInvalidApiKey() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.invalidKey"));
		response = getSoundServices(true, queryParameters);

		assertEquals(response.getStatus(), Response.Status.FORBIDDEN.getStatusCode());
	}

	@Test(description = "Test For demo api key and rate limit, User should get reults with demo key", groups = {
			"Broken", "Regression" })
	public void testDemoApiKey() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiDemoKey"));
		response = getSoundServices(true, queryParameters);

		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
		assertTrue(Integer.parseInt(response.getHeaderString("X-RateLimit-Limit")) == 40,
				"X-RateLimit-Limit is same for demo Key");

	}

	@Test(description = "Test For http request, USer should get bad request reponse.", groups = { "Feature",
			"Regression" })
	public void testHttpApiRequest() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));
		response = getSoundServices(false, queryParameters);

		assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test(description = "Test filter parameters", groups = { "Regression", "Broken1" })
	public void testSearchFilter() throws Exception {

		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));
		queryParameters.put("limit", "10");
		queryParameters.put("q", "space");

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		assertTrue(validateSearch(response, "space"), "Matching results are not found in response");
	}

	@Test(description = "Test for parameter sequence, User should get same result.", groups = { "Regression",
			"Broken1" })
	public void testParameterSequence() throws Exception {

		queryParameters.put("limit", "10");
		queryParameters.put("q", "space");
		queryParameters.put("api_key", getPropValues().getProperty("sound.apiKey"));

		response = getSoundServices(true, queryParameters);
		assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

		assertTrue(validateSearch(response, "space"), "Matching results are not found in response");
	}
}