package com.opsgenie.core.property;

import com.opsgenie.core.property.provider.PropertyProviderRegistry;

import java.util.Map;

/**
 * @author serkan
 */
public final class ProvidedPropertyAccessor implements PropertyAccessor {

    public static final ProvidedPropertyAccessor INSTANCE = new ProvidedPropertyAccessor();

    private ProvidedPropertyAccessor() {
    }

    @Override
    public String getProperty(String propName) {
        return PropertyProviderRegistry.lookupProperty(propName);
    }

    @Override
    public Map<String, String> getProperties() {
        return PropertyProviderRegistry.getAllProvidedProperties();
    }

}
