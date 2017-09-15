package com.pikamachu.services.common.clients;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The type Basic rest client.
 */
//@ApplicationScoped
public class BasicRestClient {

	/**
	 * Gets request.
	 *
	 * @param endpoint the endpoint
	 * @return the request
	 * @throws Exception the exception
	 */
	public String getRequest(String endpoint) throws Exception {

		String value = null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(endpoint);
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally clause.
		// Please note that if response content is not fully consumed the underlying
		// connection cannot be safely re-used and will be shut down and discarded
		// by the connection manager. 
		try {
		    System.out.println(response1.getStatusLine());
		    HttpEntity entity = response1.getEntity();
		    value = IOUtils.toString(entity.getContent(), "UTF-8");
		} finally {
		    response1.close();
		}

		return value;

	}

	/**
	 * Post request.
	 *
	 * @param data the data
	 * @param endpoint the endpoint
	 * @throws Exception the exception
	 */
	public void postRequest(Object data, String endpoint) throws Exception {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(endpoint);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(data);

		StringEntity input = new StringEntity(jsonInString);
		input.setContentType("application/json");

		httpPost.setEntity(input);
		CloseableHttpResponse response = httpclient.execute(httpPost);

		try {
		    
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
		    
		    HttpEntity entity = response.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity);
		} finally {
		    response.close();
		}

	}

}