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

import cn.edu.nuist.testSystem.beans.Questions;
import cn.edu.nuist.testSystem.connection.DbConnection;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.QuestionsDAOImpl;
import cn.edu.nuist.testSystem.utils.ParamsInitiation;
import cn.edu.nuist.testSystem.utils.ReadJson;

/**
 * 处理用户手动录入试题，接收前端输入的试题相关数据，并存入数据库相应位置，然后将处理结果通过json数据格式返回到前端
 * @author Timlong
 * @version V1.0
 */
public class QuestionInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DbConnection dbConn;   
	
	/**
	 * 该类的无参构造方法
	 */
    public QuestionInputServlet() {
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * 与前端通过 post方式交互
	 * @param request servlet请求对象， 
	 * @param response servlet回应对象
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Connection conn = this.dbConn.getConnection();
		
		QuestionsDAOImpl qdi = new QuestionsDAOImpl(conn);
		String json = ReadJson.getJsonString(request);
		
		Questions qus = new Gson().fromJson(json, Questions.class);
		try {
			qdi.doInsert(qus);
			String feedBack = "{\"res\" : \"true\"}";
			out.write(feedBack);
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
