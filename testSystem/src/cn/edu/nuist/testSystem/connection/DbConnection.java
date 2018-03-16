package cn.edu.nuist.testSystem.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/** 创建数据库连接与销毁数据库连接类
 * @author Timlong
 * @version V1.0
 */
public class DbConnection {

	private static String DRIVER_CLASS = "";
	private static String URL = "";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	
	public static void setDRIVER_CLASS(String dRIVER_CLASS) {
		DRIVER_CLASS = dRIVER_CLASS;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public static void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public static void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	/**
	 * 带参构造函数，调用构造器获取数据库连接信息
	 * @param dRIVER_CLASS jdbc MySQL数据库驱动类名
	 * @param uRL MySQL数据库url
	 * @param uSERNAME 数据库用户名
	 * @param pASSWORD 数据库登录密码
	 */
	public DbConnection(String dRIVER_CLASS, String uRL, String uSERNAME, String pASSWORD) {
		DRIVER_CLASS = dRIVER_CLASS;
		URL = uRL;
		USERNAME = uSERNAME;
		PASSWORD = pASSWORD;
	}

	/**
	 * 获取数据库连接对象
	 * @return jdbc.sql的数据库连接对象Connection
	 */
	public Connection getConnection(){
		Connection conn = null;
		try{
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接对象，释放连接资源
	 * @param conn 数据库连接对象
	 */
	public void close(Connection conn) {
		try {
			if (null != conn )
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
