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
import com.dev.blogs.dao.WarehouseDao;
import com.dev.blogs.model.Warehouse;
import com.mysql.jdbc.Statement;

@Component
public class WarehouseDaoImpl implements WarehouseDao {
	@Autowired
	private DataSource dataSource;
	
	public List<Warehouse> findAll() {
		List<Warehouse> result = new ArrayList<Warehouse>();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getLong("id"));
				warehouse.setAddress(rs.getString("address"));
				result.add(warehouse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
		return result;
	}
	
	public Warehouse findById(Long id) {
		Warehouse warehouse = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				warehouse = new Warehouse();
				warehouse.setId(rs.getLong("id"));
				warehouse.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
		return warehouse;
	}

	public void insert(Warehouse warehouse) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, warehouse.getAddress());
			statement.execute();
			
			ResultSet generatedkeys = statement.getGeneratedKeys();
			if (generatedkeys.next()) {
				warehouse.setId(generatedkeys.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
	}

	public void update(Warehouse warehouse) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
			statement.setString(1, warehouse.getAddress());
			statement.setLong(2, warehouse.getId());
			statement.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
	}

	public void delete(Warehouse warehouse) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
			statement.setLong(1, warehouse.getId());
			statement.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeConnection(connection);
		}
	}
}