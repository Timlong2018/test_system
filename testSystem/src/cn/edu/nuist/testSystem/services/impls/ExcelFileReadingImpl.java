package cn.edu.nuist.testSystem.services.impls;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.edu.nuist.testSystem.beans.Questions;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.QuestionsDAOImpl;

/**
 * 处理excel 表格导入试题数据库业务类
 * @author Timlong
 * @version 1.0.0
 */
public class ExcelFileReadingImpl {

	private QuestionsDAOImpl qdi;

	/**
	 * 该类的含参构造方法
	 * @param conn 数据库连接对象
	 */
	public ExcelFileReadingImpl(Connection conn) {
		this.setQdi(new QuestionsDAOImpl(conn));
	}

	/**
	 * 从指定路径读取excel试题表信息
	 * @param path 文件路径
	 * @return excel 文件信息字符串
	 */
	@SuppressWarnings("resource")
	public List<String[]> readExcelFile(String path) {

		List<String[]> rowls = new ArrayList<String[]>();
		try {
			OPCPackage pkg = OPCPackage.open(path);  
			XSSFWorkbook excel = new XSSFWorkbook(pkg);
			XSSFSheet sheet1 = excel.getSheetAt(0);

			for(@SuppressWarnings("rawtypes")java.util.Iterator itRow = sheet1.iterator(); itRow.hasNext();) {
				XSSFRow row = (XSSFRow) itRow.next();
				String[] module = new String[row.getPhysicalNumberOfCells()];
				int countRow = 0;
				for(java.util.Iterator<Cell> it = row.cellIterator(); it.hasNext();) {

					XSSFCell cell = (XSSFCell) it.next();
					//					System.out.print(cell.getStringCellValue() + "  "); 
					module[countRow ++] = cell.getStringCellValue();
				}
				rowls.add(module);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowls;
	}

	/**
	 * 将试题信息导入数据库
	 * @param path 文件路径
	 * @param type 题目类型
	 * @return boolean 表示导入数据库是否成功
	 */
	public Boolean pourExcelFileIntoDb(String path, Integer type) {

		List<String[]> ls = this.readExcelFile(path);

		String stem = "";

		for(String[] arr : ls) {
			for(String str : arr) {
				if(-1 == str.indexOf("答案")) {
					stem += str;
				}
				else {
					Questions qus = new Questions();
					qus.setStem(stem);
					qus.setAnswer(str);
					qus.setType(type);

					try {
						this.qdi.doInsert(qus);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
					stem = "";
				}
			}
		}
		return true;
	}

	public QuestionsDAOImpl getQdi() {
		return qdi;
	}
	public void setQdi(QuestionsDAOImpl qdi) {
		this.qdi = qdi;
	}
}
