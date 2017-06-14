package com.opsgenie.core.property;

import com.opsgenie.core.util.ExceptionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author serkan
 */
public class InputStreamAwarePropertyAccessor implements PropertyAccessor {

    private final Map<String, String> props = new HashMap<String, String>();

    public InputStreamAwarePropertyAccessor(InputStream ... inputStreams) {
        try {
            for (InputStream inputStream : inputStreams) {
                PropertyAccessorUtil.loadProperties(props, inputStream);
            }
        } catch (IOException e) {
            ExceptionUtil.sneakyThrow(e);
        }
    }

    @Override
    public String getProperty(String propName) {
        return props.get(propName);
    }

    @Override
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(props);
    }

}
