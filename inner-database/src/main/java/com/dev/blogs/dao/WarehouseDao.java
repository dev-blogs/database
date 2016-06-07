package com.dev.blogs.dao;

import java.util.List;
import com.dev.blogs.model.Warehouse;

public interface WarehouseDao {
	public static final String SQL_FIND_ALL = "select * from " + Warehouse.TABLE_NAME;
	public static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " where " + Warehouse.ID_COLUMN + " = ?";
	public static final String SQL_INSERT = "insert into " + Warehouse.TABLE_NAME + " (" + Warehouse.ADDRESS_COLUMN + ") values (?)";
	public static final String SQL_UPDATE = "update " + Warehouse.TABLE_NAME + " set " + Warehouse.ADDRESS_COLUMN +" = ? where " + Warehouse.ID_COLUMN + " = ?";
	public static final String SQL_DELETE = "delete from " + Warehouse.TABLE_NAME + " where " + Warehouse.ID_COLUMN + " = ?";	
	
	public List<Warehouse> findAll();
	public Warehouse findById(Long id);
	public void insert(Warehouse warehouse);
	public void update(Warehouse warehouse);
	public void delete(Warehouse warehouse);
}