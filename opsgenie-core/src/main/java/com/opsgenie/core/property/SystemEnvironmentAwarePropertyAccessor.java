package com.opsgenie.core.property;

import java.util.Map;

/**
 * Environment variable based {@link PropertyAccessor} implementation
 * which searches/loads properties from environment variables.
 *
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
