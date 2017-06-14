package com.opsgenie.core.property;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author serkan
 */
public class PropertyAccessorTest {

    @Test
    public void systemPropertyAwarePropertyAccessorShouldProvidePropertySuccessfully() {
        System.setProperty("key1", "value1");
        try {
            SystemPropertyAwarePropertyAccessor propertyAccessor =
                    new SystemPropertyAwarePropertyAccessor();
            assertEquals("value1", propertyAccessor.getProperty("key1"));
            assertEquals("value1", propertyAccessor.getProperties().get("key1"));
        } finally {
            System.clearProperty("key1");
        }
    }

    @Test
    public void systemEnvironmentAwarePropertyAccessorShouldProvidePropertySuccessfully() {
        PropertyAccessorTestUtil.setEnvironmentVariable("key1", "value1");
        try {
            SystemEnvironmentAwarePropertyAccessor propertyAccessor =
                    new SystemEnvironmentAwarePropertyAccessor();
            assertEquals("value1", propertyAccessor.getProperty("key1"));
            assertEquals("value1", propertyAccessor.getProperties().get("key1"));
        } finally {
            PropertyAccessorTestUtil.resetEnvironmentVariables();
        }
    }

    @Test
    public void classpathAwarePropertyAccessorShouldProvidePropertySuccessfully() {
        ClassPathAwarePropertyAccessor propertyAccessor =
                new ClassPathAwarePropertyAccessor("test.properties");
        assertEquals("value1", propertyAccessor.getProperty("key1"));
        assertEquals("value1", propertyAccessor.getProperties().get("key1"));
    }

    @Test
    public void classpathAwarePropertyAccessorWithProfileShouldProvidePropertySuccessfully() {
        ClassPathAwarePropertyAccessor propertyAccessor =
                new ClassPathAwarePropertyAccessor("test.properties", "testprofile");
        assertEquals("value11", propertyAccessor.getProperty("key1"));
        assertEquals("value11", propertyAccessor.getProperties().get("key1"));
    }

    @Test
    public void fileSystemAwarePropertyAccessorShouldProvidePropertySuccessfully() {
        String baseDir = getClass().getClassLoader().getResource("").getFile();
        FileSystemAwarePropertyAccessor propertyAccessor =
                new FileSystemAwarePropertyAccessor(baseDir, "test.properties");
        assertEquals("value1", propertyAccessor.getProperty("key1"));
        assertEquals("value1", propertyAccessor.getProperties().get("key1"));
    }

    @Test
    public void fileSystemAwarePropertyAccessorWithProfileShouldProvidePropertySuccessfully() {
        String baseDir = getClass().getClassLoader().getResource("").getFile();
        FileSystemAwarePropertyAccessor propertyAccessor =
                new FileSystemAwarePropertyAccessor(baseDir, "test.properties", "testprofile");
        assertEquals("value11", propertyAccessor.getProperty("key1"));
        assertEquals("value11", propertyAccessor.getProperties().get("key1"));
    }

    @Test
    public void userHomeAwarePropertyAccessorShouldProvidePropertySuccessfully() {
        String baseDir = getClass().getClassLoader().getResource("").getFile();
        String userHome = System.getProperty("user.home");
        System.setProperty("user.home", baseDir);
        try {
            UserHomeAwarePropertyAccessor propertyAccessor =
                    new UserHomeAwarePropertyAccessor("test.properties");
            assertEquals("value1", propertyAccessor.getProperty("key1"));
            assertEquals("value1", propertyAccessor.getProperties().get("key1"));
        } finally {
            System.setProperty("user.home", userHome);
        }
    }

    @Test
    public void providedPropertyAccessorShouldProvidePropertySuccessfully() {
        ProvidedPropertyAccessor propertyAccessor =
                ProvidedPropertyAccessor.INSTANCE;
        assertEquals("value1", propertyAccessor.getProperty("key1"));
        assertNull(null, propertyAccessor.getProperty("key2"));
    }

    @Test
    public void userHomeAwarePropertyAccessorWithProfileShouldProvidePropertySuccessfully() {
        String baseDir = getClass().getClassLoader().getResource("").getFile();
        String userHome = System.getProperty("user.home");
        System.setProperty("user.home", baseDir);
        try {
            UserHomeAwarePropertyAccessor propertyAccessor =
                    new UserHomeAwarePropertyAccessor("test.properties", "testprofile");
            assertEquals("value11", propertyAccessor.getProperty("key1"));
            assertEquals("value11", propertyAccessor.getProperties().get("key1"));
        } finally {
            System.setProperty("user.home", userHome);
        }
    }

    @Test
    public void combinedPropertyAccessorShouldProvidePropertySuccessfully() {
        Map<String, String> props1 = new HashMap<String, String>();
        PropertyAccessor propertyAccessor1 =
                new PropertyAccessor() {
                    @Override
                    public String getProperty(String propName) {
                        return props1.get(propName);
                    }

                    @Override
                    public Map<String, String> getProperties() {
                        return props1;
                    }
                };
        Map<String, String> props2 = new HashMap<String, String>();
        PropertyAccessor propertyAccessor2 =
                new PropertyAccessor() {
                    @Override
                    public String getProperty(String propName) {
                        return props2.get(propName);
                    }

                    @Override
                    public Map<String, String> getProperties() {
                        return props2;
                    }
                };
        props1.put("key1", "value1");
        props1.put("key2", "value2");
        props2.put("key2", "value22");
        props2.put("key3", "value3");

        CombinedPropertyAccessor propertyAccessor =
                CombinedPropertyAccessor.
                        builder().
                            add(propertyAccessor2).
                            add(propertyAccessor1).
                        build();
        assertEquals("value1", propertyAccessor.getProperty("key1"));
        assertEquals("value22", propertyAccessor.getProperty("key2"));
        assertEquals("value3", propertyAccessor.getProperty("key3"));
    }

}
