package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.models.User;
import edu.itba.paw.jimi.interfaces.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    public User findById(int id) {
        return null;
    }
}
