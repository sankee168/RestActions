package main.java.com.network;


public interface IHTTPCallActions {

    /**
     * @param url         URL on which the GET request will be made, without the parameters.
     * @param parameters  String parameters to be passed on in the request.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    IHTTPResponse makeGetCall(String url, String parameters, String cookieValue);

    /**
     * @param url        URL on which the GET request will be made, without the parameters.
     * @param parameters String parameters to be passed on in the request.
     * @return HTTP response object.
     */
    IHTTPResponse makeGetCall(String url, String parameters);

    /**
     * @param url URL on which the GET request will be made, without the parameters.
     * @return HTTP response object.
     */
    IHTTPResponse makeGetCall(String url);

    /**
     * @param url         URL on which the POST request will be made, without the parameters.
     * @param parameters  String parameters to be passed on in the request.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    IHTTPResponse makePostCall(String url, String parameters, String cookieValue);

    /**
     * @param url        URL on which the POST request will be made, without the parameters.
     * @param parameters String parameters to be passed on in the request.
     * @return HTTP response object.
     */
    IHTTPResponse makePostCall(String url, String parameters);

    /**
     * @param url         URL on which the PUT request will be made, without the parameters.
     * @param parameters  String parameters to be passed on in the request.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    IHTTPResponse makePutCall(String url, String parameters, String cookieValue);

    /**
     * @param url        URL on which the PUT request will be made, without the parameters.
     * @param parameters String parameters to be passed on in the request.
     * @return HTTP response object.
     */
    IHTTPResponse makePutCall(String url, String parameters);

    /**
     * @param url         URL on which the DELETE request will be made, without the parameters.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    IHTTPResponse makeDeleteCall(String url, String cookieValue);

    /**
     * @param url        URL on which the DELETE request will be made, without the parameters.
     * @return HTTP response object.
     */
    IHTTPResponse makeDeleteCall(String url);

}
