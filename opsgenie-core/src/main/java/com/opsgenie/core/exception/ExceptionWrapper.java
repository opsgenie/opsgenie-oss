package com.opsgenie.core.exception;

import com.opsgenie.core.util.ExceptionUtil;

/**
 * Wraps exception with its information without referencing itself.
 *
 * @author serkan
 */
public class ExceptionWrapper {

    private String exceptionType;
    private String exceptionMessage;
    private String exception;

    public ExceptionWrapper() {
    }

    public ExceptionWrapper(String exceptionType, String exceptionMessage, String exception) {
        this.exceptionType = exceptionType;
        this.exceptionMessage = exceptionMessage;
        this.exception = exception;
    }

    public ExceptionWrapper(Throwable error) {
        this.exceptionType = error.getClass().getName();
        this.exceptionMessage = error.getMessage();
        this.exception = ExceptionUtil.toString(error);
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ExceptionWrapper{" +
                "exceptionType='" + exceptionType + '\'' +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", exception='" + exception + '\'' +
                '}';
    }

}
