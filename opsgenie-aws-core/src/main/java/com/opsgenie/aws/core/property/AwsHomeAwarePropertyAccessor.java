package com.opsgenie.aws.core.property;

import com.opsgenie.core.property.FileSystemAwarePropertyAccessor;

import java.io.File;

/**
 * {@link com.opsgenie.core.property.PropertyAccessor} implementation
 * for reading AWS related properties from user home directory.
 *
 * @author serkan
 */
public class AwsHomeAwarePropertyAccessor extends FileSystemAwarePropertyAccessor {

    private static final String AWS_HOME_DIR =
            System.getProperty("user.home") + File.separator + System.getProperty("opsgenie.aws.home", ".aws");
    private static final String CREDENTIALS_FILE_NAME =
            System.getProperty("opsgenie.aws.credentialsFileName", "credentials");

    public AwsHomeAwarePropertyAccessor() {
        this(AWS_HOME_DIR, CREDENTIALS_FILE_NAME);
    }

    public AwsHomeAwarePropertyAccessor(String credentialsFileName) {
        this(AWS_HOME_DIR, credentialsFileName);
    }

    public AwsHomeAwarePropertyAccessor(String awsHomeDir, String credentialsFileName) {
        super(awsHomeDir, credentialsFileName);
    }

    @Override
    public String getProperty(String propName) {
        if (AwsProperties.AWS_CLIENT_ACCESS_KEY.equals(propName)) {
            return super.getProperty("aws_access_key_id");
        } else if (AwsProperties.AWS_CLIENT_SECRET_KEY.equals(propName)) {
            return super.getProperty("aws_secret_access_key");
        } else {
            return super.getProperty(propName);
        }
    }

}
