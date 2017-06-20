package com.opsgenie.core.property;

import com.opsgenie.core.property.provider.PropertyProviderManager;

import java.util.Map;

/**
 * {@link PropertyAccessor} implementation which provides properties
 * over registered {@link com.opsgenie.core.property.provider.PropertyProvider}s.
 *
 * @author serkan
 */
public final class ProvidedPropertyAccessor implements PropertyAccessor {

    public static final ProvidedPropertyAccessor INSTANCE = new ProvidedPropertyAccessor();

    private ProvidedPropertyAccessor() {
    }

    @Override
    public String getProperty(String propName) {
        return PropertyProviderManager.lookupProperty(propName);
    }

    @Override
    public Map<String, String> getProperties() {
        return PropertyProviderManager.getAllProvidedProperties();
    }

}
