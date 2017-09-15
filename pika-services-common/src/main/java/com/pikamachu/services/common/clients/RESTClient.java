package com.pikamachu.services.common.clients;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * The type REST client.
 * @param <T>   the type parameter
 */
//@ApplicationScoped
public class RESTClient<T> extends BasicRestClient {

	/**
	 * Gets request.
	 *
	 * @param endpoint the endpoint
	 * @param entityClass the entity class
	 * @return the request
	 * @throws Exception the exception
	 */
	protected T getRequest(String endpoint, Class<T> entityClass) throws Exception {

		String response = super.getRequest(endpoint);

		ObjectMapper mapper = new ObjectMapper();
		T value = mapper.readValue(response, entityClass);

		return value;

	}

}