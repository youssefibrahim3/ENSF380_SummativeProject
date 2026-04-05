package edu.ucalgary.oop;

import java.util.List;

/**
 * Generic Data Access Object interface.
 * Defines common CRUD operations for all entity types.
 * 
 * @param <T> The entity type
 * @param <ID> The type of the entity's identifier
 * 
 */
public interface GenericDAO<T, ID> {
    /**
     * Retrieves all entities from the database.
     * 
     * @return List of all entities
     */
    List<T> getAll();
    
    /**
     * Retrieves a specific entity by its ID.
     * 
     * @param id The ID of the entity to retrieve
     * @return The entity with the specified ID, or null if not found
     */
    T getById(ID id);
    
    /**
     * Inserts a new entity into the database.
     * 
     * @param entity The entity to insert
     * @return true if insertion was successful
     */
    boolean insert(T entity);
    
    /**
     * Updates an existing entity in the database.
     * 
     * @param entity The entity with updated information
     * @return true if update was successful
     */
    boolean update(T entity);
    
    /**
     * Deletes an entity from the database.
     * 
     * @param id The ID of the entity to delete
     * @return true if deletion was successful
     */
    boolean delete(ID id);
}
