package com.opsgenie.core.instance;

import com.opsgenie.core.entity.Ordered;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Discovers instances from <code>META-INF/services/[type_name]</code>
 * under classpath based on Java Service API standard.
 *
 * @author serkan
 */
public final class InstanceDiscovery {

    private static final String PREFIX = "META-INF/services/";

    private InstanceDiscovery() {
    }

    private static void fail(Class<?> type, String msg, Throwable cause) {
        throw new RuntimeException(type.getName() + ": " + msg, cause);
    }

    private static void fail(Class<?> type, String msg) {
        throw new RuntimeException(type.getName() + ": " + msg);
    }

    private static void fail(Class<?> type, URL url, int line, String msg) {
        fail(type, url + ":" + line + ": " + msg);
    }

    private static int parseLine(Class<?> type, URL url, BufferedReader reader, int lc,
                                 Set<String> typeNames) throws IOException {
        String ln = reader.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf('#');
        if (ci >= 0) {
            ln = ln.substring(0, ci);
        }
        ln = ln.trim();
        int n = ln.length();
        if (n != 0) {
            if ((ln.indexOf(' ') >= 0) || (ln.indexOf('\t') >= 0)) {
                fail(type, url, lc, "Illegal configuration-file syntax");
            }
            int cp = ln.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp)) {
                fail(type, url, lc, "Illegal instance-class name: " + ln);
            }
            for (int i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
                cp = ln.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp) && (cp != '.')) {
                    fail(type, url, lc, "Illegal instance-class name: " + ln);
                }
            }
            typeNames.add(ln);
        }
        return lc + 1;
    }

    private static Set<String> parse(Class<?> service, URL url) {
        InputStream in = null;
        BufferedReader reader = null;
        Set<String> typeNames = new HashSet<String>();
        try {
            in = url.openStream();
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int lc = 1;
            while ((lc = parseLine(service, url, reader, lc, typeNames)) >= 0);
        } catch (IOException x) {
            fail(service, "Error reading configuration file", x);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException y) {
                fail(service, "Error closing configuration file", y);
            }
        }
        return typeNames;
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static <T> List<Class<? extends T>> typesOf(Class<T> type) {
        return typesOf(type, null);
    }

    public static <T> List<Class<? extends T>> typesOf(Class<T> type, ClassLoader loader) {
        List<Class<? extends T>> types = new ArrayList<Class<? extends T>>();
        try {
            Enumeration<URL> configs;
            String fullName = PREFIX + type.getName();
            if (loader == null) {
                configs = InstanceDiscovery.class.getClassLoader().getResources(fullName);
            } else {
                configs = loader.getResources(fullName);
            }

            Set<String> allTypeNames = new HashSet<String>();
            if (configs != null) {
                while (configs.hasMoreElements()) {
                    Set<String> typeNames = parse(type, configs.nextElement());
                    allTypeNames.addAll(typeNames);
                }
            }

            for (String typeName : allTypeNames) {
                try {
                    Class<? extends T> instanceType;
                    if (loader == null) {
                        instanceType = (Class<? extends T>) Class.forName(typeName, false, InstanceDiscovery.class.getClassLoader());
                    } else {
                        instanceType = (Class<? extends T>) loader.loadClass(typeName);
                    }
                    if (!type.isAssignableFrom(instanceType)) {
                        fail(type, "Instance type " + typeName  + " not a subtype");
                    }
                    types.add(instanceType);
                } catch (ClassNotFoundException e) {
                    fail(type, "Instance class " + typeName + " not found");
                }
            }
        } catch (IOException e) {
            fail(type, "Error locating configuration files", e);
        }
        return types;
    }

    public static <T> Class<? extends T> typeOf(Class<T> type, ClassLoader loader) {
        List<Class<? extends T>> types = typesOf(type, loader);
        if (types.isEmpty()) {
            return null;
        }
        if (types.size() > 1) {
            throw new IllegalStateException("Multiple type of " + type + " have been found: " + types);
        }
        return types.get(0);
    }

    public static <T> Class<? extends T> typeOf(Class<T> type) {
        return typeOf(type, null);
    }

    /////////////////////////////////////////////////////////////////////////////////

    private static <T> List<T> returnInstances(Class<T> type, List<T> instances) {
        if (Ordered.class.isAssignableFrom(type)) {
            Collections.sort(instances, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return Integer.compare(((Ordered) o1).order(), ((Ordered) o2).order());
                }
            });
        }
        return instances;
    }

    public static <T> List<T> instancesOf(Class<T> type) {
        return instancesOf(type, null);
    }

    public static <T> List<T> instancesOf(Class<T> type, ClassLoader loader) {
        return instancesOf(type, loader, InstanceScope.GLOBAL);
    }

    public static <T> List<T> instancesOf(Class<T> type, ClassLoader loader,
                                          InstanceScope scope) {
        return instancesOf(type, loader, scope, null);
    }

    public static <T> List<T> instancesOf(Class<T> type, ClassLoader loader,
                                          InstanceScope scope, InstanceCreator creator) {
        List<T> instances = new ArrayList<T>();
        List<Class<? extends T>> types = typesOf(type, loader);
        for (Class<? extends T> instanceType : types) {
            try {
                T instance = InstanceProvider.getInstance(instanceType, scope, creator);
                instances.add(instance);
            } catch (Exception e) {
                fail(type, "Instance class " + instanceType.getName() + " could not be instantiated", e);
            }
        }
        return returnInstances(type, instances);
    }

    public static <T> T instanceOf(Class<T> type) {
        return instanceOf(type, null);
    }

    public static <T> T instanceOf(Class<T> type, ClassLoader loader) {
        return instanceOf(type, loader, InstanceScope.GLOBAL);
    }

    public static <T> T instanceOf(Class<T> type, ClassLoader loader, InstanceScope scope) {
        return instanceOf(type, loader, scope, null);
    }

    public static <T> T instanceOf(Class<T> type, ClassLoader loader,
                                   InstanceScope scope, InstanceCreator creator) {
        List<T> instances = instancesOf(type, loader, scope, creator);
        if (instances.isEmpty()) {
            return null;
        }
        if (instances.size() > 1) {
            throw new IllegalStateException("Multiple instance of " + type + " have been found: " + instances);
        }
        return instances.get(0);
    }

}