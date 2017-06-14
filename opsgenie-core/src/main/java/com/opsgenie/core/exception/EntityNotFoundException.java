package com.opsgenie.core.exception;

/**
 * An {@link Exception} type to represent entity not found errors.
 *
 * @author ocicek
 */
public class EntityNotFoundException extends Exception implements NonCriticalException {

    public EntityNotFoundException(Class<?> entityClass, String field, String identifier) {
        super(generateMessage(entityClass, field, identifier));
    }

    public EntityNotFoundException(Class<?> entityClass, String field, String identifier, String field1, String identifier1) {
        super(generateMessage(entityClass, field, identifier, field1, identifier1));
    }

    private static String generateMessage(Class<?> entityClass, String field, String identifier) {
        return String.format("%s with %s [%s] does not exist.", entityClass.getSimpleName(), field, identifier);
    }

    private static String generateMessage(Class<?> entityClass, String field, String identifier, String field1, String identifier1) {
        return String.format("%s with %s [%s] and %s [%s] does not exist.", entityClass.getSimpleName(), field, identifier, field1, identifier1);
    }

}
