package com.nasa.rest.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

import com.nasa.rest.common.BaseTest;

public class SoundServices extends BaseTest {

	private static Logger logger = Logger.getLogger(SoundServices.class.getName());

	// TODO need modification
	public Response SoundServicesSecured(boolean isKey, String limit, String filter)
			throws IOException, URISyntaxException {

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(getPropValues().getProperty("sound.baseURL")).setPath("/planetary/sounds");

		if (isKey == true) {
			builder.setParameter("api_key", getPropValues().getProperty("sound.apiKey"));
		}
		builder.setParameter("limit", limit).setParameter("q", filter);
		String url = builder.build().toString();

		WebTarget target = client.target(url);
		response = target.request().get();

		return response;

	}

	/*
	 * USe
	 */
	public Response getSoundServices(boolean isSecured, HashMap<String, String> queryParameters)
			throws IOException, URISyntaxException {

		URIBuilder builder = new URIBuilder();
		if (isSecured == true) {
			builder.setScheme("https");
		} else {
			builder.setScheme("http");
		}
		builder.setHost(getPropValues().getProperty("sound.baseURL")).setPath("/planetary/sounds");

		Set<Map.Entry<String, String>> entrySet = queryParameters.entrySet();

		for (Map.Entry<String, String> entry : entrySet) {
			builder.setParameter(entry.getKey(), entry.getValue());
		}

		String url = builder.build().toString();
		logger.info("Checking for URL :-" + url);

		WebTarget target = client.target(url);
		response = target.request().get();

		return response;

	}
}
