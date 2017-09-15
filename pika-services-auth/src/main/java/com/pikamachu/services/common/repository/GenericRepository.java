package com.pikamachu.services.common.repository;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * The Class GenericRepository.
 *
 * @param <T>              the generic type
 */
// @Stateless
public class GenericRepository<T> implements Serializable {

	/** Default serial. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Max _ results _ limit.
	 */
	protected Integer max_results_limit = 100;

	/** The log. */
	@Inject
	protected Logger log;

	/** The em. */
	@Inject
	protected EntityManager em;

	/** The event src. */
	@Inject
	protected Event<T> eventSrc;

	/**
	 * Find by id.
	 *
	 * @param obj the obj
	 * @param id the id
	 * @return the t
	 */
	public T findById(Class<T> obj, String id) {
		return em.find(obj, id);
	}

	/**
	 * Find all.
	 *
	 * @param obj the obj
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 */
	public List<T> findAll(Class<T> obj, int offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(obj);
		Root<T> pictureQuestion = criteria.from(obj);
		criteria.select(pictureQuestion);
		return em.createQuery(criteria)
        		.setFirstResult(offset)
        		.setMaxResults(limit)
        		.getResultList();
	}

	/**
	 * Find all.
	 *
	 * @param obj the obj
	 * @return the list
	 */
	public List<T> findAll(Class<T> obj) {
		return findAll(obj, 0, max_results_limit);
	}

	/**
	 * Find by property.
	 *
	 * @param clazz the clazz
	 * @param property the property
	 * @param value the value
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 */
	public List<T> findByProperty(Class<T> clazz, String property, Object value, int offset, int limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(clazz);
        Root<T> from = criteria.from(clazz);
        criteria.select(from).where(cb.equal(from.get(property), value));
        return em.createQuery(criteria)
        		.setFirstResult(offset)
        		.setMaxResults(limit)
        		.getResultList();
    }

	/**
	 * Find by property.
	 *
	 * @param clazz the clazz
	 * @param property the property
	 * @param value the value
	 * @return the list
	 */
	public List<T> findByProperty(Class<T> clazz, String property, Object value) {
		return findByProperty(clazz, property, value, 0, max_results_limit);
	}

	/**
	 * Save.
	 *
	 * @param entity the entity
	 */
	public void save(T entity) {
		if (log != null && log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "saving " + entity.getClass().getCanonicalName() + ".");
		}

		em.persist(entity);
		eventSrc.fire(entity);
	}

	/**
	 * Update.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void update(T entity) {
		if (log != null && log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "updating " + entity.getClass().getCanonicalName() + ".");
		}
		em.merge(entity);
		eventSrc.fire(entity);
	}

	/**
	 * Delete.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void delete(T entity) {
		if (log != null && log.isLoggable(Level.FINE)) {
			log.log(Level.FINE, "deleting " + entity.getClass().getCanonicalName() + ".");
		}

		em.remove(entity);
		eventSrc.fire(entity);
	}

}
