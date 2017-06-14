package com.opsgenie.core.property.provider;

import com.opsgenie.core.property.PropertyAccessor;

import java.util.List;

/**
 * @author serkan
 */
public interface PropertyProvider extends PropertyAccessor {

    List<String> getProvidedPropertyNames();

}
