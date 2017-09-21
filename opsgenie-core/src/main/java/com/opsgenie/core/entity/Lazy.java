package com.opsgenie.core.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author serkan
 */
@Target({ FIELD })
@Retention(RUNTIME)
public @interface Lazy {
}
