package com.opsgenie.aws.core.property;

import com.opsgenie.core.property.*;
import com.opsgenie.core.property.provider.ProfileProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard {@link PropertyAccessor} implementation
 * for {@link AwsProperties} to be used for common cases.
 *
 * @author serkan
 */
public class StandardAwsClientPropertyAccessor
        extends CombinedPropertyAccessor
        implements AwsProperties {

    public StandardAwsClientPropertyAccessor() {
        super(createStandardPropertyAccessors(AWS_CLIENT_PROPERTY_FILE_NAME, ProfileProvider.getProfile()));
    }

    public StandardAwsClientPropertyAccessor(String propFileName) {
        super(createStandardPropertyAccessors(propFileName, ProfileProvider.getProfile()));
    }

    public StandardAwsClientPropertyAccessor(String propFileName, String profileName) {
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

        propertyAccessors.add(new UserHomeAwarePropertyAccessor(StandardPropertyAccessor.BASE_CONFIG_FILE, profileName));
        propertyAccessors.add(new ClassPathAwarePropertyAccessor(StandardPropertyAccessor.BASE_CONFIG_FILE, profileName));

        propertyAccessors.add(new AwsHomeAwarePropertyAccessor());

        return propertyAccessors;
    }

}
