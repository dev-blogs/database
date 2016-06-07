package com.dev.blogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.dev.blogs.config.AppConfig;
import com.dev.blogs.dao.WarehouseDao;
import com.dev.blogs.model.Warehouse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class, loader=AnnotationConfigContextLoader.class)
public class TestWarehouse {
	@Autowired
	private WarehouseDao warehouseDao;	
	
	@Test
	public void testInsertFind() {
		// Создать тестовый объект
		Warehouse warehouse = new Warehouse();
		warehouse.setAddress("ul. Moskovskaya, 1");
		
		// Сохранить тестовый объект в базе данных
		Long id = warehouseDao.insert(warehouse);
		
		// Вытащить тестовый объект из базы данных
		Warehouse warehouseFromDb = warehouseDao.findById(id);
		
		// Сравнить вытащенный объект из базы данных с тестовым объектом
		assertEquals(warehouse, warehouseFromDb);
		
		// Удалить тестовый объект из базы данных
		warehouseDao.delete(warehouseFromDb);
	}
	
	@Test
	public void testInsertFindUpdateDelete() {
		// Создать тестовый объект
		Warehouse warehouse = new Warehouse();
		warehouse.setAddress("ul. Moskovskaya, 1");
		
		// Сохранить тестовый объект в базе данных
		Long id = warehouseDao.insert(warehouse);
		
		// Вытащить тестовый объект из базы данных
		Warehouse warehouseFromDb = warehouseDao.findById(id);
		
		//Обновить в вытащенном объекте поле address
		warehouseFromDb.setAddress("ul. Leningradskaya, 4");
		// Записать обновленый тестовый объект в базу данных
		warehouseDao.update(warehouseFromDb);
		
		// После обновления повторно вытащить тестовый объект из базы данных
		Warehouse updatedWarehouseFromDb = warehouseDao.findById(warehouseFromDb.getId());
		
		// Сравнить тестовый обновленный объект с повторно вытащенным
		assertEquals(warehouseFromDb, updatedWarehouseFromDb);
		
		// Удалить тестовый объект из базы данных
		warehouseDao.delete(updatedWarehouseFromDb);
	}
	
	@Test
	public void testInsertFindDelete() {
		// Создать тестовый объект
		Warehouse warehouse = new Warehouse();
		warehouse.setAddress("test message");
		
		// Сохранить тестовый объект в базе данных
		Long id = warehouseDao.insert(warehouse);
		// Вытащить тестовый объект из базы данных
		Warehouse warehouseToDelete = warehouseDao.findById(id);
		// Удалить тестовый объект из базы данных
		warehouseDao.delete(warehouseToDelete);
		// Найти удаленный объект в базе данных
		Warehouse warehouseAfterDeleting = warehouseDao.findById(id);
		
		// Сравнить вытащенный объект после удаления из базы данных на null
		assertNull(warehouseAfterDeleting);
	}
}