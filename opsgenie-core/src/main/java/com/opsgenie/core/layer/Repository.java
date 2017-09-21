package com.opsgenie.core.layer;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author serkan
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface Repository {
}
