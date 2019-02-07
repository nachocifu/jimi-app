package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	public Dish findById(long id) {
		return em.find(Dish.class, (int) id);
	}

	@Override
	public Collection<Dish> findAll(int pageSize, int offset) {
		return em.createQuery("from Dish order by name", Dish.class)
				.setFirstResult(offset)
				.setMaxResults(pageSize)
				.getResultList();
	}

	@Override
	public Collection<Dish> findDishesMissingStock(int pageSize, int offset) {
		return em.createQuery("from Dish as d where d.stock < d.minStock and d.discontinued = false", Dish.class)
				.setFirstResult(offset)
				.setMaxResults(pageSize)
				.getResultList();
	}

	@Override
	public Collection<Dish> findDiscontinuedDishes() {
		return em.createQuery("from Dish as d where d.discontinued = true order by name", Dish.class).getResultList();
	}

	@Override
	public int getTotalDishes() {
		Long query = em.createQuery("select count(*) from Dish", Long.class).getSingleResult();
		return query.intValue();
	}

	@Override
	public Collection<Dish> findAllAvailable(int pageSize, int offset) {
		return em.createQuery("from Dish as d where d.stock > 0 and d.discontinued = false order by name", Dish.class)
				.setFirstResult(offset)
				.setMaxResults(pageSize)
				.getResultList();
	}

	@Override
	public int getAllDishesWithStockLessThanLimit(int limit) {
		return em.createQuery("select count(*) from Dish as d where d.stock < :limit and d.discontinued = false", Long.class)
				.setParameter("limit", limit)
				.getSingleResult()
				.intValue();
	}
}
