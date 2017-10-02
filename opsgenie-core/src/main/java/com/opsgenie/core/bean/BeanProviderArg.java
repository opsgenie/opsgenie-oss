package com.opsgenie.core.bean;

/**
 * Represents single argument (with type and value)
 * to be used for creating {@link BeanProvider} instance
 * by {@link BeanProviderFactory}.
 *
 * @author serkan
 */
public class BeanProviderArg {

    private final Class<?> argType;
    private final Object argValue;

    public BeanProviderArg(Class<?> argType, Object argValue) {
        this.argType = argType;
        this.argValue = argValue;
    }

    public Class<?> getArgType() {
        return argType;
    }

    public Object getArgValue() {
        return argValue;
    }

}
