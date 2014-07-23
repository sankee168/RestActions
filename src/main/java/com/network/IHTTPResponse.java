package main.java.com.network;

import java.util.Map;

/**
 * Interface for HTTP response object.
 */
public interface IHTTPResponse {

    /**
     * @return HTTP status code of the HTTP response.
     */
    int getStatusCode();

    /**
     * @return Map of all the HTTP response headers.
     */
    Map<String, String> getAllResponseHeaders();

    /**
     * @param key HTTP response header key.
     * @return HTTP response header value of the specified key.
     */
    String getResponseHeaderByKey(String key);

    /**
     * @return URL to which the request was made.
     */
    String getBaseURL();

    /**
     * @return Map of all request parameters.
     */
    Map<String, String> getAllRequestParameters();

    /**
     * @param key Request parameter key.
     * @return Parameter value corresponding to the key.
     */
    String getRequestParameterByKey(String key);

    /**
     * @return HTTP method of the call - GET, PUT, POST etc.
     */
    String getMethod();

    /**
     * @return HTTP protocol over which the request was made.
     * HTTP or HTTPS.
     */
    String getProtocol();

    /**
     * @return Map of all the request HTTP headers.
     */
    Map<String, String> getAllRequestHeaders();

    /**
     * @param key HTTP request header key.
     * @return HTTP request header value of the specified key.
     */
    String getRequestHeaderByKey(String key);

    /**
     * @return Raw response text of the network call.
     */
    String getRawResponse();

}
