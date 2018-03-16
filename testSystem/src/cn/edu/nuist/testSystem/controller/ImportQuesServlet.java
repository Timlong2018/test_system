package cn.edu.nuist.testSystem.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.nuist.testSystem.connection.DbConnection;
import cn.edu.nuist.testSystem.services.impls.ExcelFileReadingImpl;
import cn.edu.nuist.testSystem.utils.ParamsInitiation;
import cn.edu.nuist.testSystem.utils.QuestionTypeTransition;

/**
 * 处理excel文件导入试题库Servlet，处理前端传送数据，并插入数据库，并将处理信息返回给前端
 * @author Timlong
 * @version V1.0
 */
public class ImportQuesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbConnection dbConn;

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

	/**
	 * 该类的无参构造方法
	 */
	public ImportQuesServlet() {
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
	 * @param request servlet请求对象 
	 * @param response servlet回应对象
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		request.setCharacterEncoding("utf-8");
		response.setContentType("text;charset=utf-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		Boolean flag = false;
		File storeFile = null;
		String filePath = "";
		String fileName = "";

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));


		ServletFileUpload upload = new ServletFileUpload(factory);

		// 设置最大文件上传值
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// 设置最大请求值 (包含文件和表单数据)
		upload.setSizeMax(MAX_REQUEST_SIZE);


		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

		//		System.out.println(uploadPath); 
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		List<FileItem> items;
		try {
			items = upload.parseRequest(request);

			if (null != items && items.size() > 0) {

				for(FileItem item : items) {

					System.out.println(item);

					if(!item.isFormField() ) {

						String file = item.getName();
						fileName = new File(file).getName(); 
						filePath = uploadPath + File.separator + fileName;
						storeFile = new File(filePath);

						// 在控制台输出文件的上传路径...
						System.out.println(filePath);
						item.write(storeFile);

						flag = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		if(flag && storeFile.exists()) {  //这写读取xlsx文件的代码

			ExcelFileReadingImpl efri = new ExcelFileReadingImpl(dbConn.getConnection());

			Integer quesType = QuestionTypeTransition.judgeQuestionType(fileName);

			//...
			System.out.println("fileName： " + fileName.substring(0, fileName.indexOf(".")));

			//...	
			System.out.println("试题类型：	" + quesType);

			if(efri.pourExcelFileIntoDb(filePath, quesType)) {
				out.write("{\"res\" : \"true\"}");
			}
			else {
				out.write("{\"res\" : \"false\", \"message\" : \"插入式题失败！\"}");
			}
		}
		else {
			out.write("{\"res\" : \"false\", \"message\" : \"文件上传失败！\"}");
		}
	}
}


