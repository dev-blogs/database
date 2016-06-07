package com.dev.blogs.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.dev.blogs.dao.ProviderDao;
import com.dev.blogs.jdbc.DeleteProvider;
import com.dev.blogs.jdbc.InsertItem;
import com.dev.blogs.jdbc.InsertItemProvider;
import com.dev.blogs.jdbc.InsertProvider;
import com.dev.blogs.jdbc.SelectAllProviders;
import com.dev.blogs.jdbc.SelectProviderById;
import com.dev.blogs.jdbc.UpdateProvider;
import com.dev.blogs.model.Item;
import com.dev.blogs.model.Provider;

@Component
public class ProviderDaoImpl implements ProviderDao {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SelectAllProviders selectAllProviders;
	@Autowired
	private SelectProviderById selectProviderById;
	@Autowired
	private InsertProvider insertProvider;
	@Autowired
	private InsertItem insertItem;
	@Autowired
	private InsertItemProvider insertItemProvider;
	@Autowired
	private UpdateProvider updateProvider;
	@Autowired
	private DeleteProvider deleteProvider;
	
	public List<Provider> findAll() {
		return selectAllProviders.execute();
	}

	public Provider findById(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(SelectProviderById.ID_PARAMETER, id);
		Provider provider = null;
		try {
			provider = selectProviderById.executeByNamedParam(paramMap).get(0);
		} catch (IndexOutOfBoundsException e) {
			logger.info("Provider with id " + id + " was not found");
		}
		return provider;
	}

	public Long insert(Provider provider) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(InsertProvider.NAME_PARAMETER, provider.getName());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertProvider.updateByNamedParam(paramMap, keyHolder);
		provider.setId(keyHolder.getKey().longValue());
		logger.info("New provider inserted with id: " + provider.getId());
		return provider.getId();
	}

	public Long insertWithItems(Provider provider) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(InsertProvider.NAME_PARAMETER, provider.getName());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		insertProvider.updateByNamedParam(paramMap, keyHolder);
		provider.setId(keyHolder.getKey().longValue());
		logger.info("New provider inserted with id: " + provider.getId());
		
		// Пакетна вставка для items
		Set<Item> items = provider.getItems();
		if (items != null) {
			for (Item item : items) {
				paramMap = new HashMap<String, Object>();
				paramMap.put(InsertItem.NAME_PARAMETER, item.getName());
				keyHolder = new GeneratedKeyHolder();
				insertItem.updateByNamedParam(paramMap, keyHolder);
				item.setId(keyHolder.getKey().longValue());

				paramMap = new HashMap<String, Object>();
				paramMap.put(InsertItemProvider.ITEM_ID_PARAMETER, item.getId());
				paramMap.put(InsertItemProvider.PROVIDER_ID_PARAMETER, provider.getId());
				insertItemProvider.updateByNamedParam(paramMap);
			}
		}
		insertItem.flush();
		insertItemProvider.flush();
		return provider.getId();
	}

	public void update(Provider provider) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(UpdateProvider.ID_PARAMETER, provider.getId());
		paramMap.put(UpdateProvider.NAME_PARAMETER, provider.getName());
		updateProvider.updateByNamedParam(paramMap);
		logger.info("Existing provider updated with id: " + provider.getId());
	}

	public void delete(Provider provider) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(DeleteProvider.ID_PARAMETER, provider.getId());
		deleteProvider.updateByNamedParam(paramMap);
		logger.info("Provider with id " + provider.getId() + " will be removed");
	}
}