package com.opsgenie.core.property;

import java.util.*;

/**
 * @author serkan
 */
public class SystemPropertyAwarePropertyAccessor implements PropertyAccessor {

    @Override
    public String getProperty(String propName) {
        return System.getProperty(propName);
    }

    @Override
    public Map<String, String> getProperties() {
        Properties properties = System.getProperties();
        Set<String> propNames = properties.stringPropertyNames();
        Map<String, String> propMap = new HashMap<String, String>(propNames.size());
        for (String propName : properties.stringPropertyNames()) {
            propMap.put(propName, properties.getProperty(propName));
        }
        return propMap;
    }

}
