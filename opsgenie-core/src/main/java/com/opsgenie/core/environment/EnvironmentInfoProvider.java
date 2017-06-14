package com.opsgenie.core.environment;

import com.opsgenie.core.app.ApplicationInfo;
import com.opsgenie.core.entity.Ordered;

import java.util.Map;

/**
 * <p>
 * Interface to provide environment information.
 * </p>
 * <p>
 * Note that environment information is not the same as
 * application information {@link ApplicationInfo}.
 * While application information is application specific,
 * environment information may contain additional informations
 * which are not specific to application but the environment
 * such as host ip, host name, etc ...
 * </p>
 *
 * @author serkan
 */
public interface EnvironmentInfoProvider extends Ordered {

    /**
     * Provides environment information.
     *
     * @return the provided environment information
     */
    Map<String, Object> getEnvironmentInfo();

}
