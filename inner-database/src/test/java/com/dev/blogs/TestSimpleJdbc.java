package com.dev.blogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import com.dev.blogs.dao.ItemDao;
import com.dev.blogs.dao.WarehouseDao;
import com.dev.blogs.model.Item;
import com.dev.blogs.model.Warehouse;

@ContextConfiguration("classpath:spring-context.xml")
public class TestSimpleJdbc extends AbstractJUnit4SpringContextTests {
	@Autowired
	private WarehouseDao warehouseDao;
	@Autowired
	private ItemDao itemDao;
	
	@Test
	public void testWarehouseCRUD() {
		String testData = "test address";
		// Создать тестовый объект
		Warehouse testWarehouse = new Warehouse();
		testWarehouse.setAddress(testData);
		// Сохранить тестовый объект в базе данных
		warehouseDao.insert(testWarehouse);
		// Вытащить тестовый объект из базы данных
		Warehouse warehouseFromDb = warehouseDao.findById(testWarehouse.getId());
		// Сравнить вытащенный объект из базы данных с тестовым объектом
		assertEquals(testWarehouse.getAddress(), warehouseFromDb.getAddress());
		// Удалить тестовый объект в базе данных
		warehouseDao.delete(warehouseFromDb);
		// Найти удаленный объект в базе данных
		Warehouse removedWarehouse = warehouseDao.findById(warehouseFromDb.getId());
		// Сравнить вытащенный объект после удаления из базы данных на null
		assertNull(removedWarehouse);
	}
	
	@Test
	public void testItemCRUD() {
		String testData = "test address";
		// Создать тестовый объект
		Item testItem = new Item();
		testItem.setName(testData);
		testItem.setWarehouse_id(1l);
		// Сохранить тестовый объект в базе данных
		itemDao.insert(testItem);
		// Вытащить тестовый объект из базы данных
		Item itemFromDb = itemDao.findById(testItem.getId());
		// Сравнить вытащенный объект из базы данных с тестовым объектом
		assertEquals(testItem.getName(), itemFromDb.getName());
		// Удалить тестовый объект в базе данных
		itemDao.delete(itemFromDb);
		// Найти удаленный объект в базе данных
		Item removedItem = itemDao.findById(itemFromDb.getId());
		// Сравнить вытащенный объект после удаления из базы данных на null
		assertNull(removedItem);
	}
}