package com.opsgenie.core.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author serkan
 */
public final class PropertyAccessorUtil {

    private PropertyAccessorUtil() {
    }

    public static void loadProperties(Map<String, String> props, InputStream inputStream) throws IOException {
        if (inputStream != null) {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (String propName : properties.stringPropertyNames()) {
                props.put(propName, properties.getProperty(propName));
            }
        }
    }

}
