package com.opsgenie.core.property;

import com.opsgenie.core.property.provider.ProfileProvider;

/**
 * @author serkan
 */
public class UserHomeAwarePropertyAccessor extends FileSystemAwarePropertyAccessor {

    private static final String USER_HOME_DIR = System.getProperty("user.home");

    public UserHomeAwarePropertyAccessor(String fileName) {
        this(fileName, ProfileProvider.getProfile());
    }

    public UserHomeAwarePropertyAccessor(String fileName, String profileName) {
        super(USER_HOME_DIR, fileName, profileName);
    }

}
