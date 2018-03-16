package cn.edu.nuist.testSystem.services.impls;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.nuist.testSystem.beans.PqConnection;
import cn.edu.nuist.testSystem.beans.Questions;
import cn.edu.nuist.testSystem.beans.TestPaper;
import cn.edu.nuist.testSystem.services.PaperProductionService;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.PqConnectionDAO;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.QuestionsDAOImpl;
import cn.edu.nuist.testSystem.testInterfaceDAO.impls.TestPaperDAOImpl;

/**
 * 处理按用户需求随机生成试卷业务的业务类
 * @author Timlong
 * @version 1.0
 */
public class PaperProductionServiceImpl implements PaperProductionService {

	Connection conn;
	QuestionsDAOImpl qdi = null;
	TestPaperDAOImpl tdi;
	TestPaper paper = new TestPaper();  							//必须由servlet 调用此类的构造器设值
	List<Questions> questionLs = new ArrayList<Questions>(); 		//serlet获取返回到前端
	
	/**
	 * 该类的含参构造方法
	 * @param conn 数据库连接对象
	 * @param paper testpaper表相应的bean对象
	 */
	public PaperProductionServiceImpl(Connection conn, TestPaper paper) {
		this.conn = conn;
		this.paper = paper;
	}

	@Override
	public boolean extractRandQuestions(Integer choice_num, Integer gap_fill_num, Integer answerQues_num) throws Exception{

		this.tdi = new TestPaperDAOImpl(this.conn);
		Map<Questions, Integer> questions = new HashMap<Questions, Integer>();
		questions = this.tdi.extractRandomQuestionsByNum(choice_num, gap_fill_num, answerQues_num);

		this.paper.setMap(questions);

		int paperID = this.tdi.doInsertAndGetID(this.paper);
		if(-1 != paperID) { 									   	//testPaper插入成功
			PqConnectionDAO pcd = new PqConnectionDAO(this.conn);
			int count = 0;											//记录pqconnection 表数据记录插入的行数

			for(Map.Entry<Questions, Integer> entry : questions.entrySet()) {
				PqConnection pc = new PqConnection();
				pc.setQid(entry.getKey().getId());	
				pc.setQscore(entry.getValue());
				pc.setPid(paperID);

				if(!pcd.doInsert(pc)){
					break;
				}
				count ++;
			}
			if(count < questions.entrySet().size()) { 				//pqconnection表的记录并未全部插入
				Set<Integer> ids = new HashSet<Integer>();
				ids.add(paperID);
				this.tdi.doRemoveBtch(ids);
				new PqConnectionDAO(conn).doremoveByPID(paperID);	//清除记录

				return false;
			}else {
				for(Map.Entry<Questions, Integer> entry : questions.entrySet()){
					this.questionLs.add(entry.getKey());
				}
				
				return true; //只有在此处才能返回成功
			}
		}else {
			return false;
		}
	}

	public TestPaper getPaper() {
		return paper;
	}

	public void setPaper(TestPaper paper) {
		this.paper = paper;
	}
	
	public List<Questions> getQuestionLs() {
		return questionLs;
	}

	public void setQuestionLs(List<Questions> questionLs) {
		this.questionLs = questionLs;
	}



}
