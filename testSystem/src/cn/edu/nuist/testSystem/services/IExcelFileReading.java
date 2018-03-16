package cn.edu.nuist.testSystem.services;

/**
 * 处理 excel文件导入试题库业务的公共接口
 * @author Timlong
 * @version 1.0
 */
public interface IExcelFileReading {
	/**
	 * 将excel表格数据导入后台数据库questions表的方法
	 * @param obj 
	 * @return 布尔类型，表示方法体中操作是否成功
	 * @throws Exception
	 */
	public Boolean pourExcelFileIntoDb(Object obj) throws Exception;
}
