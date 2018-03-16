package cn.edu.nuist.testSystem.utils;

import javax.servlet.ServletContext;

import cn.edu.nuist.testSystem.connection.DbConnection;

/**
 * 此类为Servlet初始化方法调用，用于读取配置文件中的全局变量值，通过DbConnection类的构造函数初始化每个Servlet类中的数据库连接对象以供后续数据库操作使用
 * Static Method：  intitDbConn()
 * @author Timlong
 * @version 1.0
 */
public class ParamsInitiation {
	/**
	 * 通过参数sc获取数据库连接信息，由此创建数据库连接对象
	 * @param sc servlet背景
	 * @return conn 数据库连接对象
	 */
	public static DbConnection initDbConn(ServletContext sc) {
		String driver_class = sc.getInitParameter("driver_class");
		String url = sc.getInitParameter("url");
		String username = sc.getInitParameter("username");
		String password = sc.getInitParameter("password");
		
		DbConnection conn = new DbConnection(driver_class, url, username, password);
		return conn;
	}
}
