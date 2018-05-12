package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.UserDao;
import edu.itba.paw.jimi.interfaces.daos.UserRolesDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    private UserRolesDao userRolesDao;

    private final static ResultSetExtractor<Collection<User>> ROW_MAPPER = new ResultSetExtractor<Collection<User>>() {
        public Collection<User> extractData(ResultSet rs) throws SQLException, DataAccessException {

            Map<Integer, User> users = new HashMap<Integer, User>();

            while (rs.next()) {

                User user;
                int userid = rs.getInt("userid");

                if (users.containsKey(userid))
                    user = users.get(userid);
                else {
                    user = new User(rs.getString("username"), rs.getInt("userid"), rs.getString("password"));
                    user.setRoles(new LinkedHashSet<String>());
                }

                if (rs.getString("role") != null && !rs.getString("role").equals(""))
                    user.getRoles().add(rs.getString("role"));


                users.put(userid, user);

            }
            return users.values();
        }
    };

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        userRolesDao = new UserRolesJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("userid");
    }



    public User findById(long id) {
        final Collection<User> list = jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN user_roles ON (user_roles.userid = users.userid) WHERE users.userid = ?", ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.iterator().next();
    }

    public Collection<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN user_roles ON (user_roles.userid = users.userid)", ROW_MAPPER);
    }

    public User create(final String username, final String password, final Set<String> roles) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("username", username);
        args.put("password", password);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        User user =  new User(username, userId.longValue(), password);

        if (roles != null) {
            for (String role : roles)
                userRolesDao.create(user, role);

            user.setRoles(roles);
        } else {
            user.setRoles(new HashSet<String>());
        }



        return user;
    }

    public void update(User user) {

        jdbcTemplate.update("UPDATE users SET (username, password) = (?, ?) WHERE userid = ?",
                user.getUsername(), user.getPassword(), user.getId());

        if (user.getRoles() != null) {
            //First we remove all existing roles from user.
            userRolesDao.deleteAllFromUser(user);
            for (String role : user.getRoles()) {
                userRolesDao.create(user, role);
            }
        }

    }

    public User findByUsername(String username) {
        Collection<User> users = jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN user_roles ON (user_roles.userid = users.userid) WHERE username = ?", ROW_MAPPER, username);
        if (users.isEmpty()) {
            return null;
        }
        return users.iterator().next();
    }

}
