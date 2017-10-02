package com.opsgenie.core.bean;

import com.opsgenie.core.util.ExceptionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manager class to bean concept and {@link BeanProvider} related operations.
 *
 * @author serkan
 */
public class BeanManager {

    private static final Map<Object, Map<Class, BeanProvider>> beanProviderMaps =
            new ConcurrentHashMap<Object, Map<Class, BeanProvider>>();
    private static volatile BeanProviderFactory globalBeanProviderFactory;

    private BeanManager() {
    }

    public static BeanProviderFactory getGlobalBeanProviderFactory() {
        return globalBeanProviderFactory;
    }

    public static void setGlobalBeanProviderFactory(BeanProviderFactory globalBeanProviderFactory) {
        BeanManager.globalBeanProviderFactory = globalBeanProviderFactory;
    }

    private static Map<Class, BeanProvider> getOrCreateBeanProviderMap(Object beanProviderIdentifier) {
        Map<Class, BeanProvider> beanProviderMap = beanProviderMaps.get(beanProviderIdentifier);
        if (beanProviderMap == null) {
            synchronized (beanProviderMaps) {
                beanProviderMap = beanProviderMaps.get(beanProviderIdentifier);
                if (beanProviderMap == null) {
                    beanProviderMap = new ConcurrentHashMap<Class, BeanProvider>();
                    beanProviderMaps.put(beanProviderIdentifier, beanProviderMap);
                }
            }
        }
        return beanProviderMap;
    }

    public static <P extends BeanProvider> P getOrCreateBeanProvider(Object beanProviderScope,
                                                                     Class<P> beanProviderClass,
                                                                     BeanProviderArg... beanProviderArgs) {
        return getOrCreateBeanProvider(beanProviderScope, beanProviderClass, null, beanProviderArgs);
    }

    public static <P extends BeanProvider> P getOrCreateBeanProvider(Object beanProviderScope,
                                                                     Class<P> beanProviderClass,
                                                                     BeanProviderFactory beanProviderFactory,
                                                                     BeanProviderArg... beanProviderArgs) {
        Map<Class, BeanProvider> beanProviderMap = getOrCreateBeanProviderMap(beanProviderScope);
        P beanProvider = (P) beanProviderMap.get(beanProviderClass);
        if (beanProvider == null) {
            synchronized (beanProviderMap) {
                beanProvider = (P) beanProviderMap.get(beanProviderClass);
                if (beanProvider == null) {
                    if (beanProviderFactory == null) {
                        beanProviderFactory = BeanManager.globalBeanProviderFactory;
                    }
                    if (beanProviderFactory != null) {
                        beanProvider =
                                beanProviderFactory.create(beanProviderScope, beanProviderClass, beanProviderArgs);
                    } else {
                        try {
                            if (beanProviderArgs != null && beanProviderArgs.length > 0) {
                                Class<?>[] beanProviderArgTypes = new Class[beanProviderArgs.length];
                                for (int i = 0; i < beanProviderArgs.length; i++) {
                                    beanProviderArgTypes[i] = beanProviderArgs[i].getArgType();
                                }
                                Constructor<P> ctor = beanProviderClass.getConstructor(beanProviderArgTypes);
                                beanProvider = ctor.newInstance(beanProviderArgs);
                            } else {
                                beanProvider = beanProviderClass.newInstance();
                            }
                        } catch (InstantiationException | IllegalAccessException |
                                NoSuchMethodException | InvocationTargetException e) {
                            ExceptionUtil.sneakyThrow(e);
                        }
                    }
                    beanProviderMap.put(beanProviderClass, beanProvider);
                }
            }
        }
        return beanProvider;
    }

    public static void resetAll() {
        Iterator<Map<Class, BeanProvider>> iter1 = beanProviderMaps.values().iterator();
        while (iter1.hasNext()) {
            Map<Class, BeanProvider> beanProviderMap = iter1.next();
            Iterator<BeanProvider> iter2 = beanProviderMap.values().iterator();
            while (iter2.hasNext()) {
                BeanProvider beanProvider = iter2.next();
                beanProvider.reset();
            }
        }
        globalBeanProviderFactory = null;
    }

    public static void destroyAll() {
        Iterator<Map<Class, BeanProvider>> iter1 = beanProviderMaps.values().iterator();
        while (iter1.hasNext()) {
            Map<Class, BeanProvider> beanProviderMap = iter1.next();
            iter1.remove();
            Iterator<BeanProvider> iter2 = beanProviderMap.values().iterator();
            while (iter2.hasNext()) {
                BeanProvider beanProvider = iter2.next();
                iter2.remove();
                beanProvider.destroy();
            }
        }
        globalBeanProviderFactory = null;
    }

}
