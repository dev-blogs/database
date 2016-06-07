package com.dev.blogs.jdbc;

import java.sql.Types;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.stereotype.Component;

@Component
public class SFNameById extends SqlFunction<String> {
	private static final String CALL_STORED_FUNCTION = "SELECT getNameByid(?)";
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		setDataSource(dataSource);
		setSql(CALL_STORED_FUNCTION);
		declareParameter(new SqlParameter(Types.INTEGER));
		compile();
	}
}