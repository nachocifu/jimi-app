package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.UserDao;
import edu.itba.paw.jimi.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext(unitName = "testName", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public User findById(long id) {
        return em.find(User.class, id);
    }

    public Collection<User> findAll() {
        final TypedQuery<User> query = em.createQuery("from User", User.class);
        return query.getResultList();
    }

    public User create(String username, String password, Set<String> roles) {
        final User usr = new User(username,password);
        if(roles != null){
            usr.setRoles(roles);
        } else {
            HashSet<String> rol = new HashSet<String>();
            usr.setRoles(rol);
        }
        em.persist(usr);
        return usr;

    }

    public User findByUsername(String username) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> l = query.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }

    public void update(User user) {
        em.merge(user);
    }
}
