package main.java.com.network.impl;

import main.java.com.network.HTTPFactory;
import main.java.com.network.IHTTPCallActions;
import main.java.com.network.IHTTPResponse;
import main.java.com.utilities.Logger;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Utility to make HTTP calls.
 * The following methods are supported: GET, PUT, POST, DELETE.
 * The calls also handle certificate errors.
 */
public class HTTPCallActions implements IHTTPCallActions {

    /**
     * Root logger instance.
     */
    private static Logger LOGGER = Logger.getRootLogger();

    public static IHTTPCallActions getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * @param url         URL on which the GET request will be made, without the parameters.
     * @param parameters  String parameters to be passed on in the request.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makeGetCall(String url, String parameters, String cookieValue) {
        return core.makeGetCall(url, parameters, cookieValue);
    }

    /**
     * @param url        URL on which the GET request will be made, without the parameters.
     * @param parameters String parameters to be passed on in the request.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makeGetCall(String url, String parameters) {
        return core.makeGetCall(url, parameters, null);
    }

    /**
     * @param url URL on which the GET request will be made, without the parameters.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makeGetCall(String url) {
        return core.makeGetCall(url, null, null);
    }

    /**
     * @param url         URL on which the POST request will be made, without the parameters.
     * @param parameters  String parameters to be passed on in the request.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makePostCall(String url, String parameters, String cookieValue) {
        return core.makePostCall(url, parameters, cookieValue);
    }

    /**
     * @param url        URL on which the POST request will be made, without the parameters.
     * @param parameters String parameters to be passed on in the request.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makePostCall(String url, String parameters) {
        return core.makePostCall(url, parameters, null);
    }

    /**
     * @param url         URL on which the PUT request will be made, without the parameters.
     * @param parameters  String parameters to be passed on in the request.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makePutCall(String url, String parameters, String cookieValue) {
        return core.makePutCall(url, parameters, cookieValue);
    }

    /**
     * @param url        URL on which the PUT request will be made, without the parameters.
     * @param parameters String parameters to be passed on in the request.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makePutCall(String url, String parameters) {
        return core.makePutCall(url, parameters, null);
    }

    /**
     * @param url         URL on which the DELETE request will be made, without the parameters.
     * @param cookieValue Cookie data in a string format. This format is
     *                    similar to that of request parameters.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makeDeleteCall(String url, String cookieValue) {
        return core.makeDeleteCall(url, cookieValue);
    }

    /**
     * @param url        URL on which the DELETE request will be made, without the parameters.
     * @return HTTP response object.
     */
    @Override
    public IHTTPResponse makeDeleteCall(String url) {
        return core.makeDeleteCall(url, null);
    }

    /**
     * Holder class to enforce singleton pattern in a thread safe manner.
     */
    private final static class Holder {
        private static final IHTTPCallActions INSTANCE = new HTTPCallActions();
    }

    private static class core {

        /**
         * @param getUrl      URL on which the GET request will be made, without the parameters.
         * @param parameters  String parameters to be passed on in the request.
         * @param cookieValue Cookie data in a string format. This format is
         *                    similar to that of request parameters.
         * @return HTTP response object.
         */
        private static IHTTPResponse makeGetCall(String getUrl, String parameters, String cookieValue) {
            handleCertificateErrors();

            if (parameters != null)
                getUrl = getUrl + "?" + parameters;

            LOGGER.info("GET call: " + getUrl);

            URL url = null;
            try {
                url = new URL(getUrl);
            } catch (MalformedURLException e) {
                LOGGER.error("Improper URL", e);
                System.out.println("ERROR: Improper URL: " + getUrl);
                System.exit(1);
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                LOGGER.error("Unable to connect to host.", e);
                System.out.println("ERROR: Unable to connect to host.");
                System.exit(1);
            }

            if (cookieValue != null) {
                if (cookieValue.length() != 0)
                    connection.setRequestProperty("Cookie", cookieValue);
            }

            IHTTPResponse response = HTTPFactory.getHTTPResponse(connection);
            LOGGER.info("Raw response: " + response.getRawResponse());
            return response;
        }

