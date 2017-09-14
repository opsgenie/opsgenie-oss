package com.opsgenie.core.entity;

/**
 * @author serkan
 *
 * Interface for implementations which can be activated/deactivated.
 */
public interface Activatable {

    /**
     * Returns activation status whether it is active.
     *
     * @return <code>true</code> if active, <code>false</code> otherwise
     */
    boolean isActive();

    /**
     * Activates.
     */
    void activate();

    /**
     * Deactivates.
     */
    void deactivate();

}
