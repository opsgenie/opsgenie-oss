package com.opsgenie.core.property;

/**
 * @author serkan
 */
public interface DelegatedPropertyAccessor extends PropertyAccessor {

    void injectPropertyProviderToDelegate(PropertyAccessor propertyAccessor);

}
