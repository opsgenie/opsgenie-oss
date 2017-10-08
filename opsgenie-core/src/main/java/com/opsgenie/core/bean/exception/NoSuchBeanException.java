package com.opsgenie.core.bean.exception;

/**
 * @author serkan
 */
public class NoSuchBeanException extends BeanException {

    public NoSuchBeanException() {
    }

    public NoSuchBeanException(String message) {
        super(message);
    }

    public NoSuchBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchBeanException(Throwable cause) {
        super(cause);
    }

    public NoSuchBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
