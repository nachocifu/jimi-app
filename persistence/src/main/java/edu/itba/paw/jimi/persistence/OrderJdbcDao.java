package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.OrderDao;
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
import java.util.*;

@Repository
public class OrderJdbcDao implements OrderDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert jdbcInsert;
	
	private OrderItemJdbcDao orderItemJdbcDao;
	
	/* es la herramienta que me extrae las entidades (no va row por row) */
	private static ResultSetExtractor<Collection<Order>> ROW_MAPPER = new ResultSetExtractor<Collection<Order>>() {
		public Collection<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Map<Integer, Order> orders = new HashMap<Integer, Order>();
			
			while (rs.next()) {
				
				if (orders.containsKey(rs.getInt("orderid"))) {
					orders.get(rs.getInt("orderid"))
							.addDish(new Dish(
									rs.getString("name"),
									rs.getFloat("price"),
									rs.getInt("dishid"),
									rs.getInt("stock"))
							);
				} else {
					Order order = new Order(rs.getInt("orderid"));
					order.addDish(new Dish(
							rs.getString("name"),
							rs.getFloat("price"),
							rs.getInt("dishid"),
							rs.getInt("stock"))
					);
					
					orders.put(rs.getInt("orderid"), order);
				}
			}
			return orders.values();
		}
	};
	
	@Autowired
	public OrderJdbcDao(final DataSource ds) {
		orderItemJdbcDao = new OrderItemJdbcDao(ds);
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("orders")
				.usingGeneratedKeyColumns("orderid");
	}
	
	
	public Order findById(long id) {
		final Collection<Order> list = jdbcTemplate.query("SELECT * FROM orders NATURAL JOIN orders_items WHERE orderid = ?", ROW_MAPPER, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.iterator().next();
	}
	
	public Order create() {
		final Map<String, Object> args = new HashMap<String, Object>();
		final Number orderId = jdbcInsert.executeAndReturnKey(args);
		return new Order(orderId.longValue());
	}
	
	public Boolean update(Order order) {
		
		for (Map.Entry<Dish, Integer> entry : order.getDishes().entrySet()) {
			orderItemJdbcDao.createOrUpdate(order, entry.getKey(), entry.getValue());
		}
		
		return true;
	}
	
}
