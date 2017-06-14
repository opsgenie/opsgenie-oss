package com.opsgenie.core.environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author serkan
 */
public class TestEnvironmentInfoProvider implements EnvironmentInfoProvider {

    @Override
    public Map<String, Object> getEnvironmentInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("env-info-1", "env-info-value-1");
        return map;
    }

}
