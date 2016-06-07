package com.dev.blogs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;
import com.dev.blogs.model.Provider;

@Component
public class SelectAllProviders extends MappingSqlQuery<Provider> {
	private static final String SQL_SELECT_ALL_PROVIDERS = "SELECT * FROM " + Provider.TABLE_NAME;
	
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		setSql(SQL_SELECT_ALL_PROVIDERS);
	}

	@Override
	protected Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
		Provider provider = new Provider();
		provider.setId(rs.getLong(Provider.ID_COLUMN));
		provider.setName(rs.getString(Provider.NAME_COLUMN));
		return provider;
	}
}