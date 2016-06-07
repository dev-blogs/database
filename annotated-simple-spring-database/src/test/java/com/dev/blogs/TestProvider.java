package com.dev.blogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.dev.blogs.config.AppConfig;
import com.dev.blogs.dao.ItemDao;
import com.dev.blogs.dao.ProviderDao;
import com.dev.blogs.dao.ProviderSFDao;
import com.dev.blogs.model.Item;
import com.dev.blogs.model.Provider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class, loader=AnnotationConfigContextLoader.class)
public class TestProvider {
	@Autowired
	private ProviderDao providerDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ProviderSFDao providerSFDao;
	
	/**
	 * Тест хранимой функции
	 */
	@Test
	public void testStoredFunction() {
		String name = providerSFDao.getNameById(1l);
		assertEquals(name, "Vasya");
	}
	
	@Test
	public void testInsertProviderWithItems() {
		// Создать тестовый объект Provider
		Provider provider = new Provider();
		provider.setName("test provider");
		
		// Создать тестовый объекты Item
		Set<Item> items = new HashSet<Item>();
		items.add(new Item("item1"));
		items.add(new Item("item2"));
		items.add(new Item("item3"));
		items.add(new Item("item4"));
		items.add(new Item("item5"));
		
		provider.setItems(items);
		
		// Сохранить тестовый объект Provider в базе данных
		Long id = providerDao.insertWithItems(provider);
		// Вытащить тестовый объект из базы данных
		Provider providerFromDb = providerDao.findById(id);
		
		// Сравнить вытащенный объект из базы данных с тестовым объектом
		assertEquals(provider, providerFromDb);
		
		// Вытащить из вытащенного объекта Provider набор объектов Item
		Set<Item> itemsFromDb = provider.getItems();
		
		// Пройти по каждому объекту Item и сравнить с исходными
		for (Item itemFromDb : itemsFromDb) {
			// Проверить входит ли вытащенный объект item из базы данных в набор items тестового объекта Provider
			assertEquals(items.contains(itemFromDb), true);
			// Удалить тестовый объект Item из базы данных
			itemDao.delete(itemFromDb);
		}
		
		// Удалить тестовый объект Provider из базы данных
		providerDao.delete(providerFromDb);
	}		
	
	@Test
	public void testInsertFind() {		
		// Создать тестовый объект
		Provider provider = new Provider();
		provider.setName("test provider");

		// Сохранить тестовый объект в базе данных
		Long id = providerDao.insert(provider);
		// Вытащить тестовый объект из базы данных
		Provider providerFromDb = providerDao.findById(id);
		
		// Сравнить вытащенный объект из базы данных с тестовым объектом
		assertEquals(provider, providerFromDb);
		
		// Удалить тестовый объект из базы данных
		providerDao.delete(providerFromDb);		
	}
	
	@Test
	public void testInsertFindUpdate() {
		// Создать тестовый объект
		Provider provider = new Provider();
		provider.setName("test provider");
		
		// Сохранить тестовый объект в базе данных
		Long id = providerDao.insert(provider);
		
		// Вытащить тестовый объект из базы данных
		Provider providerFromDb = providerDao.findById(id);
		
		// Обновить в вытащенном объекте поле name
		providerFromDb.setName("other provider");
		// Записать обновленый тестовый объект в базу данных
		providerDao.update(providerFromDb);
		
		// После обновления повторно вытащить тестовый объект из базы данных
		Provider updatedProviderFromDb = providerDao.findById(providerFromDb.getId());
		
		// Сравнить тестовый обновленный объект с повторно вытащенным
		assertEquals(providerFromDb, updatedProviderFromDb);
		
		// Удалить тестовый объект из базы данных
		providerDao.delete(updatedProviderFromDb);
	}
	
	@Test
	public void testDelete() {
		// Создать тестовый объект
		Provider provider = new Provider();
		provider.setName("test provider");
		
		// Сохранить тестовый объект в базе данных
		Long id = providerDao.insert(provider);
		// Вытащить тестовый объект из базы данных
		Provider providerFromDb = providerDao.findById(id);
		// Удалить тестовый объект из базы данных
		providerDao.delete(providerFromDb);
		// Найти удаленный объект в базе данных
		Provider providerAfterDeleting = providerDao.findById(providerFromDb.getId());
		
		// Сравнить вытащенный объект после удаления из базы данных на null
		assertNull(providerAfterDeleting);
	}
}