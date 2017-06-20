package com.opsgenie.core.property;

import com.opsgenie.core.property.provider.ProfileProvider;
import com.opsgenie.core.util.ExceptionUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classpath based {@link PropertyAccessor} implementation
 * which searches/loads properties from classpath.
 *
 * @author serkan
 */
public class ClassPathAwarePropertyAccessor implements PropertyAccessor {

    private final Map<String, String> props = new HashMap<String, String>();

    public ClassPathAwarePropertyAccessor(String fileName) {
        this(fileName, ProfileProvider.getProfile());
    }

    public ClassPathAwarePropertyAccessor(String fileName, String profileName) {
        try (InputStream propertyFileStream =
                     getClass().getClassLoader().getResourceAsStream(fileName)) {
            PropertyAccessorUtil.loadProperties(props, propertyFileStream);
        } catch (IOException e) {
            ExceptionUtil.sneakyThrow(e);
        }

        if (profileName != null && profileName.length() > 0) {
            try (InputStream propertyFileStream =
                         getClass().getClassLoader().
                                 getResourceAsStream(profileName + File.separator + fileName)) {
                PropertyAccessorUtil.loadProperties(props, propertyFileStream);
            } catch (IOException e) {
                ExceptionUtil.sneakyThrow(e);
            }
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
