package main.java.com.service.impl;

import main.java.com.network.HTTPFactory;
import main.java.com.network.IHTTPCallActions;
import main.java.com.network.IHTTPResponse;
import main.java.com.references.AutomationReferences;
import main.java.com.references.ConfigReferences;
import main.java.com.service.IServiceConnector;
import main.java.com.utilities.Logger;
import main.java.com.utilities.PropertyFileUtility;
import org.testng.Assert;

/**
 * This class can be used to query Prediction Console in terms
 * of REST calls.
 */
public class ServiceConnector implements IServiceConnector {

    private static Logger LOGGER = Logger.getRootLogger();

    //Cookie values
    private String AMAuthCookie;
    private String amlbCookie;
    private String openamJSessionIDCookie;
    private String iPlanetDirectoryProCookie;
    private String adminconsoleJSessionIDCookie;

    /**
     * URL of CWP login page.
     */
    private String cwpLoginUrl;

    /**
     * URL of PC home page.
     */
    private String pcHomeUrl;

    /**
     * HTTP call actions utility instance.
     */
    private IHTTPCallActions http;

    /**
     * Prediction console connector constructor.
     */
    private ServiceConnector() {
        PropertyFileUtility config = new PropertyFileUtility(ConfigReferences.PREDICTION_CONSOLE_CONFIG_FILE);

        this.cwpLoginUrl = config.getStringValue(ConfigReferences.PROPERTY_CWP_URL);
        if (this.cwpLoginUrl == null)
            throw new IllegalStateException("CWP login URL is not defined in "
                    + ConfigReferences.PREDICTION_CONSOLE_CONFIG_FILE);

        this.pcHomeUrl = config.getStringValue(ConfigReferences.PROPERTY_PC_URL);
        if (this.pcHomeUrl == null)
            throw new IllegalStateException("PC home page URL is not defined in "
                    + ConfigReferences.PREDICTION_CONSOLE_CONFIG_FILE);

        this.http = HTTPFactory.getHTTPCallActionsInstance();
        //FIXME: login function
        //login();
    }

    /**
     * @return Instance of Prediction Console connector.
     */
    public static IServiceConnector getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * @param path       Relative path to make the request.
     * @param parameters Request parameters if any, else pass null.
     * @return String raw response.
     */
    @Override
    public String makeGetCall(String path, String parameters) {
        String cookieValue = "amlbCookie=" + this.amlbCookie + "; " +
                "iPlanetDirectoryPro=" + this.iPlanetDirectoryProCookie + "; " +
                "JSESSIONID=" + this.adminconsoleJSessionIDCookie;
        IHTTPResponse get = this.http.makeGetCall(this.pcHomeUrl + path, parameters, cookieValue);
        return get.getRawResponse();
    }

    /**
     * @param path       Relative path to make the request.
     * @param parameters Request parameters if any, else pass null.
     * @return String raw response.
     */
    @Override
    public String makePostCall(String path, String parameters) {
        String cookieValue = "amlbCookie=" + this.amlbCookie + "; " +
                "iPlanetDirectoryPro=" + this.iPlanetDirectoryProCookie + "; " +
                "JSESSIONID=" + this.adminconsoleJSessionIDCookie;
        IHTTPResponse post = this.http.makePostCall(this.pcHomeUrl + path, parameters, cookieValue);
        return post.getRawResponse();
    }

    /**
     * @param path       Relative path to make the request.
     * @param parameters Request parameters if any, else pass null.
     * @return String raw response.
     */
    @Override
    public String makePutCall(String path, String parameters) {
        String cookieValue = "amlbCookie=" + this.amlbCookie + "; " +
                "iPlanetDirectoryPro=" + this.iPlanetDirectoryProCookie + "; " +
                "JSESSIONID=" + this.adminconsoleJSessionIDCookie;
        IHTTPResponse put = this.http.makePutCall(this.pcHomeUrl + path, parameters, cookieValue);
        return put.getRawResponse();
    }

