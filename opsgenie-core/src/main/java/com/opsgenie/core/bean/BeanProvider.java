package com.opsgenie.core.bean;

import com.opsgenie.core.instance.InstanceLoader;
import com.opsgenie.core.instance.InstanceProvider;

/**
 * Interface for implementations which provide managed beans.
 *
 * @author serkan
 */
public interface BeanProvider {

    /**
     * Gets the given <code>beanType</code> typed requested bean
     * associated with the given <code>beanName</code> name.
     *
     * @param beanType interface {@link Class} of the requested bean
     * @param beanName name of the requested bean
     * @param <T>      generic type of the bean
     * @return the provided bean
     */
    <T> T get(Class<T> beanType, String beanName);

    /**
     * Gets the given <code>beanType</code> typed default requested bean
     * with no name.
     *
     * @param beanType interface {@link Class} of the requested bean
     * @param <T>      generic type of the bean
     * @return the provided bean
     */
    default <T> T get(Class<T> beanType) {
        return get(beanType, null);
    }

    /**
     * Gets the given <code>beanType</code> typed requested bean
     * associated with the given <code>beanName</code> name.
     *
     * @param beanType interface {@link Class} of the requested bean
     * @param beanName name of the requested bean
     * @param lazy     <code>true</code> if the provided beans should be loaded lazy
     *                 when it first used, <code>false</code> otherwise
     * @param <T>      generic type of the bean
     * @return the provided bean
     */
    default <T> T get(Class<T> beanType, String beanName, boolean lazy) {
        if (lazy) {
            return InstanceProvider.createLazyLoadableInstance(
                    beanType,
                    new InstanceLoader<T>() {
                        @Override
                        public T load() {
                            return get(beanType);
                        }
                    });
        } else {
            return get(beanType, beanName);
        }
    }

    /**
     * Gets the given <code>beanType</code> typed default requested bean
     * with no name.
     *
     * @param beanType interface {@link Class} of the requested bean
     * @param lazy     <code>true</code> if the provided beans should be loaded lazy
     *                 when it first used, <code>false</code> otherwise
     * @param <T>      generic type of the bean
     * @return the provided bean
     */
    default<T> T get(Class<T> beanType, boolean lazy) {
        return get(beanType, null, lazy);
    }

    /**
     * Resets this bean provider by clearing current beans.
     * After reset, this bean provider still can be used.
     * However since the old beans are not exist anymore the new fresh beans are created.
     */
    void reset();

    /**
     * Destroys this bean provider.
     * After destroy, this bean provider cannot be used anymore.
     */
    void destroy();

}
