package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//@Repository
public class OrderJdbcDao implements OrderDao {
	
	private static final String ORDER_TABLE_NAME = "orders";
	
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert jdbcInsert;
	
	private OrderItemJdbcDao orderItemJdbcDao;
	
	// This row mapper creates the order object from the DB and then fills the Map<Dish, Integer>
	// with the corresponding dishes and amount of said dish.
	private static ResultSetExtractor<Collection<Order>> ROW_MAPPER = new ResultSetExtractor<Collection<Order>>() {
		public Collection<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Map<Integer, Order> orders = new HashMap<Integer, Order>();
			
			while (rs.next()) {
				
				Order order;
				int orderid = rs.getInt("orderid");
				
				// Does the order already contain stuff?
				if (orders.containsKey(orderid))
					order = orders.get(orderid);
				else
					order = new Order(orderid, rs.getTimestamp("openedAt"),
							rs.getTimestamp("closedAt"),
							OrderStatus.getOrderStatus(rs.getInt("statusid")),
							rs.getInt("diners"),
							rs.getFloat("total"));
				
				// Add the stuff.
				if (rs.getString("name") != null && !rs.getString("name").equals(""))
					// It is a left outer join, so empty orders can get retrieved but we need to check.
					order.setDish(new Dish(
									rs.getString("name"),
									rs.getFloat("price"),
									rs.getInt("dishid"),
									rs.getInt("stock"))
							, rs.getInt("quantity"));
				
				
				// The id is the same, so we can overwrite if already in the map.
				orders.put(orderid, order);
				
			}
			return orders.values();
			
		}
	};
	
	@Autowired
	public OrderJdbcDao(final DataSource ds) {
		orderItemJdbcDao = new OrderItemJdbcDao(ds);
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName(ORDER_TABLE_NAME)
				.usingGeneratedKeyColumns("orderid");
	}
	
	public Order findById(final long id) {
		// This SQL sentence joins the orders, orders_items and dishes tables to one.
		// The orders table is on the outer side of the join so it returns orders with no dishes inside.
		final Collection<Order> list = jdbcTemplate.query(
				"SELECT * " +
						"FROM (SELECT orders.orderid, dishid, quantity, statusid, openedAt, closedAt, diners, total " +
						"FROM orders  LEFT OUTER JOIN orders_items " +
						"ON (orders.orderid = orders_items.orderid))" +
						"as o LEFT OUTER JOIN dishes " +
						"ON (o.dishid = dishes.dishid) " +
						"WHERE o.orderid = ?"
				, ROW_MAPPER, id);
		
		if (list.isEmpty()) {
			return null;
		}
		return list.iterator().next();
	}
	
	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners, float total) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("statusid", status.ordinal());
		args.put("openedAt", openedAt);
		args.put("closedAt", closedAt);
		args.put("diners", diners);
		args.put("total", 0f);
		final Number orderId = jdbcInsert.executeAndReturnKey(args);
		return new Order(orderId.longValue(), openedAt, closedAt, status, diners, total);
	}
	
	public void update(Order order) {

		jdbcTemplate.update("UPDATE orders SET (statusid ,openedAt ,closedAt, diners, total) = (?, ?, ?, ?, ?) WHERE orderid = ?",
				order.getStatus().ordinal(), order.getOpenedAt(), order.getClosedAt(), order.getDiners(), order.getTotal(), order.getId());

		// If the map shows 0 in amount for a dish then we need to remove it from the DB.
		for (Map.Entry<Dish, Integer> entry : order.getDishes().entrySet()) {
			if (entry.getValue() != 0)
				orderItemJdbcDao.createOrUpdate(order, entry.getKey(), entry.getValue());
			else
				orderItemJdbcDao.delete(order, entry.getKey());
		}
	}

	public Collection<Order> findAll() {
		final Collection<Order> col = jdbcTemplate.query(
				"SELECT * " +
						"FROM (SELECT orders.orderid, dishid, quantity, statusid, openedAt, closedAt, diners, total " +
						"FROM orders  LEFT OUTER JOIN orders_items " +
						"ON (orders.orderid = orders_items.orderid))" +
						"as o LEFT OUTER JOIN dishes " +
						"ON (o.dishid = dishes.dishid) " +
						"WHERE o.statusid = ? ",
				ROW_MAPPER, OrderStatus.CLOSED.ordinal());
		return col;
	}

	public Map getMonthlyOrderTotal() {

//		final Map<YearMonth, Double> m = jdbcTemplate.query(
//				"SELECT EXTRACT(year from closedat) as yr, EXTRACT(month from closedat) as mon, SUM(total) as monthtotal " +
//						"FROM orders " +
//						"GROUP BY EXTRACT(year from closedat), EXTRACT(month from closedat)" +
//						"ORDER BY yr, mon",
//				rs -> {
//					Map<YearMonth, Double> mapRet = new HashMap<>();
//					while (rs.next()) {
//
//						System.out.println("monthtotal: \n" + rs.getDouble("monthtotal"));
//
//						System.out.println("rs: \n" + rs.);
//
//						mapRet.put(YearMonth.of(rs.getInt("yr"), rs.getInt("mon") + 1), rs.getDouble("monthtotal"));
//					}
//
//					System.out.println("mapRet: \n" + mapRet);
//
//					return mapRet;
//				});
//		return m;

		Map<YearMonth, Double> hardcodeado = new HashMap<>();
		hardcodeado.put(YearMonth.of(2018, 1), 100.0);
		hardcodeado.put(YearMonth.of(2018, 2), 150.0);
		hardcodeado.put(YearMonth.of(2018, 3), 130.0);

		return hardcodeado;
	}
	
}
