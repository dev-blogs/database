package com.dev.blogs.dao;

import java.util.List;
import com.dev.blogs.model.Provider;

public interface ProviderDao {
	public List<Provider> findAll();
	public Provider findById(Long id);
	public Long insert(Provider provider);
	public Long insertWithItems(Provider provider);
	public void update(Provider provider);
	public void delete(Provider provider);
}