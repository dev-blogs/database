package com.dev.blogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import com.dev.blogs.dao.ItemDao;
import com.dev.blogs.model.Item;

@ContextConfiguration("classpath:spring/spring-context.xml")
public class TestItem extends AbstractJUnit4SpringContextTests {
	@Autowired
	private ItemDao itemDao;
	
	@Test
	public void testInsertFind() {
		// Создать тестовый объект
		Item item = new Item();
		item.setName("zheka");
		item.setWarehouseId(1l);
		
		// Сохранить тестовый объект в базе данных
		Long id = itemDao.insert(item);
		// Вытащить тестовый объект из базы данных
		Item itemFromDb = itemDao.findById(id);
		
		// Сравнить вытащенный объект из базы данных с тестовым объектом
		assertEquals(item, itemFromDb);
		
		// Удалить тестовый объект из базы данных
		itemDao.delete(itemFromDb);
	}
	
	@Test
	public void testInsertFindUpdate() {
		// Создать тестовый объект
		Item item = new Item();
		item.setName("zheka");
		item.setWarehouseId(1l);
		
		// Сохранить тестовый объект в базе данных
		Long id = itemDao.insert(item);
		
		// Вытащить тестовый объект из базы данных
		Item itemFromDb = itemDao.findById(id);
		
		// Обновить в вытащенном объекте поле name
		itemFromDb.setName("vasya");
		// Записать обновленый тестовый объект в базу данных
		itemDao.update(itemFromDb);
		// После обновления повторно вытащить тестовый объект из базы данных
		Item updatedItemFromDb = itemDao.findById(itemFromDb.getId());
		
		// Сравнить тестовый обновленный объект с повторно вытащенным
		assertEquals(itemFromDb, updatedItemFromDb);
		
		// Удалить тестовый объект из базы данных
		itemDao.delete(updatedItemFromDb);
	}
	
	@Test
	public void testDelete() {
		// Создать тестовый объект
		Item item = new Item();
		item.setName("test message");
		item.setWarehouseId(1l);
		
		// Сохранить тестовый объект в базе данных
		Long id = itemDao.insert(item);
		// Вытащить тестовый объект из базы данных
		Item itemToDelete = itemDao.findById(id);
		// Удалить тестовый объект из базы данных
		itemDao.delete(itemToDelete);
		// Найти удаленный объект в базе данных
		Item itemAfterDeleting = itemDao.findById(id);
		
		// Сравнить вытащенный объект после удаления из базы данных на null
		assertNull(itemAfterDeleting);
	}
}