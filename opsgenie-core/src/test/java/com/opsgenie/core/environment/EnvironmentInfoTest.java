package com.opsgenie.core.environment;

import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author serkan
 */
public class EnvironmentInfoTest {

    @After
    public void teardown() {
        EnvironmentInfoManager.clearEnvironmentInfoProviders();
    }

    @Test
    public void environmentInfoProvidersShouldBeAbleToDiscoveredFromClasspath() {
        EnvironmentInfoManager.clearEnvironmentInfoProviders();
        assertEquals(0, EnvironmentInfoManager.getEnvironmentInfoProviders().size());

        EnvironmentInfoManager.registerEnvironmentInfoProvidersFromClasspath();
        assertEquals(1, EnvironmentInfoManager.getEnvironmentInfoProviders().size());

        EnvironmentInfoProvider environmentInfoProvider =
                EnvironmentInfoManager.getEnvironmentInfoProviders().iterator().next();
        assertNotNull(environmentInfoProvider);
        assertTrue(environmentInfoProvider instanceof TestEnvironmentInfoProvider);
        assertEquals(1, environmentInfoProvider.getEnvironmentInfo().size());
        assertEquals("env-info-value-1", environmentInfoProvider.getEnvironmentInfo().get("env-info-1"));
    }

    @Test
    public void customEnvironmentInfoProviderShouldBeAbleToUsed() {
        EnvironmentInfoManager.clearEnvironmentInfoProviders();
        assertEquals(0, EnvironmentInfoManager.getEnvironmentInfoProviders().size());

        EnvironmentInfoProvider environmentInfoProvider1 =
                new EnvironmentInfoProvider() {
                    @Override
                    public Map<String, Object> getEnvironmentInfo() {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("env-info-1", "env-info-value-1");
                        return map;
                    }
                };
        EnvironmentInfoProvider environmentInfoProvider2 =
                new EnvironmentInfoProvider() {
                    @Override
                    public Map<String, Object> getEnvironmentInfo() {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("env-info-2", "env-info-value-2");
                        return map;
                    }
                };
        EnvironmentInfoManager.registerEnvironmentInfoProvider(environmentInfoProvider1);
        EnvironmentInfoManager.registerEnvironmentInfoProvider(environmentInfoProvider2);

        assertEquals(2, EnvironmentInfoManager.getEnvironmentInfoProviders().size());

        assertEquals(1, environmentInfoProvider1.getEnvironmentInfo().size());
        assertEquals("env-info-value-1", environmentInfoProvider1.getEnvironmentInfo().get("env-info-1"));

        assertEquals(1, environmentInfoProvider2.getEnvironmentInfo().size());
        assertEquals("env-info-value-2", environmentInfoProvider2.getEnvironmentInfo().get("env-info-2"));

        Map<String, Object> allEnvironmentInfos = EnvironmentInfoManager.getAllEnvironmentInfos();
        assertEquals(2, allEnvironmentInfos.size());
        assertEquals("env-info-value-1", allEnvironmentInfos.get("env-info-1"));
        assertEquals("env-info-value-2", allEnvironmentInfos.get("env-info-2"));

        EnvironmentInfoManager.deregisterEnvironmentInfoProvider(environmentInfoProvider2);
        assertEquals(1, EnvironmentInfoManager.getEnvironmentInfoProviders().size());
        allEnvironmentInfos = EnvironmentInfoManager.getAllEnvironmentInfos();
        assertEquals(1, allEnvironmentInfos.size());
        assertEquals("env-info-value-1", allEnvironmentInfos.get("env-info-1"));
        assertEquals(null, allEnvironmentInfos.get("env-info-2"));

        EnvironmentInfoManager.clearEnvironmentInfoProviders();
        assertEquals(0, EnvironmentInfoManager.getEnvironmentInfoProviders().size());
        allEnvironmentInfos = EnvironmentInfoManager.getAllEnvironmentInfos();
        assertEquals(0, allEnvironmentInfos.size());
        assertEquals(null, allEnvironmentInfos.get("env-info-1"));
        assertEquals(null, allEnvironmentInfos.get("env-info-2"));
    }

}
