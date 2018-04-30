package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.TableDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TableJdbcDao implements TableDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert jdbcInsert;
	
	private OrderJdbcDao orderJdbcDao;
	
	
	private static ResultSetExtractor<Collection<Table>> ROW_MAPPER = new ResultSetExtractor<Collection<Table>>() {
		
		public Collection<Table> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Map<Long, Table> tables = new HashMap<Long, Table>();
			
			while (rs.next()) {
				
				if (tables.containsKey(rs.getLong("tableid"))) {
					
					Table table = tables.get(rs.getLong("tableid"));
					
					table.getOrder().addDish(new Dish(
							rs.getString("dish.name"),
							rs.getFloat("price"),
							rs.getLong("dishid"),
							rs.getInt("stock"))
					);
					
					
				} else {
					
					Order order = null;
					
					if (rs.getLong("orderid") != 0) {
						
						order = new Order();
						
						if (rs.getLong("dishid") != 0) {
							
							order.addDish(new Dish(
									rs.getString("dish.name"),
									rs.getFloat("price"),
									rs.getLong("dishid"),
									rs.getInt("stock"))
							);
							
						}
						
						
					}
					
					Table table = new Table(
							rs.getString("table.name"),
							rs.getLong("tableid"),
							TableStatus.getTableStatus(rs.getInt("status")),
							order,
							rs.getInt("diners"));
					
					tables.put(table.getId(), table);
				}
			}
			
			return tables.values();
		}
	};
	
	@Autowired
	public TableJdbcDao(final DataSource ds) {
		
		orderJdbcDao = new OrderJdbcDao(ds);
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("dishes")
				.usingGeneratedKeyColumns("dishid");
	}
	
	public Table findById(long id) {
		final Collection<Table> list = jdbcTemplate.query(
				"SELECT * FROM tables AS tables " +
						"INNER JOIN orders ON tables.orderid = orders.orderid " +
						"INNER JOIN orders_items ON orders_items.orderid = orders.orderid " +
						"INNER JOIN dishes ON dishes.dishid = orders_items.dishid " +
						"WHERE tableid = ?", ROW_MAPPER, id);
		
		if (list.isEmpty()) {
			return null;
		}
		return list.iterator().next();
	}
	
	public void update(Table table) {
		
		orderJdbcDao.update(table.getOrder());
		
		jdbcTemplate.update("UPDATE tables SET (statusid, orderid, diners) = (?, ?, ?) WHERE tableid = ?",
				table.getStatus().getId(),
				table.getOrder().getId(),
				table.getDiners(),
				table.getId());
		
	}
	
	public Collection<Table> findAll() {
		return jdbcTemplate.query("SELECT * FROM tables", ROW_MAPPER);
	}
}
