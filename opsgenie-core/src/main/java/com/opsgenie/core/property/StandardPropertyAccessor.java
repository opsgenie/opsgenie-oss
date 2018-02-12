package com.opsgenie.core.property;

import com.opsgenie.core.property.provider.ProfileProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Standard {@link PropertyAccessor} implementation to be used for common cases.
 *
 * @author serkan
 */
public class StandardPropertyAccessor
        extends CombinedPropertyAccessor
        implements MutablePropertyAccessor {

    public static final String BASE_CONFIG_FILE =
            System.getProperty("opsgenie.property.baseConfigFile", "app-config.properties");

    public static final StandardPropertyAccessor DEFAULT = new StandardPropertyAccessor();

    private StandardPropertyAccessor() {
        super(new ConcurrentHashMap<String, String>(), // because this property accessor is mutable
              createStandardPropertyAccessors(null, ProfileProvider.getProfile()));
    }

    public StandardPropertyAccessor(String propFileName) {
        super(new ConcurrentHashMap<String, String>(), // because this property accessor is mutable
              createStandardPropertyAccessors(propFileName, ProfileProvider.getProfile()));
    }

    public StandardPropertyAccessor(String propFileName, String profileName) {
        super(new ConcurrentHashMap<String, String>(), // because this property accessor is mutable
              createStandardPropertyAccessors(propFileName, profileName));
    }

    private static List<PropertyAccessor> createStandardPropertyAccessors(String propFileName, String profileName) {
        List<PropertyAccessor> propertyAccessors = new ArrayList<PropertyAccessor>();

        propertyAccessors.add(ProvidedPropertyAccessor.INSTANCE);
        propertyAccessors.add(new SystemPropertyAwarePropertyAccessor());
        propertyAccessors.add(new SystemEnvironmentAwarePropertyAccessor());

        if (propFileName != null) {
            propertyAccessors.add(new UserHomeAwarePropertyAccessor(propFileName, profileName));
            propertyAccessors.add(new ClassPathAwarePropertyAccessor(propFileName, profileName));
        }

        propertyAccessors.add(new UserHomeAwarePropertyAccessor(BASE_CONFIG_FILE, profileName));
        propertyAccessors.add(new ClassPathAwarePropertyAccessor(BASE_CONFIG_FILE, profileName));

        return propertyAccessors;
    }

    @Override
    public String putProperty(String propName, String propValue) {
        return props.put(propName, propValue);
    }

    @Override
    public String putPropertyIfAbsent(String propName, String propValue) {
        return props.putIfAbsent(propName, propValue);
    }

    @Override
    public String removeProperty(String propName) {
        return props.remove(propName);
    }

}
