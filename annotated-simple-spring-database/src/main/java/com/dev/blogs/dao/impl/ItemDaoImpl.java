package com.dev.blogs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dev.blogs.dao.ItemDao;
import com.dev.blogs.model.Item;

@Component
public class ItemDaoImpl implements ItemDao {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Item> findAll() {
		return jdbcTemplate.query(SQL_FIND_ALL, new RowMapper<Item>() {
			public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item = new Item();
				item.setId(rs.getLong(Item.ID_COLUMN));
				item.setName(rs.getString(Item.NAME_COLUMN));
				item.setWarehouseId(rs.getLong(Item.WAREHOUSE_ID_COLUMN));
				return item;
			}
		});
	}

	public Item findById(Long id) {
		Item item = null;
		try {
			item = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new RowMapper<Item>() {
				public Item mapRow(ResultSet rs, int arg) throws SQLException {
					Item item = new Item();
					item.setId(rs.getLong(Item.ID_COLUMN));
					item.setName(rs.getString(Item.NAME_COLUMN));
					item.setWarehouseId(rs.getLong(Item.WAREHOUSE_ID_COLUMN));
					return item;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			logger.info("Empty result");
		}
		return item;
	}

	public Item findByName(String name) {
		Object[] objects = new Object[] { name };
		Item item = null;
		try {			
			item = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, objects, new RowMapper<Item>() {
				public Item mapRow(ResultSet rs, int arg) throws SQLException {
					Item item = new Item();
					item.setId(rs.getLong(Item.ID_COLUMN));
					item.setName(rs.getString(Item.NAME_COLUMN));
					item.setWarehouseId(rs.getLong(Item.WAREHOUSE_ID_COLUMN));
					return item;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			logger.info("Empty result");
		}
		return item;
	}
	
	public Long insert(final Item item) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { Item.ID_COLUMN });
                ps.setString(1, item.getName());
                ps.setLong(2, item.getWarehouseId());
                return ps;
            }
        };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(preparedStatementCreator, holder);
		return holder.getKey().longValue();
	}

	public void update(Item item) {
		Object[] objects = new Object[] {
			item.getName(),
			item.getWarehouseId(),
			item.getId()
		};
		jdbcTemplate.update(SQL_UPDATE, objects);
	}

	public void delete(Item item) {
		Object[] objects = new Object[] {
			item.getId()
		};
		jdbcTemplate.update(SQL_DELETE, objects);
	}
}