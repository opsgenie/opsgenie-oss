package com.opsgenie.core.property;

import java.util.*;

/**
 * @author serkan
 */
public class CombinedPropertyAccessor implements PropertyAccessor {

    private final List<PropertyAccessor> propertyAccessors;
    private final Map<String, String> props = new HashMap<String, String>();

    public CombinedPropertyAccessor(PropertyAccessor... propertyAccessors) {
        this.propertyAccessors = Arrays.asList(propertyAccessors);
        for (int i = propertyAccessors.length - 1; i >= 0; i--) {
            PropertyAccessor propertyAccessor = propertyAccessors[i];
            props.putAll(propertyAccessor.getProperties());
        }
    }

    public CombinedPropertyAccessor(List<PropertyAccessor> propertyAccessors) {
        this.propertyAccessors = propertyAccessors;
        for (int i = propertyAccessors.size() - 1; i >= 0; i--) {
            PropertyAccessor propertyAccessor = propertyAccessors.get(i);
            props.putAll(propertyAccessor.getProperties());
        }
    }

    @Override
    public String getProperty(String propName) {
        for (PropertyAccessor propertyAccessor : propertyAccessors) {
            String propValue = propertyAccessor.getProperty(propName);
            if (propValue != null) {
                return propValue;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(props);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<PropertyAccessor> propertyAccessors = new ArrayList<PropertyAccessor>();
        private PropertyAccessor latestPropertyAccessor;

        private Builder() {
        }

        public Builder add(PropertyAccessor propertyAccessor) {
            if (latestPropertyAccessor instanceof DelegatedPropertyAccessor) {
                ((DelegatedPropertyAccessor) latestPropertyAccessor).injectPropertyProviderToDelegate(propertyAccessor);
            }
            propertyAccessors.add(propertyAccessor);
            latestPropertyAccessor = propertyAccessor;
            return this;
        }

        public CombinedPropertyAccessor build() {
            return new CombinedPropertyAccessor(propertyAccessors);
        }

    }

}
