package com.opsgenie.core.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark the class with any value.
 *
 * @author serkan
 */
@Target({ TYPE, METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface Annotated {

    /**
     * Gets the annotated value
     *
     * @return the annotated value
     */
    String[] value() default {};

}
