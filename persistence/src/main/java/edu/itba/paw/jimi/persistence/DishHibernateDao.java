package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;


@Repository
public class DishHibernateDao implements DishDao {

    @PersistenceContext(unitName = "testName")
    private EntityManager em;


    public Dish create(String name, float price, int stock) {
//        final Dish dish = new Dish(name, price, stock);
//        em.persist(dish);
        return new Dish(name, price, 1, stock);
    }

    public int update(Dish dish) {
        return 0;
    }

    public Dish findById(long id) {
//        return em.find(Dish.class, id);
        return null;
    }

    public Collection<Dish> findAll() {
        return null;
    }
}
