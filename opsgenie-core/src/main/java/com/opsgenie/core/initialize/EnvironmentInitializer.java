package com.opsgenie.core.initialize;

import com.opsgenie.core.entity.Ordered;

/**
 * Interfaces for types to be initialized on startup.
 *
 * @author serkan
 */
public interface EnvironmentInitializer extends Ordered {

    /**
     * Executes initialization logic.
     */
    void initialize();

}
