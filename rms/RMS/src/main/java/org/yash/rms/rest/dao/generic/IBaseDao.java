package org.yash.rms.rest.dao.generic;

import java.util.List;

import org.yash.rms.exception.DaoRestException;
import org.yash.rms.rest.utils.QueryObject;


/**
 * <b>Data Access Object (DAO) Pattern</b>: Abstracts from any direct type of 
 * database or persistence mechanism. Provides specific operations without 
 * exposing details of the database.
 * <br/><br/>
 * <b>Responsibility</b> : Base Data Access Object definition including: 
 * <br/>
 * <br/>
 * <ul>
 * <li>Creating a new record in the underlying persistent storage</li> 
 * <li>Reading existing records from the underlying persistence 
 * storage through the finder methods.
 * </li> 
 * <li>Update an existing record in the underlying persistent storage</li> 
 * <li>Delete an existing record in the underlying persistent storage</li> 
 * <li>Delete all records from the underlying persistent storage</li> 
 * </ul>
 * 
 * @param <Pk> Entity primary key type
 * @param <Element> Entity type
 * 
 */
public interface IBaseDao <Pk, Entity> {
	/**
	 * Creates an entity in the persistent store. Returns the same entity after
	 * creation.
	 * 
	 * @param anEntity Entity to create
	 * 
	 */
	Entity create(Entity anEntity) throws DaoRestException;
	/**
	 * Updates an entity in the persistent store. Returns the same entity after
	 * updation is done.
	 * 
	 * @param anEntity Entity to update.
	 * 
	 */
	Entity update(Entity anEntity) throws DaoRestException;
	/**
	 * Delete the entity from the persistent store. Returns the same entity after
	 * deletion. 
	 * Note: Entity's primary key must be populated.
	 * @param anEntity
	 */
	void delete(Entity anEntity) throws DaoRestException;
	/**
	 * Delete the entity from the persistent store identified by its primary key. 
	 * 
	 * @param entityPk Primary key object of the entity.
	 */
	void deleteByPk(Pk entityPk) throws DaoRestException;
	/**
	 * Checks if an entity with similar attribute values exists.
	 * 
	 * @param anEntity represents entity which is referred for equality comparison. 
	 * @return returns true if one or more entities exists else returns false.
	 */
	boolean contains(Entity anEntity);
	
	/**
	 * Count all the entities filtered using the query object. Note this will not
	 * consider the pagination count.
	 * @param queryObject
	 * @return
	 */
	Long count(QueryObject queryObject);
	/**
	 * Find by primary key.
	 * Search for an entity of the specified class and primary key.
	 * If the entity instance is contained in the persistence context
	 * it is returned from there.
	 * 
	 * @param entityPk primary key.
	 * @return the found entity instance or null if the entity does
	 * not exist
	 * @throws IllegalArgumentException if the first argument does
	 * not denote an entity type or the second argument is
	 * is not a valid type for that entity's primary key or
	 * is null
	 */
	Entity findByPk(Pk entityPk) throws DaoRestException;
	/**
	 * @return returns all the entities in the persistent store.
	 */
	List<Entity> findAll() throws DaoRestException;
	/**
	 * Search for entities of the specified class based on the query pattern.
	 * If the entities are found in the persistence context it is returned 
	 * wrapped in a List.
	 * @param queryObject query pattern
	 * @param entityPk primary key.
	 * 
	 * @return the list of entities found or null if no such entity exists.
	 */
	List<Entity> find(QueryObject queryObject) throws DaoRestException;
}
