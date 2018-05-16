package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderItemDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * See OrderItemDao interface for context and uses of this implementation.
 */

@Repository
public class OrderItemJdbcDao implements OrderItemDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert jdbcInsert;
	
	private static ResultSetExtractor<Integer> SCALAR_MAPPER = new ResultSetExtractor<Integer>() {
		public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Integer count = 0;
			
			while (rs.next()) {
				
				count++;
			}
			return count;
		}
	};
	
	@Autowired
	public OrderItemJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("orders_items");
	}
	
	public void createOrUpdate(Order order, Dish dish, int quantity) {
		
		if (orderItemExists(order, dish))
			update(order, dish, quantity);
		else
			create(order, dish, quantity);
	}
	
	public void delete(Order order, Dish dish) {
		jdbcTemplate.update("DELETE FROM orders_items WHERE orderid = ? AND dishid = ?", order.getId(), dish.getId());
	}
	
	private Boolean orderItemExists(Order order, Dish dish) {
		final Integer count = jdbcTemplate.query("SELECT * FROM orders_items WHERE orderid = ? AND dishid = ?", SCALAR_MAPPER, order.getId(), dish.getId());
		
		return count > 0;
	}
	
	private void update(Order order, Dish dish, Integer quantity) {
		jdbcTemplate.update("UPDATE orders_items SET quantity = ? WHERE orderid = ? AND dishid = ?", quantity, order.getId(), dish.getId());
		
	}
	
	private void create(Order order, Dish dish, Integer quantity) {
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("orderid", order.getId());
		args.put("dishid", dish.getId());
		args.put("quantity", quantity);
		
		jdbcInsert.execute(args);
	}
}
