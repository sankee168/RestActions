package main.java.com.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


/**
 * Utility for reading from a property file.
 * It assumes that all the properties file are maintained
 * inside the conf directory.
 */

public class PropertyFileUtility {

    private static Logger LOGGER = Logger.getRootLogger();

    /**
     * Configuration object populated from the specified properties file.
     */
    private Properties config;

    /**
     * Property file to be accessed.
     * Used for logging purposes.
     */
    private String fileName;

    /**
     * Property file utility constructor.
     *
     * @param fileName Property file to be read.
     */
    public PropertyFileUtility(String fileName) {
        try {
            this.fileName = fileName;
            File file = new File(this.fileName);
            FileReader reader = new FileReader(file);
            this.config = new Properties();
            this.config.load(reader);
            reader.close();
        } catch (IOException e) {
            System.out.println("ERROR: Error in reading file: " + this.fileName);
            LOGGER.error("Error in reading file: " + this.fileName, e);
            System.exit(1);
        }
    }

    /**
     * @param key   Key parameter to set in properties file.
     * @param value Value corresponding to the specified key.
     */
    public void setProperty(String key, String value) {
        config.setProperty(key, value);
        try {
            config.store(new FileWriter(new File(fileName)), "Darshana is ghot");
        } catch (IOException e) {
            LOGGER.error("Error writing into property file: " + fileName + ", key: " + key);
            System.exit(1);
        }
    }

    /**
     * @return Map of all the properties.
     */
    public Map<String, String> getAllProperties() {
        Iterator<Entry<Object, Object>> iter = this.config.entrySet().iterator();
        Map<String, String> allProperties = new HashMap<>();
        while (iter.hasNext()) {
            Entry<Object, Object> entry = iter.next();
            allProperties.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return allProperties;
    }

    /**
     * @param key Property to be returned.
     * @return If found, string value of the specified property is returned.
     * Else, null is returned.
     */
    public String getStringValue(String key) {
        if (this.config.containsKey(key))
            return this.config.getProperty(key);
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return null;
    }

    /**
     * @param key          Property to be returned.
     * @param defaultValue Default value of the property.
     * @return If found, string value of the property is returned.
     * Else, the default value is returned.
     */
    public String getStringValue(String key, String defaultValue) {
        if (this.config.containsKey(key))
            return this.config.getProperty(key);
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return defaultValue;
    }

    /**
     * @param key Property to be returned.
     * @return If found, boolean value of the specified property is returned.
     * Else, false is returned.
     * False is also returned, in case the string does not match true.
     */
    public boolean getBooleanValue(String key) {
        if (this.config.containsKey(key)) {
            String val = this.config.getProperty(key).trim().toLowerCase();
            if (val.equals("true"))
                return true;
            return false;
        }
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return false;
    }

    /**
     * @param key          Property to be returned.
     * @param defaultValue Default value of the property.
     * @return If found, boolean value of the property is returned.
     * Else, the default value is returned.
     * The default value is also returned in case the string value
     * does not match true.
     */
    public boolean getBooleanValue(String key, boolean defaultValue) {
        if (this.config.containsKey(key)) {
            String val = this.config.getProperty(key).trim().toLowerCase();
            if (val.equals("true"))
                return true;
            return false;
        }
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return defaultValue;
    }

    /**
     * @param key Property to be returned.
     * @return If found, integer value of the specified property is returned.
     * Else, -1 is returned.
     * -1 is also returned, in case of parse exception.
     */
    public int getIntegerValue(String key) {
        if (this.config.containsKey(key)) {
            String val = this.config.getProperty(key).trim().toLowerCase();
            try {
                return Integer.parseInt(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Error parsing as integer value: " + val
                        + "\nIn " + this.fileName + " for key: " + key, e);
                return -1;
            }
        }
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return -1;
    }

    /**
     * @param key          Property to be returned.
     * @param defaultValue Default value of the property.
     * @return If found, integer value of the property is returned.
     * Else, the default value is returned.
     * The default value is also returned in case of a parse exception.
     */
    public int getIntegerValue(String key, int defaultValue) {
        if (this.config.containsKey(key)) {
            String val = this.config.getProperty(key).trim().toLowerCase();
            try {
                return Integer.parseInt(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Error parsing as integer value: " + val
                        + "\nIn " + this.fileName + " for key: " + key, e);
                return defaultValue;
            }
        }
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return defaultValue;
    }

    /**
     * @param key Property to be returned.
     * @return If found, integer value of the specified property is returned.
     * Else, -1 is returned.
     * -1 is also returned, in case of parse exception.
     */
    public double getDoubleValue(String key) {
        if (this.config.containsKey(key)) {
            String val = this.config.getProperty(key).trim().toLowerCase();
            try {
                return Double.parseDouble(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Error parsing as double value: " + val
                        + "\nIn " + this.fileName + " for key: " + key, e);
                return -1.0;
            }
        }
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return -1.0;
    }

    /**
     * @param key          Property to be returned.
     * @param defaultValue Default value of the property.
     * @return If found, double value of the property is returned.
     * Else, the default value is returned.
     * The default value is also returned in case of a parse exception.
     */
    public double getDoubleValue(String key, double defaultValue) {
        if (this.config.containsKey(key)) {
            String val = this.config.getProperty(key).trim().toLowerCase();
            try {
                return Double.parseDouble(val);
            } catch (NumberFormatException e) {
                LOGGER.error("Error parsing as double value: " + val
                        + "\nIn " + this.fileName + " for key: " + key, e);
                return defaultValue;
            }
        }
        LOGGER.warn("Specified key not found in " + this.fileName + ": " + key);
        return defaultValue;
    }
}
