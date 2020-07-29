package com.tss.awesomehotel.model.interfaces;

/**
 * This interface is implemented by the entities to validate
 * the information prior sending to the persistance layer
 *
 * @param <T> The type of entity to validate
 */
public interface ModelInsertionValidator<T> extends ModelValidator<T>
{
    /**
     * This method validates the entity for insertion in the
     * persistance layer
     * @param entity The entity to check
     */
    void validateForInserting(T entity);

    /**
     * This method validates the ID of the entity to make
     * sure it complies with the requirements
     *
     * @param entity The entity to check
     */
    void validateID(T entity);
}
