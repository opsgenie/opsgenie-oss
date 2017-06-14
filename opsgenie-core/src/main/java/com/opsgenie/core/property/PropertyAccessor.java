package com.opsgenie.core.property;

import java.util.Map;

public interface PropertyAccessor {

    String getProperty(String propName);

    Map<String, String> getProperties();

}
