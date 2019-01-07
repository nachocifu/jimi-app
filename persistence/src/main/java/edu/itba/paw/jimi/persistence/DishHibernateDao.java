package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;


@Repository
public class DishHibernateDao implements DishDao {
	
	@PersistenceContext(unitName = "testName")
	private EntityManager em;
	
	
	@Override
	public Dish create(String name, float price, int stock) {
		final Dish dish = new Dish(name, price, stock);
		em.persist(dish);
		return dish;
	}
	
	@Override
	public int update(Dish dish) {
		em.merge(dish);
		return 1;
	}
	
	@Override
	public Collection<Dish> findAll() {
		final TypedQuery<Dish> query = em.createQuery("from Dish order by name", Dish.class);
		return query.getResultList();
	}
	
	@Override
	public Dish findById(long id) {
		return em.find(Dish.class, (int) id);
	}
	
	@Override
	public Collection<Dish> findAll(QueryParams qp) {
		final Query query = em.createQuery("from Dish order by name", Dish.class);
		query.setFirstResult(qp.getStartAt());
		query.setMaxResults(qp.getPageSize());
		
		return (Collection<Dish>) query.getResultList();
	}
	
	@Override
	public int getTotalDishes() {
		Long query = em.createQuery("select count(*) from Dish", Long.class).getSingleResult();
		return query.intValue();
	}
}
