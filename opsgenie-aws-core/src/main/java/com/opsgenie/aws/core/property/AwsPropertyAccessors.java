package com.opsgenie.aws.core.property;

import com.amazonaws.auth.*;
import com.amazonaws.util.StringUtils;
import com.opsgenie.core.property.PropertyAccessor;

import java.util.Map;

/**
 * Defines AWS based {@link PropertyAccessor} implementations.
 *
 * @author serkan
 */
public enum AwsPropertyAccessors
        implements PropertyAccessor, AwsProperties {

    STANDARD_AWS_CLIENT(new StandardAwsClientPropertyAccessor());

    private static final AWSCredentials STANDARD_CREDENTIALS = getStandardCredentials();

    private PropertyAccessor propertyAccessor;

    AwsPropertyAccessors(PropertyAccessor propertyAccessor) {
        this.propertyAccessor  = propertyAccessor;
    }

    public String getAccessKey() {
        return getProperty(AwsProperties.AWS_CLIENT_ACCESS_KEY);
    }

    public String getSecretKey() {
        return getProperty(AwsProperties.AWS_CLIENT_SECRET_KEY);
    }

    public String getRegion() {
        return getProperty(AwsProperties.AWS_CLIENT_REGION);
    }

    @Override
    public String getProperty(String propName) {
        return propertyAccessor.getProperty(propName);
    }

    @Override
    public Map<String, String> getProperties() {
        return propertyAccessor.getProperties();
    }

    private static AWSCredentials getStandardCredentials() {
        String accessKey = STANDARD_AWS_CLIENT.getProperty(AWS_CLIENT_ACCESS_KEY);
        String secretKey = STANDARD_AWS_CLIENT.getProperty(AWS_CLIENT_SECRET_KEY);
        if (StringUtils.isNullOrEmpty(accessKey) || StringUtils.isNullOrEmpty(secretKey)) {
            return null;
        }
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    public static AWSCredentialsProvider getDefaultCredentialsProvider() {
        if (STANDARD_CREDENTIALS == null) {
            return DefaultAWSCredentialsProviderChain.getInstance();
        }
        return new AWSStaticCredentialsProvider(STANDARD_CREDENTIALS);
    }

    public static AWSCredentials getDefaultCredentials() {
        return getDefaultCredentialsProvider().getCredentials();
    }

}
