package cn.edu.nuist.testSystem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.nuist.testSystem.beans.Users;
import cn.edu.nuist.testSystem.connection.DbConnection;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.UsersDAOImpl;
import cn.edu.nuist.testSystem.utils.ParamsInitiation;
import cn.edu.nuist.testSystem.utils.ReadJson;

/**
 * 处理用户注册，接收前端传送的注册信息，并将数据存入到数据库相应位置，并将注册结果通过json数据返回到前端
 * @author Timlong
 * @version V1.0
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbConnection dbConn;

	/**
	 * 该类的无参构造方法
	 */
	public RegisterServlet() {
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
		//	doPost(request, response);
	}
	
	/**
	 * 与前端通过 post方式交互
	 * @param request servlet请求对象， 
	 * @param response servlet回应对象
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Connection conn = this.dbConn.getConnection();
		PrintWriter out = response.getWriter();

		String json = ReadJson.getJsonString(request);

		Users user = new Gson().fromJson(json.toString(), Users.class);
		UsersDAOImpl udi = new UsersDAOImpl(conn);

		System.out.println();

		try {
			if(null == udi.findByUsername(user.getUserName())){
				if(udi.doInsert(user)){
					String feedBack = "{\"res\" : \"true\",\"message\" : \"注册成功！\"}";
					out.write(feedBack);
				}
			} else{
				String feedBack = "{\"res\" : \"false\",\"message\" : \"该用户名已经存在！\"}";
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
