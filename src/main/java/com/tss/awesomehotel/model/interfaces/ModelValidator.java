package com.tss.awesomehotel.model.interfaces;

/**
 * This interface will be implemented by all
 * the classes wanting to validate model entities
 *
 * @param <T> The type of entity to validate
 */
public interface ModelValidator<T>
{
    /**
     * This method is the one that performs the
     * validation check
     *
     * @param entity The entity to validate
     */
    void validate(T entity);

}
