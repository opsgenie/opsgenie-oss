package com.opsgenie.core.property;

import com.opsgenie.core.util.ExceptionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author serkan
 */
public class FileSystemAwarePropertyAccessor implements PropertyAccessor {

    private final Map<String, String> props = new HashMap<String, String>();

    public FileSystemAwarePropertyAccessor(String dirPath, String fileName) {
        this(dirPath, fileName, null);
    }

    public FileSystemAwarePropertyAccessor(String dirPath, String fileName, String profileName) {
        File propertyFile = new File(dirPath + File.separator + fileName);

        if (propertyFile.exists()) {
            try (FileInputStream propertyFileStream = new FileInputStream(propertyFile)) {
                PropertyAccessorUtil.loadProperties(props, propertyFileStream);
            } catch (IOException e) {
                ExceptionUtil.sneakyThrow(e);
            }
        }

        if (profileName != null && profileName.length() > 0) {
            propertyFile = new File(dirPath + File.separator + profileName + File.separator + fileName);
            if (propertyFile.exists()) {
                try (FileInputStream propertyFileStream = new FileInputStream(propertyFile)) {
                    PropertyAccessorUtil.loadProperties(props, propertyFileStream);
                } catch (IOException e) {
                    ExceptionUtil.sneakyThrow(e);
                }
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
