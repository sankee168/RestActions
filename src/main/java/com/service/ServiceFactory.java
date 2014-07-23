package main.java.com.service;


import main.java.com.service.impl.ServiceConnector;

/**
 * Maintains map of core and implementing classes.
 */
public class ServiceFactory {

    /**
     * @return Prediction Console connector service.
     */
    public static IServiceConnector getServiceConnector() {
        return ServiceConnector.getInstance();
    }


}
