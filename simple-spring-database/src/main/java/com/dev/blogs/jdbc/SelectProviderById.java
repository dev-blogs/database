package com.dev.blogs.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;
import com.dev.blogs.model.Provider;

@Component
public class SelectProviderById extends MappingSqlQuery<Provider> {
	public static final String ID_PARAMETER = "id";
	private static final String SQL_SELECT_ALL_PROVIDERS_BY_ID = "SELECT * FROM " + Provider.TABLE_NAME + " WHERE " + Provider.ID_COLUMN + " = :" + ID_PARAMETER;
	
	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		setSql(SQL_SELECT_ALL_PROVIDERS_BY_ID);
		declareParameter(new SqlParameter(ID_PARAMETER, Types.INTEGER));
	}

	@Override
	protected Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
		Provider provider = new Provider();
		provider.setId(rs.getLong(Provider.ID_COLUMN));
		provider.setName(rs.getString(Provider.NAME_COLUMN));
		return provider;
	}
}