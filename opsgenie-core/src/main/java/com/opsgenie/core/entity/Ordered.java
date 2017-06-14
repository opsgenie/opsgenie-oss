package com.opsgenie.core.entity;

/**
 * @author serkan
 *
 * Interface to specify order.
 */
public interface Ordered {

    /**
     * Returns the order.
     *
     * @return the order
     */
    default int order() {
        return 0;
    }

}
