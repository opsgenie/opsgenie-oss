package com.opsgenie.core.exception;

/**
 * Base class for {@link ExceptionAware} implementations.
 *
 * @author serkan
 */
public abstract class AbstractExceptionAware implements ExceptionAware {

    protected ExceptionWrapper exceptionWrapper;

    @Override
    public ExceptionWrapper getExceptionWrapper() {
        return exceptionWrapper;
    }

    @Override
    public boolean setExceptionWrapper(ExceptionWrapper exceptionWrapper) {
        this.exceptionWrapper = exceptionWrapper;
        return true;
    }

}
