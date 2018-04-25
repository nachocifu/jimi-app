package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.DishDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DishJdbcDao implements DishDao {

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Dish> ROW_MAPPER = new RowMapper<Dish>() {
        public Dish mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Dish(rs.getString("name"), rs.getFloat("price"), rs.getInt("dishid"));
        }
    };

    @Autowired
    public DishJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("dishes")
                .usingGeneratedKeyColumns("dishid");
    }


    public Dish findById(long id) {
        final List<Dish> list = jdbcTemplate.query("SELECT * FROM dishes WHERE dishid = ?", ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Dish create(String name, float price) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("name", name);
        args.put("price", price);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new Dish(name, price, userId.longValue());
    }
}
