package main.java.com.network;

import java.util.List;
import java.util.Map;


/**
 * Interface for network proxy that can be integrated with Selenium web driver.
 * The constructor will be responsible for initializing and starting the proxy server.
 */
public interface INetworkProxy {

    /**
     * Starts the proxy server.
     */
    void start();

    /**
     * Stops the proxy server.
     */
    void stop();

    /**
     * Notifies the proxy that webdriver will be navigating to a new page.
     */
    void newPage();

    /**
     * @return Map of all intercepted network traffic.
     * Where, the key will denote the page number.
     */
    Map<String, List<IHTTPResponse>> getAllTraffic();

    /**
     * @return Host name of the machine where the proxy is running.
     */
    String getProxyHost();

    /**
     * @return Port on which the proxy server is running.
     */
    int getProxyPort();

    /**
     * @return Key representing the current page in the map of traffic.
     */
    String getCurrentPageKey();

    /**
     * @return Sequential list of all network calls intercepted in the
     * current page.
     */
    List<IHTTPResponse> getCurrentPageTraffic();

}
