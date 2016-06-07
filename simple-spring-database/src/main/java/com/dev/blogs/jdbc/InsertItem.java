package com.dev.blogs.jdbc;

import java.sql.Types;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Component;
import com.dev.blogs.model.Item;

@Component
public class InsertItem extends BatchSqlUpdate {
	public static final String NAME_PARAMETER = "name";
	private static final String SQL_INSERT_ITEMS = "INSERT INTO " + Item.TABLE_NAME
			+ " (" + Item.NAME_COLUMN + ")"
			+ " VALUES (:" + NAME_PARAMETER + ")";
	private static final int BATCH_SIZE = 10;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		setSql(SQL_INSERT_ITEMS);
		declareParameter(new SqlParameter(NAME_PARAMETER, Types.VARCHAR));
		setBatchSize(BATCH_SIZE);
		// Объявляем имя столбца для которого СУРБД генерирует ключ
		setGeneratedKeysColumnNames(new String[] { Item.ID_COLUMN });
		// Заставить лежащий в основе драйвер JDBC извлечь сгенерированный ключ
		setReturnGeneratedKeys(true);
	}
}