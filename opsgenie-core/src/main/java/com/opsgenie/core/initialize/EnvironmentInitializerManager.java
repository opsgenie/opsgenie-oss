package com.opsgenie.core.initialize;

import com.opsgenie.core.instance.InstanceDiscovery;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Manager class to manage environment
 * {@link EnvironmentInitializer} related operations.
 *
 * @author serkan
 */
public final class EnvironmentInitializerManager {

    private static final Logger LOGGER = Logger.getLogger(EnvironmentInitializerManager.class);

    private static boolean initialized = false;
    private static List<EnvironmentInitializer> ENVIRONMENT_INITIALIZERS =
            Collections.unmodifiableList(
                    InstanceDiscovery.instancesOf(EnvironmentInitializer.class));

    private EnvironmentInitializerManager() {
    }

    public static synchronized void ensureInitialized() {
        if (!initialized) {
            try {
                init();
            } finally {
                initialized = true;
            }
        }
    }

    public static Collection<EnvironmentInitializer> getEnvironmentInitializers() {
        return ENVIRONMENT_INITIALIZERS;
    }

    private static void init() {
        for (EnvironmentInitializer environmentInitializer : ENVIRONMENT_INITIALIZERS) {
            try {
                environmentInitializer.initialize();
            } catch (Throwable t) {
                LOGGER.error("Unable to initialize " + environmentInitializer, t);
            }
        }
    }

}
