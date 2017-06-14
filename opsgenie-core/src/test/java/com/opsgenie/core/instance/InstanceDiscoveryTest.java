package com.opsgenie.core.instance;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author serkan
 */
public class InstanceDiscoveryTest {

    @Test
    public void instanceShouldBeAbleToDiscovered() {
        TestDiscoveredInstance testDiscoveredInstance =
                InstanceDiscovery.instanceOf(TestDiscoveredInstance.class);
        assertNotNull(testDiscoveredInstance);
        assertTrue(testDiscoveredInstance instanceof TestDiscoveredInstanceImpl);
    }

}
