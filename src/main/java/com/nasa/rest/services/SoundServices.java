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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.rest.common.BaseTest;
import com.nasa.rest.response.model.Results;
import com.nasa.rest.response.model.Sounds;

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

	/**
	 * This method makes rest call and returns the response.
	 * 
	 * @param boolean
	 *            isSecured is http or https
	 * @param HashMap<String,
	 *            String> queryParameters
	 * @return request Response
	 * @throws IOException
	 *             URISyntaxException
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

	//TODO need modification
	public boolean validateSearch(Response response, String search) throws Exception {

		boolean isFilter = false;

		String value = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		Sounds result = mapper.readValue(value, Sounds.class);

		for (Results rs : result.getResults()) {
			try {
				if (rs.getDescription().toLowerCase().contains(search.toLowerCase())
						|| rs.getTitle().toLowerCase().contains(search.toLowerCase())
						|| rs.getTagList().toLowerCase().contains(search.toLowerCase())) {
					System.out.println("Match Found");
					isFilter = true;
				} else {
					isFilter = false;
					break;
				}
			} catch (NullPointerException e) {
				if (rs.getTitle().toLowerCase().contains(search.toLowerCase())
						|| rs.getTagList().toLowerCase().contains(search.toLowerCase())) {
					System.out.println("Match Found After Null");
					isFilter = true;
				} else {
					isFilter = false;
					break;
				}
			}
		}
		return isFilter;
	}
}
