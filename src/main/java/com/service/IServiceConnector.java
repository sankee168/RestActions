package main.java.com.service;

/**
 * Interface for making calls to Prediction Console and retrieving
 * REST responses.
 */
public interface IServiceConnector {

    /**
     * @param path       Relative path to make the request.
     * @param parameters Request parameters if any, else pass null.
     * @return String raw response.
     */
    String makeGetCall(String path, String parameters);

    /**
     * @param path       Relative path to make the request.
     * @param parameters Request parameters if any, else pass null.
     * @return String raw response.
     */
    String makePostCall(String path, String parameters);

    /**
     * @param path       Relative path to make the request.
     * @param parameters Request parameters if any, else pass null.
     * @return String raw response.
     */
    String makePutCall(String path, String parameters);

    /**
     * @param path Relative pathto make the request.
     * @return String raw response.
     */
    String makeDeleteCall(String path);

}
