package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//@Repository
public class TableJdbcDao implements TableDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	private OrderDao orderJdbcDao;
	
	private static final String TABLE_TABLE_NAME = "tables";
	
	private ResultSetExtractor<Collection<Table>> ROW_MAPPER = new ResultSetExtractor<Collection<Table>>() {
		
		public Collection<Table> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Map<Long, Table> tables = new HashMap<Long, Table>();
			
			while (rs.next()) {
				// Get the order from the orderDao.
				Order order = orderJdbcDao.findById(rs.getLong("orderid"));
				
				// Create the table with the correct values.
				Table table = new Table(rs.getString("name"),
						rs.getLong("tableid"),
						TableStatus.getTableStatus(rs.getInt("statusid")),
						order);
				
				// Save it.
				tables.put(table.getId(), table);
			}
			
			return tables.values();
		}
	};
	
	@Autowired
	public TableJdbcDao(final DataSource ds) {
		
//		orderJdbcDao = new OrderJdbcDao(ds);
		jdbcTemplate = new JdbcTemplate(ds);
		
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName(TABLE_TABLE_NAME)
				.usingGeneratedKeyColumns("tableid");
		
	}
	
	public Table create(String name, TableStatus ts, Order order) throws TableWithNullOrderException {
		
		// In case the order is null or empty, throw exception.
		if (order == null || orderJdbcDao.findById(order.getId()) == null)
			throw new TableWithNullOrderException();
		
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("name", name);
		args.put("statusid", ts.ordinal());
		args.put("orderid", order.getId());
		final Number tableId = jdbcInsert.executeAndReturnKey(args);
		return findById(tableId.intValue());
	}
	
	public Table findById(final long id) {
		final Collection<Table> list = jdbcTemplate.query(
				"SELECT * FROM tables " +
						"WHERE tableid = ?", ROW_MAPPER, id);
		
		if (list.isEmpty()) {
			return null;
		}
		return list.iterator().next();
	}
	
	public void update(Table table) {
		
		orderJdbcDao.update(table.getOrder());
		
		jdbcTemplate.update("UPDATE tables SET (statusid, orderid, name) = (?, ?, ?) WHERE tableid = ?",
				table.getStatus().ordinal(),
				table.getOrder().getId(),
				table.getName(),
				table.getId());
		
	}
	
	public Collection<Table> findAll() {
		return jdbcTemplate.query("SELECT * FROM tables", ROW_MAPPER);
	}

    @Override
    public Collection<Table> findAll(QueryParams qp) {
        return null;
    }

    @Override
    public int getTotalTables() {
        return 0;
    }
}
