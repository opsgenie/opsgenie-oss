package com.opsgenie.core.layer;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark the class as in <b>Repository</b> layer.
 *
 * @author serkan
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Inherited
public @interface Controller {
}
