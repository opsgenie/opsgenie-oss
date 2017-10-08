package com.opsgenie.core.entity;

/**
 * Interface to represent types which can give information about themselves.
 *
 * @author serkan
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
