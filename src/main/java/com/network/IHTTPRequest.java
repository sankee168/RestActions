package main.java.com.network;

import java.util.Map;

/**
 * Interface for HTTP request object.
 */
public interface IHTTPRequest {

    /**
     * @return Base URL of the request.
     */
    String getBaseURL();

    /**
     * @return HTTP method of the request.
     */
    String getMethod();

    /**
     * @return HTTP protocol of the request.
     */
    String getProtocol();

    /**
     * @return Map of all request headers.
     */
    Map<String, String> getAllHeaders();

    /**
     * @param key HTTP header key
     * @return HTTP header value of the specified key.
     */
    String getHeaderByKey(String key);

    /**
     * @return Map of all request parameters.
     */
    Map<String, String> getAllParameters();

    /**
     * @param key Request parameter key.
     * @return Parameter value corresponding to the key.
     */
    String getRequestParameterByKey(String key);

}
