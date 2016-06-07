package com.dev.blogs.jdbc;

import java.sql.Types;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Component;

@Component
public class InsertItemProvider extends BatchSqlUpdate {
	public static final String TABLE_NAME = "items_providers";
	public static final String ITEM_ID_COLUMN = "item_id";
	public static final String PROVIDER_ID_COLUMN = "provider_id";
	
	public static final String ITEM_ID_PARAMETER = "item_id";
	public static final String PROVIDER_ID_PARAMETER = "provider_id";
	
	private static final String SQL_INSERT_ITEMS_PROVIDERS = "INSERT INTO " + TABLE_NAME
			+ " (" + ITEM_ID_COLUMN + ", " + PROVIDER_ID_COLUMN + ")"
			+ " VALUES (:" + ITEM_ID_PARAMETER + ", :" + PROVIDER_ID_PARAMETER + ")";
	
	private static final int BATCH_SIZE = 10;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		setSql(SQL_INSERT_ITEMS_PROVIDERS);
		setBatchSize(BATCH_SIZE);
		declareParameter(new SqlParameter(ITEM_ID_PARAMETER, Types.INTEGER));
		declareParameter(new SqlParameter(PROVIDER_ID_PARAMETER, Types.INTEGER));
	}
}