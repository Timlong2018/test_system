package cn.edu.nuist.testSystem.testInterfaceDAO;

import java.util.List;

import cn.edu.nuist.testSystem.beans.TestPaper;

/**
 * 对testpaper表的操作公共接口 
 * @author Timlong
 * @version 1.0
 */
public interface ITestPaperDAO extends IBase<Integer, TestPaper> {
	
	public TestPaper findByIdDetails(Integer id) throws Exception;
	
	public List<TestPaper> findAllDetails() throws Exception;
	

}
