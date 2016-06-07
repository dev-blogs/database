package com.dev.blogs.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan(basePackages="com.dev.blogs")
public class AppConfig {
	
	@Bean
	public DataSource dataSource() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("src/main/resources/jdbc.properties");
			prop.load(input);
			
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setUrl(prop.getProperty("jdbc.url"));
			dataSource.setUsername(prop.getProperty("jdbc.login"));
			dataSource.setPassword(prop.getProperty("jdbc.password"));
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}
}