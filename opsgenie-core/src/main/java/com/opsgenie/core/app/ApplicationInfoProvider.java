package com.opsgenie.core.app;

/**
 * @author serkan
 *
 * Interface to provide {@link ApplicationInfo}
 * which contains application specific information
 * such as application name, type, id, etc ...
 */
public interface ApplicationInfoProvider {

    /**
     * Provides {@link ApplicationInfo}.
     *
     * @return the provided {@link ApplicationInfo}
     */
    ApplicationInfo getApplicationInfo();

}
