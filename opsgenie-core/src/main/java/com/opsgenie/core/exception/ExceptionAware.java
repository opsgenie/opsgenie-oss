package com.opsgenie.core.exception;

/**
 * Interface to represent types which can give information
 * whether an exception has been attached to themselves.
 *
 * @author serkan
 */
public interface ExceptionAware {

    /**
     * Gets the associated {@link ExceptionWrapper}.
     *
     * @return the associated {@link ExceptionWrapper}
     */
    ExceptionWrapper getExceptionWrapper();

    /**
     * Sets the associated {@link ExceptionWrapper}.
     *
     * @param exceptionWrapper the {@link ExceptionWrapper} to be set
     * @return <code>true</code> if set is successful, <code>false</code> otherwise
     */
    boolean setExceptionWrapper(ExceptionWrapper exceptionWrapper);

}
