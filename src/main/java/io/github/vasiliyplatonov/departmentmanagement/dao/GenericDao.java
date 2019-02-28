package io.github.vasiliyplatonov.departmentmanagement.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Unified interface for managing persistent state of any domain
 *
 * @param <T> type of domain
 * @param <ID> type of primary key
 */
public interface GenericDao<T, ID extends Serializable> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @return the saved entity
     * @throws NullPointerException if {@code entity} is {@code null}
     */
    T save(T entity);

    /**
     * Saves all given entities.
     *
     * @return count of the saved entities
     * @throws NullPointerException in case the given list of entities is {@code null}
     */
    int save(List<T> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@code null}.
     * @return the entity with the given id or {@code null} if none found
     * @throws NullPointerException if {@code id} is {@code null}
     */
    T findOne(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws NullPointerException if {@code id} is {@code null}
     */
    boolean exists(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<T> findAll();


    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    int count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws NullPointerException if {@code id} is {@code null}
     */
    void delete(ID id);


    /**
     * Updates the entities with the given id
     *
     * @param entity  must not be {@literal null}
     * @throws NullPointerException if {@code entity} is {@code null}
     *
     * */
    void update(T entity);
}

