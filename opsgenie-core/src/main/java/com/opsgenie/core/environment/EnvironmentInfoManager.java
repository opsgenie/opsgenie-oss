package com.opsgenie.core.environment;

import com.opsgenie.core.instance.InstanceDiscovery;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manager class to manage environment information and
 * {@link EnvironmentInfoProvider} related operations.
 *
 * @author serkan
 */
public final class EnvironmentInfoManager {

    private static final List<EnvironmentInfoProvider> ENVIRONMENT_INFO_PROVIDER_LIST =
            new CopyOnWriteArrayList<EnvironmentInfoProvider>();

    static {
        registerEnvironmentInfoProvidersFromClasspath();
    }

    private EnvironmentInfoManager() {
    }

    public static Collection<EnvironmentInfoProvider> getEnvironmentInfoProviders() {
        return Collections.unmodifiableList(ENVIRONMENT_INFO_PROVIDER_LIST);
    }

    public static void registerEnvironmentInfoProvider(EnvironmentInfoProvider environmentInfoProvider) {
        ENVIRONMENT_INFO_PROVIDER_LIST.add(environmentInfoProvider);
    }

    public static void registerEnvironmentInfoProvidersFromClasspath() {
        ENVIRONMENT_INFO_PROVIDER_LIST.addAll(InstanceDiscovery.instancesOf(EnvironmentInfoProvider.class));
    }

    public static void deregisterEnvironmentInfoProvider(EnvironmentInfoProvider environmentInfoProvider) {
        ENVIRONMENT_INFO_PROVIDER_LIST.remove(environmentInfoProvider);
    }

    public static void clearEnvironmentInfoProviders() {
        ENVIRONMENT_INFO_PROVIDER_LIST.clear();
    }

    public static Map<String, Object> getAllEnvironmentInfos() {
        Map<String, Object> allEnvironmentInfo = new HashMap<String, Object>();
        for (EnvironmentInfoProvider environmentInfoProvider : ENVIRONMENT_INFO_PROVIDER_LIST) {
            Map<String, Object> environmentInfo = environmentInfoProvider.getEnvironmentInfo();
            if (environmentInfo != null) {
                allEnvironmentInfo.putAll(environmentInfo);
            }
        }
        return allEnvironmentInfo;
    }

}
