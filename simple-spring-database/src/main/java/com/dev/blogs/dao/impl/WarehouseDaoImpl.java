package com.dev.blogs.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dev.blogs.dao.WarehouseDao;
import com.dev.blogs.model.Item;
import com.dev.blogs.model.Warehouse;

@Component
public class WarehouseDaoImpl implements WarehouseDao {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Warehouse> findAll() {
		return namedParameterJdbcTemplate.query(SQL_FIND_ALL, new RowMapper<Warehouse>() {
			public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getLong(Warehouse.ID_COLUMN));
				warehouse.setAddress(rs.getString(Warehouse.ADDRESS_COLUMN));
				return warehouse;
			}
		});
	}

	public List<Warehouse> findWarehousesWithItems() {
		return namedParameterJdbcTemplate.query(SQL_FIND_WAREHOUSES_WITH_ITEMS, new ResultSetExtractor<List<Warehouse>>() {
			public List<Warehouse> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Long, Warehouse> map = new HashMap<Long, Warehouse>();
				Warehouse warehouse = null;
				while (rs.next()) {
					Long id = rs.getLong(Warehouse.ID_COLUMN);
					warehouse = map.get(id);
					if (warehouse == null) {
						warehouse = new Warehouse();
						warehouse.setId(id);
						warehouse.setAddress(rs.getString(Warehouse.ADDRESS_COLUMN));
						map.put(id, warehouse);
					}
					Long itemId = rs.getLong(Item.ALIAS_ID_ITEM_COLUMN);
					if (itemId != null) {
						Item item = new Item();
						item.setId(itemId);
						item.setName(rs.getString(Item.NAME_COLUMN));
						item.setWarehouseId(rs.getLong(Item.WAREHOUSE_ID_COLUMN));
						warehouse.addItem(item);
					}
				}
				return new ArrayList<Warehouse>(map.values());
			}
		});
	}

	public Warehouse findById(Long id) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(WarehouseDao.ID_PARAMETER, id);
		Warehouse warehouse = null;
		try {
			warehouse = namedParameterJdbcTemplate.queryForObject(PARAMETERIZED_SQL_FIND_BY_ID, sqlParameterSource, new RowMapper<Warehouse>() {
				public Warehouse mapRow(ResultSet rs, int arg) throws SQLException {
					Warehouse warehouse = new Warehouse();
					warehouse.setId(rs.getLong(Warehouse.ID_COLUMN));
					warehouse.setAddress(rs.getString(Warehouse.ADDRESS_COLUMN));
					return warehouse;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			logger.info("Empty result");
		}
		return warehouse;
	}

	public Long insert(Warehouse warehouse) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(WarehouseDao.ADDRESS_PARAMETER, warehouse.getAddress());
		KeyHolder holder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(PARAMETERIZED_SQL_INSERT, sqlParameterSource, holder);
		return holder.getKey().longValue();
	}

	public void update(Warehouse warehouse) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(WarehouseDao.ID_PARAMETER, warehouse.getId());
		params.put(WarehouseDao.ADDRESS_PARAMETER, warehouse.getAddress());
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
		namedParameterJdbcTemplate.update(PARAMETERIZED_SQL_UPDATE, sqlParameterSource);		
	}

	public void delete(Warehouse warehouse) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(WarehouseDao.ID_PARAMETER, warehouse.getId());
		namedParameterJdbcTemplate.update(PARAMETERIZED_SQL_DELETE, sqlParameterSource);
	}
}