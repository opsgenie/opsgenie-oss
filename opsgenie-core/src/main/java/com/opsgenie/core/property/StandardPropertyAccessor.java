package com.opsgenie.core.property;

import com.opsgenie.core.property.provider.ProfileProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard {@link PropertyAccessor} implementation to be used for common cases.
 *
 * @author serkan
 */
public class StandardPropertyAccessor extends CombinedPropertyAccessor {

    public static final String BASE_CONFIG_FILE =
            System.getProperty("opsgenie.property.baseConfigFile", "app-config.properties");

    public static final StandardPropertyAccessor DEFAULT = new StandardPropertyAccessor();

    private StandardPropertyAccessor() {
        super(createStandardPropertyAccessors(null, ProfileProvider.getProfile()));
    }

    public StandardPropertyAccessor(String propFileName) {
        super(createStandardPropertyAccessors(propFileName, ProfileProvider.getProfile()));
    }

    public StandardPropertyAccessor(String propFileName, String profileName) {
        super(createStandardPropertyAccessors(propFileName, profileName));
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

}
