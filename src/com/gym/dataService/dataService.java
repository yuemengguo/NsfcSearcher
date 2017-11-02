package com.gym.dataService;

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class dataService {
	
	public static Service service = 
			new ClassPathXmlApplicationContext("/applicationContext.xml").getBean("service",Service.class);
	/**
	 * 懒汉单例模式
	 * 获取JdbcTemplate
	 * @return 
	 * @throws IOException 
	 */
	public static JdbcTemplate getTemplate() throws IOException {
//		if(service == null) {
//			//注入实例
//			service = new Service();
//		}
		return service.getJdbcTemplate();
		
	}
	
	/**
	 * 
	 * @param sql  执行的sql语句
	 * @param args 不定项参数
	 * @return
	 * @throws DataAccessException
	 * @throws IOException
	 */
	public static JSONArray toJSONArray(String sql, Object... args) throws DataAccessException, IOException {
//		if(service == null) {
//			//注入实例
//			service = new Service();
//		}
		List list = service.getJdbcTemplate().queryForList(sql, args);
	
		return JSONArray.fromObject(list);
		
	}
	
	public static JSONObject toJSONObject(String sql, Object... args) throws DataAccessException, IOException {
		if(service == null) {
			//实例化
			service = new Service();
		}
		List list = service.getJdbcTemplate().queryForList(sql,args);
		if(list.size() == 0) {
			return new JSONObject();
		}
		return JSONObject.fromObject(list.get(0));
	}
//	public static void main(String[] args) {
//		try {
//			JSONArray jsonArray = dataService.toJSONArray("select * from t_user where id = 2332 limit 10");
//			System.out.println(jsonArray);
//			for(int i=0;i<jsonArray.size();i++) {
//				JSONObject json = jsonArray.getJSONObject(i);
//				System.out.println("id:" + json.getString("id") + " username:" 
//						+ json.getString("username") + " age:" + json.getString("age"));
//			}
//		} catch (DataAccessException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//		}
//		System.out.println("-----------------------");
//		try {
//			JSONObject json = dataService.toJSONObject("select * from t_user where id = 1212 limit 1");
//			System.out.println(json);
//			System.out.println("id:" + json.optString("id",null) + " username:" 
//					+ json.optString("username",null) + " age:" + json.optString("age",null));
//		} catch (DataAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