    /**
     * @param path Relative path to make the request.
     * @return String raw response.
     */
    @Override
    public String makeDeleteCall(String path) {
        String cookieValue = "amlbCookie=" + this.amlbCookie + "; " +
                "iPlanetDirectoryPro=" + this.iPlanetDirectoryProCookie + "; " +
                "JSESSIONID=" + this.adminconsoleJSessionIDCookie;
        IHTTPResponse delete = this.http.makeDeleteCall(this.pcHomeUrl + path, cookieValue);
        return delete.getRawResponse();
    }

    /**
     * Uses the defined user/password to login to prediction console and obtain cookie information.
     */
    private void login() {
        //GET CWP
        IHTTPResponse cwpGet = this.http.makeGetCall(this.cwpLoginUrl);

        Assert.assertEquals(cwpGet.getStatusCode(), 200);
        String[] cookies1 = cwpGet.getResponseHeaderByKey("Set-Cookie").split('[' + AutomationReferences.COOKIE_SEPARATOR + ']');
        int size = cookies1.length;
        Assert.assertEquals(size, 3);

        for (int i = 0; i < size; i++) {
            String curr = cookies1[i];
            if (curr.indexOf("AMAuthCookie") != -1)
                this.AMAuthCookie = curr.split("=")[1].split(";")[0];
            else if (curr.indexOf("amlbcookie") != -1)
                this.amlbCookie = curr.split("=")[1].split(";")[0];
            else if (curr.indexOf("JSESSIONID") != -1)
                this.openamJSessionIDCookie = curr.split("=")[1].split(";")[0];
            else
                System.out.println("Unexpected cookie: " + curr);
        }

        //POST CWP
        String parameters = "IDToken1=pxoe-mailer&IDToken2=spirit12345$&IDButton=Log+In&goto=&gotoOnFail=&SunQueryParamsString=&encoded=false&gx_charset=UTF-8";
        String cookieValue = "JSESSIONID=" + this.openamJSessionIDCookie + "; " +
                "amlbCookie=" + this.amlbCookie + "; " +
                "AMAuthCookie=" + this.AMAuthCookie;
        IHTTPResponse cwpPost = this.http.makePostCall(this.cwpLoginUrl, parameters, cookieValue);

        Assert.assertEquals(cwpPost.getStatusCode(), 302);
        String[] cookies2 = cwpPost.getResponseHeaderByKey("Set-Cookie").split('[' + AutomationReferences.COOKIE_SEPARATOR + ']');
        size = cookies2.length;
        Assert.assertEquals(size, 2);

        for (int i = 0; i < size; i++) {
            String curr = cookies2[i];
            if (curr.indexOf("iPlanetDirectoryPro") != -1)
                this.iPlanetDirectoryProCookie = curr.split("=")[1].split(";")[0];
            else if (curr.indexOf("AMAuthCookie") != -1)
                this.AMAuthCookie = curr.split("=")[1].split(";")[0];
        }

        //GET PC HOME
        cookieValue = "amlbCookie=" + this.amlbCookie + "; " +
                "iPlanetDirectoryPro=" + this.iPlanetDirectoryProCookie;
        IHTTPResponse pcGet = this.http.makeGetCall(this.pcHomeUrl, null, cookieValue);

        Assert.assertEquals(pcGet.getStatusCode(), 200);
        String[] cookies3 = pcGet.getResponseHeaderByKey("Set-Cookie").split('[' + AutomationReferences.COOKIE_SEPARATOR + ']');
        size = cookies3.length;
        Assert.assertEquals(size, 1);

        for (int i = 0; i < size; i++) {
            String curr = cookies3[i];
            if (curr.indexOf("JSESSIONID") != -1)
                this.adminconsoleJSessionIDCookie = curr.split("=")[1].split(";")[0];
        }

        LOGGER.info("Login successful.");
    }

    /**
     * Holder class for Prediction Console connector which will contain the
     * instance in a thread safe manner.
     */
    private final static class Holder {
        private final static IServiceConnector INSTANCE = new ServiceConnector();
    }

}
