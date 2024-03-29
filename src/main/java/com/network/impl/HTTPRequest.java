package main.java.com.network.impl;

import main.java.com.network.IHTTPRequest;
import main.java.com.references.AutomationReferences;
import main.java.com.utilities.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Represents a network request.
  */
public class HTTPRequest implements IHTTPRequest {

    /**
     * Encoding format to be used to decode the intercepted
     * network call URLs.
     */
    private final static String ENCODING_FORMAT = "UTF-8";
    /**
     * Root logger instance.
     */
    private static Logger LOGGER = Logger.getRootLogger();
    /**
     * Base URL to which the request was made.
     * The base URL will not caontain any request parameters.
     */
    private String baseUrl;

    /**
     * HTTP method of the call - GET, PUT, POST etc.
     */
    private String method;

    /**
     * HTTP protocol of the network call - HTTP or HTTPS.
     */
    private String protocol;

    /**
     * Map of request headers.
     */
    private Map<String, String> headers;

    /**
     * Map of request parameters.
     */
    private Map<String, String> parameters;

    /**
     * Whether the base URL has been formatted or not.
     */
    private boolean updateCompleted = false;

    /**
     * HTTP request constructor.
     *
     * @param url     URL of the request.
     * @param method  HTTP method of the request.
     * @param headers Map of request headers.
     */
    public HTTPRequest(String url, String method, Map<String, String> headers, Map<String, String> postData) {
        if (method.equals(AutomationReferences.HTTP_POST_METHOD)) {
            this.parameters = postData;
            this.baseUrl = url;
        } else
            formatURL(url);

        this.method = method;
        if (this.baseUrl.startsWith("https:"))
            this.protocol = "https";
        else
            this.protocol = "http";
        this.headers = headers;
    }

    /**
     * Splits the input URL into path and request parameters.
     *
     * @param url Full URL of the network call.
     */
    private void formatURL(String url) {
        String[] decoded = null;

        try {
            decoded = URLDecoder.decode(url, ENCODING_FORMAT).split("[?]");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unsopported encoding: " + ENCODING_FORMAT, e);
            System.out.println("ERROR: Unsopported encoding: " + ENCODING_FORMAT);
            System.exit(1);
        }

        if (decoded.length > 2) {
            System.out.println(new HashSet<>(Arrays.asList(decoded)));
            throw new Error("Logic error.");
        }

        this.baseUrl = decoded[0];
        this.updateCompleted = true;

        //Only if the URL actually contains request parameters.
        if (decoded.length == 2) {
            String[] pairs = decoded[1].split("[&]");
            int size = pairs.length;
            this.parameters = new HashMap<>();
            for (int i = 0; i < size; i++) {
                if (pairs[i].indexOf("=") == -1)
                    throw new Error("Logic error: Parameter does not contain '='\n" + url);

                String[] keyVals = pairs[i].split("[=]");

                if (keyVals.length > 2) {
                    LOGGER.warn("Logic error: Multiple '=' found in a single argument pair.\n" + url);
                    LOGGER.warn(new HashSet<>(Arrays.asList(keyVals)).toString());
                    continue;
                }

                if (keyVals.length == 1)
                    this.parameters.put(keyVals[0], "");
                else
                    this.parameters.put(keyVals[0], keyVals[1]);
            }
        } else
            this.parameters = new HashMap<>();
    }

    /**
     * @return Base URL of the request.
     */
    public String getBaseURL() {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        return this.baseUrl;
    }

    /**
     * @return HTTP method of the request.
     */
    public String getMethod() {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        return this.method;
    }

    /**
     * @return HTTP protocol of the request.
     */
    public String getProtocol() {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        return this.protocol;
    }

    /**
     * @return Map of all request headers.
     */
    public Map<String, String> getAllHeaders() {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        return this.headers;
    }

    /**
     * @param key HTTP header key
     * @return HTTP header value of the specified key.
     */
    public String getHeaderByKey(String key) {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        if (this.headers.containsKey(key))
            return this.headers.get(key);
        return null;
    }

    /**
     * @return Map of all request parameters.
     */
    public Map<String, String> getAllParameters() {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        return this.parameters;
    }

    /**
     * @param key Request parameter key.
     * @return Parameter value corresponding to the key.
     */
    public String getRequestParameterByKey(String key) {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        if (this.parameters.containsKey(key))
            return this.parameters.get(key);
        return null;
    }

    @Override
    public String toString() {
        if (!this.updateCompleted)
            formatURL(this.baseUrl);
        return this.method + " " + this.protocol + " " + this.baseUrl
                + "\nParameters: " + this.parameters + "\n";
    }

}
