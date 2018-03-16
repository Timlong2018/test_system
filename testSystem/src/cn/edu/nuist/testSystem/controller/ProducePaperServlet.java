package cn.edu.nuist.testSystem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
import cn.edu.nuist.testSystem.beans.TestPaper;
import cn.edu.nuist.testSystem.connection.DbConnection;
import cn.edu.nuist.testSystem.services.impls.PaperProductionServiceImpl;
import cn.edu.nuist.testSystem.utils.ParamsInitiation;
import cn.edu.nuist.testSystem.utils.ReadJson;

/**
 * 处理生成试卷，接收前端传送数据，按要求在数据库抽取合适的试题，将试题信息通过json返回到前端
 * @author Timlong
 * @version V1.0
 */
public class ProducePaperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbConnection dbConn;
	private PaperProductionServiceImpl ppsi;

	/**
	 * 该类的无参构造方法
	 */
	public ProducePaperServlet() {

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
		Gson gson = new Gson();
		

		String json = ReadJson.getJsonString(request);

		//JsonParser ---->JsonElement ----> JsonObject ----> jsonObj.get()
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonObject jsonObj = element.getAsJsonObject();
		
		Integer choice_num = Integer.parseInt(jsonObj.get("choice_num").getAsString());
		Integer gap_fill_num = Integer.parseInt(jsonObj.get("gap_fill_num").getAsString());
		Integer answerQues_num = Integer.parseInt(jsonObj.get("answerQues_num").getAsString());
		
		//。。。
//		System.out.println(answerQues_num);

		TestPaper paper = gson.fromJson(json, TestPaper.class);
	
		List<Questions> ls = new ArrayList<Questions>();

		try {
			this.ppsi = new PaperProductionServiceImpl(conn, paper);
			boolean flag = this.ppsi.extractRandQuestions(choice_num, gap_fill_num, answerQues_num);
			if(flag) {
				ls = this.ppsi.getQuestionLs();
				if(!ls.isEmpty()){  //抽取的试题不为空
					String returnJsonStr = gson.toJson(ls);
					out.write(returnJsonStr);
				}else {				//抽取的试题为空
					out.write("{\"res\" : \"false\", \"info\" : \"none_questions\", \"message\" : \"抽取到的试题为空！\"}");
				}
			}else {
				out.write("{\"res\" : \"false\", \"info\" : \"insert_error\", \"message\" : \"插入试题失败！\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			out.flush();
			out.close();
			this.dbConn.close(conn);
		}
	}
}