        /**
         * @param postUrl     URL to which the POST call will be made.
         * @param parameters  String parameters to be passed on in the request.
         * @param cookieValue Cookie data in a string format. This format is
         *                    similar to that of request parameters.
         * @return HTTP response object.
         */
        private static IHTTPResponse makePostCall(String postUrl, String parameters, String cookieValue) {
            handleCertificateErrors();

            LOGGER.info("POST call: " + postUrl + "\nParams: " + parameters);

            URL url = null;
            try {
                url = new URL(postUrl);
            } catch (MalformedURLException e) {
                LOGGER.error("Improper URL", e);
                System.out.println("ERROR: Improper URL: " + postUrl);
                System.exit(1);
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                LOGGER.error("Unable to connect to host.", e);
                System.out.println("ERROR: Unable to connect to host.");
                System.exit(1);
            }

            if (cookieValue != null) {
                if (cookieValue.length() != 0)
                    connection.setRequestProperty("Cookie", cookieValue);
            }

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            try {
                connection.setRequestMethod("POST");
            } catch (ProtocolException e) {
                LOGGER.error("Protocol Exception", e);
                System.out.println("ERROR: Protocol excetion.");
                System.exit(1);
            }
            //connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
            connection.setUseCaches(false);

            try {
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(parameters);
                wr.flush();
                wr.close();
            } catch (IOException e) {
                LOGGER.error("Unable to write request parameter.", e);
                System.out.println("ERROR: Unable to write request parameters.");
                System.exit(1);
            }

            return HTTPFactory.getHTTPResponse(connection);
        }

        /**
         * @param putUrl      URL to which the PUT call will be made.
         * @param parameters  String parameters to be passed on in the request.
         * @param cookieValue Cookie data in a string format. This format is
         *                    similar to that of request parameters.
         * @return HTTP response object.
         */
        private static IHTTPResponse makePutCall(String putUrl, String parameters, String cookieValue) {
            handleCertificateErrors();

            LOGGER.info("PUT call: " + putUrl + "\nParams: " + parameters);
            URL url = null;
            try {
                url = new URL(putUrl);
            } catch (MalformedURLException e) {
                LOGGER.error("Improper URL", e);
                System.out.println("ERROR: Improper URL: " + putUrl);
                System.exit(1);
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                LOGGER.error("Unable to connect to host.", e);
                System.out.println("ERROR: Unable to connect to host.");
                System.exit(1);
            }

            if (cookieValue != null) {
                if (cookieValue.length() != 0)
                    connection.setRequestProperty("Cookie", cookieValue);
            }

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            try {
                connection.setRequestMethod("PUT");
            } catch (ProtocolException e) {
                LOGGER.error("Protocol Exception", e);
                System.out.println("ERROR: Protocol excetion.");
                System.exit(1);
            }

            OutputStreamWriter out;
            try {
                out = new OutputStreamWriter(connection.getOutputStream());
                out.write(parameters);
                out.close();
            } catch (IOException e) {
                LOGGER.error("Unable to write request parameter.", e);
                System.out.println("ERROR: Unable to write request parameters.");
                System.exit(1);
            }

            return HTTPFactory.getHTTPResponse(connection);
        }

        /**
         * @param deleteUrl   URL to which the delete call will be made.
         * @param cookieValue Cookie data in a string format. This format is
         *                    similar to that of request parameters.
         * @return String HTTP response.
         */
        private static IHTTPResponse makeDeleteCall(String deleteUrl, String cookieValue) {
            handleCertificateErrors();

            LOGGER.info("DELETE call: " + deleteUrl);
            URL url = null;
            try {
                url = new URL(deleteUrl);
            } catch (MalformedURLException e) {
                LOGGER.error("Improper URL", e);
                System.out.println("ERROR: Improper URL: " + deleteUrl);
                System.exit(1);
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                LOGGER.error("Unable to connect to host.", e);
                System.out.println("ERROR: Unable to connect to host.");
                System.exit(1);
            }

            if (cookieValue != null) {
                if (cookieValue.length() != 0)
                    connection.setRequestProperty("Cookie", cookieValue);
            }

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            try {
                connection.setRequestMethod("DELETE");
            } catch (ProtocolException e) {
                LOGGER.error("Protocol Exception", e);
                System.out.println("ERROR: Protocol excetion.");
                System.exit(1);
            }
            try {
                connection.connect();
            } catch (IOException e) {
                LOGGER.error("Unable to connect to host.", e);
                System.out.println("ERROR: Unable to connect to host.");
                System.exit(1);
            }

            return HTTPFactory.getHTTPResponse(connection);
        }

        /**
         * This method is used to handle certificate errors.
         */
        private static void handleCertificateErrors() {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = null;
            try {
                sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                System.out.println("ERROR: Unable to handle certifcate errors.");
                LOGGER.error("Unable to handle certifcate errors.", e);
                System.exit(1);
            }

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }

    }

}
