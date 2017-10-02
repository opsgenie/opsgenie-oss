package com.opsgenie.core.bean;

/**
 * Interface for implementations which create {@link BeanProvider}.
 *
 * @author serkan
 */
public interface BeanProviderFactory {

    /**
     * Creates given <code>beanProviderClass</code> typed {@link BeanProvider} instance.
     *
     * @param beanProviderIdentifier assigned identifier for {@link BeanProvider} instance to be created
     * @param beanProviderClass      type of the {@link BeanProvider} instance to be created
     * @param beanProviderArgs       arguments to be used while creating {@link BeanProvider} instance
     * @param <P>                    generic type of the {@link BeanProvider} instance to be created
     * @return the created {@link BeanProvider} instance
     */
    <P extends BeanProvider> P create(Object beanProviderIdentifier, Class<P> beanProviderClass, BeanProviderArg... beanProviderArgs);

}
