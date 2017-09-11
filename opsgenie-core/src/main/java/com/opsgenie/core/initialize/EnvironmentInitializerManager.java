package com.opsgenie.core.initialize;

import com.opsgenie.core.instance.InstanceDiscovery;

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
            long start = System.currentTimeMillis();
            try {
                environmentInitializer.initialize();
                System.out.println(
                        String.format(
                                "Environment initializer %s has completed its initialization in %d milliseconds",
                                environmentInitializer, System.currentTimeMillis() - start));
            } catch (Throwable t) {
                t.printStackTrace();
                System.err.println(
                        String.format(
                                "Environment initializer %s has failed because of %s after %d milliseconds: %s",
                                environmentInitializer,
                                t.getMessage(),
                                System.currentTimeMillis() - start,
                                t.getMessage()));
            }
        }
    }

}
