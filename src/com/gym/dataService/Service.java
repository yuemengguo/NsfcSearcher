package com.gym.dataService;

import java.io.IOException;
import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


public class Service {

	private ApplicationContext ctx;
	
	public Service() {
		super();
	}
	private DataSource getBasicDataSource() throws IOException {
		//建立连接池资源
//		BasicDataSource dataSource = new BasicDataSource();
//	    //读取配置信息
//		Properties prop = new Properties();
//		InputStream in = new BufferedInputStream (Service.class.getClassLoader().getResourceAsStream("config.properties"));
//		prop.load(in);
//		DriverClassName = prop.getProperty("driverclassname");
//		Url = prop.getProperty("url");
//		
//		//进行配置
//		dataSource.setDriverClassName(DriverClassName);
//		//设置数据库访问的URL
//		dataSource.setUrl(Url);
//		//注意，在URL中已经设置过用户名和密码之后，就不需要再进行设置用户名和密码了，否则将会报错
//		
//		//设置数据库访问的用户名
////		dataSource.setUsername(this.Username);
////		//设置数据库访问密码
////		dataSource.setPassword(this.Password); 
//		
//		//最大连接数量
//		dataSource.setMaxActive(150);  
//		//最小空闲连接
//		dataSource.setMinIdle(5);
//		// 最大空闲连接
//		dataSource.setMaxIdle(200);
//		//初始化连接数量
//		dataSource.setInitialSize(30);
//		//连接被泄露是是否打印
//		dataSource.setLogAbandoned(true); 
//		// 是否自动回收超时连接
//		dataSource.setRemoveAbandoned(true); 
//		//设置超时等待时间
//		dataSource.setRemoveAbandonedTimeout(10); 
		ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");  
		 
		DataSource dataSource= ctx.getBean("dataSource", DataSource.class);
		return dataSource;
	}
	
	public JdbcTemplate getJdbcTemplate() throws IOException {
		//加载source
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getBasicDataSource());
		return jdbcTemplate;
	}
}
