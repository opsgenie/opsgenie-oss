package com.opsgenie.core.instance;

import com.opsgenie.core.util.ExceptionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Provides instance for given {@link Class} at given {@link InstanceScope}
 * by using specified {@link InstanceCreator}.
 *
 * @author serkan
 */
public final class InstanceProvider {

    private static final Map<InstanceScope, InstanceFactory> INSTANCE_FACTORY_MAP =
            new HashMap<InstanceScope, InstanceFactory>();
    private static final DefaultInstanceCreator DEFAULT_INSTANCE_CREATOR =
            new DefaultInstanceCreator();

    static {
        INSTANCE_FACTORY_MAP.put(
                InstanceScope.GLOBAL,
                new GlobalInstanceFactory());
        INSTANCE_FACTORY_MAP.put(
                InstanceScope.THREAD_LOCAL,
                new ThreadLocalInstanceFactory());
        INSTANCE_FACTORY_MAP.put(
                InstanceScope.INHERITABLE_THREAD_LOCAL,
                new InheritableThreadLocalInstanceFactory());
        INSTANCE_FACTORY_MAP.put(
                InstanceScope.PROTOTYPE,
                new PrototypeInstanceFactory());
    }

    private InstanceProvider() {
    }

    public static <T> T getInstance(Class<T> clazz, InstanceScope scope) {
        return getInstance(clazz, scope, DEFAULT_INSTANCE_CREATOR);
    }

    public static <T> T getInstance(Class<T> clazz, InstanceScope scope,
                                    InstanceCreator creator) {
        InstanceFactory instanceFactory = INSTANCE_FACTORY_MAP.get(scope);
        if (instanceFactory == null) {
            throw new IllegalArgumentException("Unsupported instance scope type: " + scope);
        }
        if (creator == null) {
            creator = DEFAULT_INSTANCE_CREATOR;
        }
        return instanceFactory.get(creator, clazz);
    }

    private static class DefaultInstanceCreator implements InstanceCreator {

        @Override
        public <T> T create(Class<T> clazz) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                ExceptionUtil.sneakyThrow(e);
                return null;
            }
        }

    }

    private interface InstanceFactory {

        <T> T get(InstanceCreator creator, Class<T> clazz);

    }

    private static class GlobalInstanceFactory implements InstanceFactory {

        private final ConcurrentMap<Class, Object> instanceMap =
                new ConcurrentHashMap<Class, Object>();

        @Override
        public <T> T get(InstanceCreator creator, Class<T> clazz) {
            Object instance = instanceMap.get(clazz);
            if (instance == null) {
                synchronized (instanceMap) {
                    instance = instanceMap.get(clazz);
                    if (instance == null) {
                        instance = creator.create(clazz);
                        instanceMap.put(clazz, instance);
                    }
                }
            }
            return (T) instance;
        }

    }

    private static class ThreadLocalInstanceFactory implements InstanceFactory {

        private final ThreadLocal<Object> threadLocalInstance =
                new ThreadLocal<Object>();

        @Override
        public <T> T get(InstanceCreator creator, Class<T> clazz) {
            Object instance = threadLocalInstance.get();
            if (instance == null) {
                instance = creator.create(clazz);
                threadLocalInstance.set(instance);
            }
            return (T) instance;
        }

    }

    private static class InheritableThreadLocalInstanceFactory implements InstanceFactory {

        private final ThreadLocal<Object> threadLocalInstance =
                new InheritableThreadLocal<Object>();

        @Override
        public <T> T get(InstanceCreator creator, Class<T> clazz) {
            Object instance = threadLocalInstance.get();
            if (instance == null) {
                instance = creator.create(clazz);
                threadLocalInstance.set(instance);
            }
            return (T) instance;
        }

    }

    private static class PrototypeInstanceFactory implements InstanceFactory {

        @Override
        public <T> T get(InstanceCreator creator, Class<T> clazz) {
            return creator.create(clazz);
        }

    }

    public static <T> T createLazyLoadableInstance(Class<T> instanceInterface, InstanceLoader<T> instanceLoader) {
        return createLazyLoadableInstance(instanceInterface, instanceLoader, null);
    }

    public static <T> T createLazyLoadableInstance(Class<T> instanceInterface,
                                                   InstanceLoader<T> instanceLoader,
                                                   Class<? extends T> instanceClass) {
        if (!instanceInterface.isInterface()) {
            throw new IllegalArgumentException("Specified type for instance interface is not an interface");
        }

        Method getInstTypeMethod = null;
        try {
            getInstTypeMethod = InstanceTypeAwareProxy.class.getMethod("getInstanceType");
        } catch (NoSuchMethodException e) {
            ExceptionUtil.sneakyThrow(e);
        }
        final Method getInstanceTypeMethod = getInstTypeMethod;

        Method getInstClassMethod = null;
        if (instanceClass != null) {
            try {
                getInstClassMethod = InstanceClassAwareProxy.class.getMethod("getInstanceClass");
            } catch (NoSuchMethodException e) {
                ExceptionUtil.sneakyThrow(e);
            }
        }
        final Method getInstanceClassMethod = getInstClassMethod;

        Method getInstMethod = null;
        try {
            getInstMethod = InstanceAwareProxy.class.getMethod("getInstance");
        } catch (NoSuchMethodException e) {
            ExceptionUtil.sneakyThrow(e);
        }
        final Method getInstanceMethod = getInstMethod;

        return (T) Proxy.newProxyInstance(
                instanceInterface.getClassLoader(),
                getInstanceClassMethod != null
                    ? new Class[] { InstanceTypeAwareProxy.class, InstanceClassAwareProxy.class, InstanceAwareProxy.class, instanceInterface }
                    : new Class[] { InstanceTypeAwareProxy.class, InstanceAwareProxy.class, instanceInterface },
                new InvocationHandler() {
                    private final Object mutex = new Object();
                    private volatile Object bean;
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.equals(getInstanceTypeMethod)) {
                            return instanceInterface;
                        } else if (getInstanceClassMethod != null && method.equals(getInstanceClassMethod)) {
                            return instanceClass;
                        } else if (method.equals(getInstanceMethod)) {
                            if (bean == null) {
                                synchronized (mutex) {
                                    if (bean == null) {
                                        bean = instanceLoader.load();
                                    }
                                }
                            }
                            return bean;
                        } else {
                            if (bean == null) {
                                synchronized (mutex) {
                                    if (bean == null) {
                                        bean = instanceLoader.load();
                                    }
                                }
                            }
                            try {
                                return method.invoke(bean, args);
                            } catch (InvocationTargetException e) {
                                throw e.getCause();
                            }
                        }
                    }
                });
    }

}
