package cn.edu.nuist.testSystem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.edu.nuist.testSystem.beans.Questions;
import cn.edu.nuist.testSystem.connection.DbConnection;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.QuestionsDAOImpl;
import cn.edu.nuist.testSystem.utils.ParamsInitiation;
import cn.edu.nuist.testSystem.utils.ReadJson;

/**
 * 处理用户查看试题库任意题型操作，接收前端传送数据的题干关键字与题型关键字，并通过关键词在数据库已存选择题进行搜索，并通过json返回搜索结果
 * @author Timlong
 * @version V1.0
 */
public class ViewQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbConnection dbConn;

	/**
	 * 该类的无参构造方法
	 */
	public ViewQuestionsServlet() {
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
		//	response.getWriter().append("Served at: ").append(request.getContextPath());
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
		Connection conn = this.dbConn.getConnection();
		QuestionsDAOImpl qdi = new QuestionsDAOImpl(conn);
		Gson gson = new Gson();

		String json = ReadJson.getJsonString(request);

		//JsonParser ---->JsonElement ----> JsonObject ----> jsonObj.get()
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonObject jsonObj = element.getAsJsonObject();
		String type = jsonObj.get("type").getAsString();
		String key = jsonObj.get("searchkey").getAsString();

		try {
			if(null == type || type.equals("")){
				List<Questions> ls = qdi.getQuestions(key);

				if(0 < ls.size()){
					String res = gson.toJson(ls);
					out.write(res);
				}
				else{
					out.write("{\"res\" : \"false\", \"message\" : \"抱歉，找不到相关试题！\"}");
				}
			}
			else{
				List<Questions> ls = qdi.getQuestions(type, key);

				if(0 < ls.size()){
					String res = gson.toJson(ls);
					out.write(res);
				}
				else{
					out.write("{\"res\" : \"false\", \"message\" : \"抱歉，找不到相关试题！\"}");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			out.flush();
			out.close();
			this.dbConn.close(conn);
		}
	}
}
