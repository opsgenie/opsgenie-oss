package com.opsgenie.core.property;

import java.util.Map;

/**
 * Interface for implementations which provide properties.
 *
 * @author serkan
 */
public interface PropertyAccessor {

    /**
     * Gets the property associated with given property name.
     *
     * @param propName the name of property to be retrieved
     * @return the property associated with given property name
     */
    String getProperty(String propName);

    /**
     * Gets the all properties.
     *
     * @return the all properties
     */
    Map<String, String> getProperties();

}
