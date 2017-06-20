package com.opsgenie.aws.s3.property;

import com.opsgenie.core.property.*;
import com.opsgenie.core.property.provider.ProfileProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard {@link PropertyAccessor} implementation
 * for AWS S3 based properties to be used for common cases.
 *
 * @author serkan
 */
public class S3AwareStandardPropertyAccessor
        extends CombinedPropertyAccessor {

    public S3AwareStandardPropertyAccessor(String bucketName, String objectFolder, String objectName) {
        this(bucketName, objectFolder, objectName, ProfileProvider.getProfile());
    }

    public S3AwareStandardPropertyAccessor(String bucketName, String objectFolder, String objectName,
                                           String profileName) {
        super(createStandardPropertyAccessors(bucketName, objectFolder, objectName, profileName));
    }

    private static List<PropertyAccessor> createStandardPropertyAccessors(String bucketName, String objectFolder,
                                                                          String objectName, String profileName) {
        List<PropertyAccessor> propertyAccessors = new ArrayList<PropertyAccessor>();

        propertyAccessors.add(ProvidedPropertyAccessor.INSTANCE);
        propertyAccessors.add(new SystemPropertyAwarePropertyAccessor());
        propertyAccessors.add(new SystemEnvironmentAwarePropertyAccessor());

        if (objectName != null) {
            propertyAccessors.add(
                    new S3AwarePropertyAccessor(bucketName, objectFolder, objectName, profileName));
            propertyAccessors.add(new UserHomeAwarePropertyAccessor(objectName, profileName));
            propertyAccessors.add(new ClassPathAwarePropertyAccessor(objectName, profileName));
        }

        propertyAccessors.add(
                new S3AwarePropertyAccessor(bucketName, objectFolder, StandardPropertyAccessor.BASE_CONFIG_FILE, profileName));
        propertyAccessors.add(new UserHomeAwarePropertyAccessor(StandardPropertyAccessor.BASE_CONFIG_FILE, profileName));
        propertyAccessors.add(new ClassPathAwarePropertyAccessor(StandardPropertyAccessor.BASE_CONFIG_FILE, profileName));

        return propertyAccessors;
    }

}
