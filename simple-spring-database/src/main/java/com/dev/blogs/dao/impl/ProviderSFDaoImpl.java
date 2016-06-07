package com.dev.blogs.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dev.blogs.dao.ProviderSFDao;
import com.dev.blogs.jdbc.SFNameById;

@Component
public class ProviderSFDaoImpl implements ProviderSFDao {
	@Autowired
	private SFNameById sfNameById;

	public String getNameById(Long id) {
		List<String> result = sfNameById.execute(id);
		try {
			return result.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}