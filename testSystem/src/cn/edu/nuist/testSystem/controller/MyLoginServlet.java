package cn.edu.nuist.testSystem.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.edu.nuist.testSystem.connection.DbConnection;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.UsersDAOImpl;
import cn.edu.nuist.testSystem.utils.ParamsInitiation;
import cn.edu.nuist.testSystem.utils.ReadJson;

/**
 * 处理用户登录，处理前端传送数据，并与数据库已存用户信息对比，并通过对比结果判断前端用户是否可以登录
 * @author Timlong
 * @version V1.0
 */
public class MyLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DbConnection dbConn;

	/**
	 * 该类的无参构造方法
	 */
	public MyLoginServlet() {
		super();
	}
	
	/**
	 * Servlet初始化方法，目标是初始化成员变量dbConn
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		
		ServletContext sc = this.getServletContext();
		this.dbConn = ParamsInitiation.initDbConn(sc);
	}

	/**
	 * 与前端通过 get方式交互
	 * @param request servlet请求对象， 
	 * @param response servlet回应对象
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//			doPost(request, response);
	}
	

	/**
	 * 与前端通过 post方式交互
	 * @param request servlet请求对象， 
	 * @param response servlet回应对象
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		Connection conn = this.dbConn.getConnection();   //为 每个连接的用户开辟一个数据库连接

		String json = ReadJson.getJsonString(request);
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json.toString());
		JsonObject jsonObj = element.getAsJsonObject();
		String upass = jsonObj.get("password").getAsString();
		String uname = jsonObj.get("username").getAsString();

		UsersDAOImpl udi = new UsersDAOImpl(conn);
		try {
			if(null == udi.findByUsername(uname).getUserName() || udi.findByUsername(uname).getUserName().equals("")){
				String feedBack = "{\"res\" : \"false\", \"message\" : \"不存在该用户，请先注册！\"}";
				out.write(feedBack);
			}
			else if(!udi.findByUsername(uname).getPassword().equals(upass)){
				String feedBack = "{\"res\" : \"false\", \"message\" : \"密码不正确！\"}";
				out.write(feedBack);
			}
			else{
				String feedBack = "{\"res\" : \"true\", \"message\" : \"登录成功，进入主页！\"}";
				out.write(feedBack);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(null != out){
				out.flush();
				out.close();
				this.dbConn.close(conn);
			}
		}
	}
}
