package com.opsgenie.core.entity;

/**
 * @author serkan
 *
 * Interface to represent types which can give information about themselves.
 */
public interface SelfAware {

    /**
     * Returns status whether this instance is empty.
     *
     * @return <code>true</code> if this instance is empty,
     *         <code>false</code> otherwise
     */
    boolean isEmpty();

}
