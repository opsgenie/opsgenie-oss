package com.opsgenie.core.property.provider;

import com.opsgenie.core.instance.InstanceDiscovery;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * @author serkan
 */
public final class PropertyProviderRegistry {

    private static final ConcurrentMap<String, PropertyProvider> PROPERTY_PROVIDER_MAP =
            new ConcurrentHashMap<String, PropertyProvider>();
    private static final ConcurrentMap<String, String> PROVIDED_PROPS =
            new ConcurrentHashMap<String, String>();

    static {
        InstanceDiscovery.instancesOf(PropertyProvider.class).forEach(new Consumer<PropertyProvider>() {
            @Override
            public void accept(PropertyProvider propertyProvider) {
                registerProviderInternal(propertyProvider);
            }
        });
    }

    private PropertyProviderRegistry() {
    }

    private static void registerProviderInternal(PropertyProvider propertyProvider) {
        List<String> providedPropertyNames = propertyProvider.getProvidedPropertyNames();
        for (String propName : providedPropertyNames) {
            PropertyProvider existingPropertyProvider =
                    PROPERTY_PROVIDER_MAP.putIfAbsent(propName, propertyProvider);
            if (existingPropertyProvider != null) {
                // Fail-fast
                throw new IllegalStateException(
                        String.format(
                                "Property '%s' is wanted to be provided by the provider '%s' " +
                                "but it is already provided by the provider '%s'!",
                                propName, existingPropertyProvider, propertyProvider));
            }
            String propValue = propertyProvider.getProperty(propName);
            if (propValue != null) {
                PROVIDED_PROPS.put(propName, propValue);
            }
        }
    }

    public static void registerProvider(PropertyProvider propertyProvider) {
        registerProviderInternal(propertyProvider);
    }

    public static String lookupProperty(String propName) {
        PropertyProvider propertyProvider = PROPERTY_PROVIDER_MAP.get(propName);
        if (propertyProvider != null) {
            return propertyProvider.getProperty(propName);
        }
        return null;
    }

    public static Map<String, String> getAllProvidedProperties() {
        return Collections.unmodifiableMap(PROVIDED_PROPS);
    }

}
