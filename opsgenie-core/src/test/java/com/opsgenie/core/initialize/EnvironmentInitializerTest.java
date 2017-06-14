package com.opsgenie.core.initialize;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author serkan
 */
public class EnvironmentInitializerTest {

    @Test
    public void environmentInitializersShouldBeAbleToDiscoveredAndInitialized() {
        Collection<EnvironmentInitializer> environmentInitializers =
                EnvironmentInitializerManager.getEnvironmentInitializers();
        assertEquals(1, environmentInitializers.size());

        EnvironmentInitializer environmentInitializer =
                environmentInitializers.iterator().next();
        assertTrue(environmentInitializer instanceof TestEnvironmentInitializer);

        TestEnvironmentInitializer testEnvironmentInitializer =
                (TestEnvironmentInitializer) environmentInitializer;
        assertEquals(0, testEnvironmentInitializer.getInitializeCounter());

        EnvironmentInitializerManager.ensureInitialized();
        assertEquals(1, testEnvironmentInitializer.getInitializeCounter());

        EnvironmentInitializerManager.ensureInitialized();
        assertEquals(1, testEnvironmentInitializer.getInitializeCounter());
    }

}
