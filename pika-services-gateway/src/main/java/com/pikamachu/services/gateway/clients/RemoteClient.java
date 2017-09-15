package com.pikamachu.services.gateway.clients;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.pikamachu.services.common.annotations.Config;
import com.pikamachu.services.common.clients.BasicRestClient;
import com.pikamachu.services.common.exceptions.BadRequestException;
import com.pikamachu.services.common.exceptions.InternalServerErrorException;

/**
 * The type remote tiers client.
 */
@Singleton
public class RemoteClient extends BasicRestClient {

	/** The log. */
	@Inject
	private Logger log;

	/**
	 * The Tibco tier service endpoint.
	 */
	@Config
	@Inject
	private String remoteServiceEndpoint = "http://localhost:8080";


	/**
	 * Gets tiers.
	 *
	 * @return the tiers
	 * @throws Exception the exception
	 */
	public String getRemoteService() throws Exception {
		return super.getRequest(remoteServiceEndpoint);
	}

	public String postRemoteService(String json) throws Exception {
		return postStringRequest(json, remoteServiceEndpoint);
	}

	public String postStringRequest(String data, String endpoint) throws Exception {

		HttpPost httpPost = new HttpPost(endpoint);
		
		// Set headers
		httpPost.addHeader("X-PIKA", "pika");
		
		// Set content
		StringEntity input = new StringEntity(data);
		input.setContentType("application/json");
		httpPost.setEntity(input);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpPost);
		
		String responseEntity = null;
		try {
			HttpEntity entity = response.getEntity();
			responseEntity = EntityUtils.toString(entity);

			String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
			if (statusCode.startsWith("4")) {
				throw new BadRequestException(responseEntity);
			} else if (statusCode.startsWith("5")) {
				throw new InternalServerErrorException(responseEntity);
			}
		} finally {
			response.close();
		}

		return responseEntity;

	}


}