package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collection;


@Repository
public class DishHibernateDao implements DishDao {

    @PersistenceContext(unitName = "testName", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;


    public Dish create(String name, float price, int stock) {
        final Dish dish = new Dish(name, price, stock);
        em.setFlushMode(FlushModeType.AUTO);
        em.persist(dish);
        return dish;
    }

    public int update(Dish dish) {
        //TODO: Falta tests de esto.
        em.merge(dish);
        return 1;
    }

    public Dish findById(long id) {
        return em.find(Dish.class, (int) id);
    }

    public Collection<Dish> findAll() {
        final TypedQuery<Dish> query = em.createQuery("from Dish", Dish.class);
        return query.getResultList();
    }
}
