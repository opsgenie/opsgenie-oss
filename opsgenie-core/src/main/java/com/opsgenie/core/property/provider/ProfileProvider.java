package com.opsgenie.core.property.provider;

import com.opsgenie.core.util.ExceptionUtil;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author serkan
 */
public final class ProfileProvider {

    public static final String PROFILE_SYS_PROP_NAME = "opgenie.profile";
    public static final String PROFILE_FILE_NAME = ".ogprofile";

    private static final String profile;

    static {
        String p = null;

        String profileSysPropValue = System.getProperty(PROFILE_SYS_PROP_NAME);
        if (profileSysPropValue != null && profileSysPropValue.length() > 0) {
            p = profileSysPropValue;
        } else {
            String userHomeDir = System.getProperty("user.home");
            String ogProfileFilePath = userHomeDir + File.separator + PROFILE_FILE_NAME;
            File ogProfileFileFile = new File(ogProfileFilePath);
            if (ogProfileFileFile.exists()) {
                try {
                    byte[] ogProfileContent =
                            IOUtils.readFully(
                                    new FileInputStream(ogProfileFileFile), -1, true);
                    p = new String(ogProfileContent, "UTF-8");
                } catch (Throwable t) {
                    ExceptionUtil.sneakyThrow(t);
                }
            }
        }

        if (p != null) {
            profile = p.trim();
        } else {
            profile = null;
        }
    }

    private ProfileProvider() {
    }

    public static boolean hasProfile() {
        return profile != null && profile.length() > 0;
    }

    public static String getProfile() {
        return profile;
    }

}
