package com.pikamachu.services.common.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <p>
 * This filter wraps qualified requests for JSON content with that standard JSONP padding. This makes these calls accessible cross-domain using
 * standard JSONP approaches.
 * </p>
 * <p>
 * To qualify for wrapping the request must be made to the <i>/*</i> path, and contain a query parameter call <i>jsoncallback</i> that defines the
 * JSONP callback method to use with the response.
 * </p>
 *
 * @author balunasj
 */
@WebFilter("/api/*")
public class JsonpRequestFilter implements Filter {

	// The callback method to use
	/** The Constant CALLBACK_METHOD. */
	private static final String CALLBACK_METHOD = "jsonpcallback";

	// This is a simple safe pattern check for the callback method
	/** The Constant SAFE_PRN. */
	public static final Pattern SAFE_PRN = Pattern.compile("[a-zA-Z0-9_\\.]+");

	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "application/javascript";

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		// Nothing needed
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Only HttpServletRequest requests are supported");
		}

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		// extract the callback method from the request query parameters
		String callback = getCallbackMethod(httpRequest);

		if (!isJSONPRequest(callback)) {
			// Enable cross-domain
			if (httpResponse != null) {
				// This should be added in response to both the preflight and the actual request
				httpResponse.addHeader("Access-Control-Allow-Origin", "*");
				httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
			}
			// Request is not a JSONP request move on
			chain.doFilter(request, response);
		} else {
			// Need to check if the callback method is safe
			if (!SAFE_PRN.matcher(callback).matches()) {
				throw new ServletException("JSONP Callback method '" + CALLBACK_METHOD + "' parameter not valid function");
			}

			// Will stream updated response
			final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

			// Create a custom response wrapper to adding in the padding
			HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(httpResponse) {

				@Override
				public ServletOutputStream getOutputStream() throws IOException {
					return new ServletOutputStream() {
						@Override
						public void write(int b) throws IOException {
							byteStream.write(b);
						}

						@Override
						public boolean isReady() {
							return true;
						}

						@Override
						public void setWriteListener(WriteListener arg0) {
						}
					};
				}

				@Override
				public PrintWriter getWriter() throws IOException {
					return new PrintWriter(byteStream);
				}
			};

			// Process the rest of the filter chain, including the JAX-RS request
			chain.doFilter(request, responseWrapper);

			// Override response content and encoding
			response.setContentType(CONTENT_TYPE);
			response.setCharacterEncoding("UTF-8");

			// Write the padded updates to the output stream.
			response.getOutputStream().write((callback + "(").getBytes());
			response.getOutputStream().write(byteStream.toByteArray());
			response.getOutputStream().write(");".getBytes());
		}
	}

	/**
	 * Gets the callback method.
	 *
	 * @param httpRequest             the http request
	 * @return the callback method
	 */
	private String getCallbackMethod(HttpServletRequest httpRequest) {
		return httpRequest.getParameter(CALLBACK_METHOD);
	}

	/**
	 * Checks if is jSONP request.
	 *
	 * @param callbackMethod             the callback method
	 * @return true, if is jSONP request
	 */
	private boolean isJSONPRequest(String callbackMethod) {
		// A simple check to see if the query parameter has been set.
		return (callbackMethod != null && callbackMethod.length() > 0);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// Nothing to do
	}
}