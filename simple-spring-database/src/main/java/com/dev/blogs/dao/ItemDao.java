package com.dev.blogs.dao;

import java.util.List;
import com.dev.blogs.model.Item;

public interface ItemDao {
	public static final String COMMA = ", ";
	
	public static final String SQL_FIND_ALL = "SELECT * FROM " + Item.TABLE_NAME;
	public static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE " + Item.ID_COLUMN + " = ?";
	public static final String SQL_FIND_BY_NAME = SQL_FIND_ALL + " WHERE " + Item.NAME_COLUMN + " = ?";
	public static final String SQL_INSERT = "INSERT INTO " + Item.TABLE_NAME + " (" + Item.NAME_COLUMN + ", " + Item.WAREHOUSE_ID_COLUMN + ") VALUES (?, ?)";
	public static final String SQL_UPDATE = "UPDATE " + Item.TABLE_NAME + " SET " + Item.NAME_COLUMN + " = ?" + COMMA + Item.WAREHOUSE_ID_COLUMN + " = ?" + " WHERE " + Item.ID_COLUMN + " = ?";
	public static final String SQL_DELETE = "DELETE FROM " + Item.TABLE_NAME + " WHERE " + Item.ID_COLUMN + " = ?";
	
	public List<Item> findAll();
	public Item findById(Long id);
	public Item findByName(String name);
	public Long insert(Item item);
	public void update(Item item);
	public void delete(Item item);
}