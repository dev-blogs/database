package com.dev.blogs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dev.blogs.DataSource;
import com.dev.blogs.dao.ItemDao;
import com.dev.blogs.model.Item;
import com.mysql.jdbc.Statement;

@Component
public class ItemDaoImpl implements ItemDao {
	@Autowired
	private DataSource dataSource;

	public List<Item> findAll() {
		List<Item> result = new ArrayList<Item>();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getLong(Item.ID_COLUMN));
				item.setName(rs.getString(Item.NAME_COLUMN));
				result.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
		return result;
	}

	public Item findById(Long id) {
		Item item = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				item = new Item();
				item.setId(rs.getLong(Item.ID_COLUMN));
				item.setName(rs.getString(Item.NAME_COLUMN));
				item.setWarehouse_id(rs.getLong(Item.WAREHOUSE_ID_COLUMN));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
		return item;
	}

	public void insert(Item item) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, item.getName());
			statement.setLong(2, item.getWarehouse_id());
			statement.execute();
			
			ResultSet generatedkeys = statement.getGeneratedKeys();
			if (generatedkeys.next()) {
				item.setId(generatedkeys.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
	}

	public void update(Item item) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
			statement.setString(1, item.getName());
			statement.setLong(2, item.getId());
			statement.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
	}

	public void delete(Item item) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
			statement.setLong(1, item.getId());
			statement.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
	}
}