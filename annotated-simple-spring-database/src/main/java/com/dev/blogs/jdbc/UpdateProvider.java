package com.dev.blogs.jdbc;

import java.sql.Types;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Component;
import com.dev.blogs.model.Provider;

@Component
public class UpdateProvider extends SqlUpdate {
	public static final String ID_PARAMETER = "id";
	public static final String NAME_PARAMETER = "name";
	
	private static final String PARAMETERIZED_SQL_UPDATE_PROVIDER = "UPDATE "
			+ Provider.TABLE_NAME
			+ " SET " + Provider.NAME_COLUMN + " = :" + NAME_PARAMETER
			+ " WHERE " + Provider.ID_COLUMN + " = :" + ID_PARAMETER;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		setSql(PARAMETERIZED_SQL_UPDATE_PROVIDER);
		declareParameter(new SqlParameter(ID_PARAMETER, Types.INTEGER));
		declareParameter(new SqlParameter(NAME_PARAMETER, Types.VARCHAR));
	}
}