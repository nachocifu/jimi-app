package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.UserRolesDao;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class UserRolesJdbcDao implements UserRolesDao {

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;



    @Autowired
    public UserRolesJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_roles");
    }

    public void delete(User user, String role) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE userid = ? AND role = ?", user.getId(), role);
    }


    public void deleteAllFromUser(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE userid = ?", user.getId());
    }


    public void create(User user, String role) {

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userid", user.getId());
        args.put("role", role);

        jdbcInsert.execute(args);
    }
}
