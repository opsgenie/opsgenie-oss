package com.opsgenie.core.property;

import java.util.Map;

/**
 * @author serkan
 */
public class SystemEnvironmentAwarePropertyAccessor implements PropertyAccessor {

    @Override
    public String getProperty(String propName) {
        return System.getenv(propName);
    }

    @Override
    public Map<String, String> getProperties() {
        return System.getenv();
    }

}
