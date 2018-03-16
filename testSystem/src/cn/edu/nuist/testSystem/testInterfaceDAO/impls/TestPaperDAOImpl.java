package cn.edu.nuist.testSystem.testInterfaceDAO.impls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.nuist.testSystem.beans.Questions;
import cn.edu.nuist.testSystem.beans.TestPaper;
import cn.edu.nuist.testSystem.testInterfaceDAO.ITestPaperDAO;
import cn.edu.nuist.testSystem.utils.RandPaperUtils;

/**
 * testpaper表的操作类
 * @author Timlong
 * @version 1.0
 */
public class TestPaperDAOImpl implements ITestPaperDAO {
	Connection conn = null;
	PreparedStatement ps = null;

	/**
	 * 该类的含参构造方法
	 * @param conn 数据库连接类
	 */
	public TestPaperDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doInsert(TestPaper vo) throws Exception {
		String sql = "insert into testpaper(name, type, score, examtime) values(?, ?, ?, ?)";
		this.ps = conn.prepareStatement(sql);

		this.ps.setString(1, vo.getName());
		this.ps.setInt(2, vo.getType());
		this.ps.setInt(3, vo.getScore());
		this.ps.setTimestamp(4, vo.getExamtime());

		return ps.executeUpdate() > 0;
	}

	/**
	 * 数据库表testpaper数据插入，并返回插入的记录的id
	 * @param vo testpaper对应的Bean对象
	 * @return 插入的记录的id
	 * @throws SQLException
	 */
	public int doInsertAndGetID(TestPaper vo) throws SQLException {
		String sql = "insert into testpaper(name, type, score, examtime) values(?, ?, ?, ?)";
		this.ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		this.ps.setString(1, vo.getName());
		this.ps.setInt(2, vo.getType());
		this.ps.setInt(3, vo.getScore());
		this.ps.setTimestamp(4, vo.getExamtime());

		if(this.ps.executeUpdate() > 0){
			ResultSet rs = this.ps.getGeneratedKeys();
			while(rs.next()){
				return rs.getInt(1);
			}
		}
		return -1;
	}


	@Override
	public boolean doUpdate(TestPaper vo) throws Exception {
		String sql = "update testPaper set name = ?, type = ?, score = ?, examtime = ? where id = ?"; 
		this.ps = conn.prepareStatement(sql);

		this.ps.setString(1, vo.getName());
		this.ps.setInt(2, vo.getType());
		this.ps.setInt(3, vo.getScore());
		this.ps.setTimestamp(4, vo.getExamtime());
		this.ps.setInt(5, vo.getId());

		return this.ps.executeUpdate() > 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean doRemoveBtch(Set<Integer> ids) throws Exception {

		int i = 0;
		if(null == ids || ids.size() == 0){
			return false;
		}

		StringBuffer sql = new StringBuffer();

		while(i < ids.size() - 1){
			sql.append("?, ");
		}
		sql.append("?)");

		this.ps = this.conn.prepareStatement(sql.toString());		
		Iterator it = ids.iterator();

		i = 1;
		while(it.hasNext()){
			this.ps.setInt(i, (int) it.next());		
			i++;
		}
		return this.ps.executeUpdate() == ids.size();
	}

	@Override
	public TestPaper findById(Integer id) throws Exception {

		TestPaper vo = new TestPaper();
		String sql = "select id, name, type, score, examtime from testPaper where id = ?";
		this.ps = this.conn.prepareStatement(sql);

		this.ps.setInt(1, id);
		ResultSet rs = this.ps.executeQuery();
		if(rs.next()){

			vo.setId(rs.getInt(1));	
			vo.setName(rs.getString(2));
			vo.setType(rs.getInt(3));
			vo.setScore(rs.getInt(4));
			vo.setExamtime(rs.getTimestamp(5));
		}
		return vo;
	}

	@Override
	public List<TestPaper> findAll() throws Exception {
		List<TestPaper> all = new ArrayList<TestPaper>(); 
		String sql = "select id, type, score, examtime from questions"; 
		this.ps = this.conn.prepareStatement(sql); 
		ResultSet rs = this.ps.executeQuery(); 
		while (rs.next()) { 
			TestPaper vo = new TestPaper(); 
			vo.setId(rs.getInt(1));	
			vo.setName(rs.getString(2));
			vo.setType(rs.getInt(3));
			vo.setScore(rs.getInt(4));
			vo.setExamtime(rs.getTimestamp(5));
			all.add(vo);
		}
		return all;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws Exception {
		String sql = "select count(id) from testpaper where ? like ?";
		this.ps = conn.prepareStatement(sql); 
		this.ps.setString(1, column);
		this.ps.setString(2, "%" + keyWord + "%"); 
		ResultSet rs = this.ps.executeQuery(); 
		if (rs.next()) { 
			return rs.getInt(1); 
		} 
		return null;
	}

	@Override
	public TestPaper findByIdDetails(Integer id) throws Exception {
		TestPaper vo = new TestPaper();
		Map<Questions, Integer> map = new HashMap<Questions, Integer>();

		String sql = "select id, name, type, score, examtime from testpaper where id = ?"; 
		this.ps = this.conn.prepareStatement(sql); 
		this.ps.setInt(1, id);

		ResultSet rs = this.ps.executeQuery(); 
		if (rs.next()) { 
			vo.setId(rs.getInt(1));	
			vo.setName(rs.getString(2));
			vo.setType(rs.getInt(3));
			vo.setScore(rs.getInt(4));
			vo.setExamtime(rs.getTimestamp(5));
		} 

		sql = " select a.id, a.type, a.stem, a.answer, a.difdeg, b.qscore from questions a, pqconnection b "
				+ "where b.pid = ? and b.qid = a.id";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setInt(1, id);
		rs = this.ps.executeQuery();
		while(rs.next()){
			Questions q =new Questions();
			q.setId(rs.getInt(1));
			q.setType(rs.getInt(2));
			q.setStem(rs.getString(3));
			q.setAnswer(rs.getString(4));
			q.setDifdeg(rs.getInt(5));
			map.put(q, rs.getInt(6));
		}
		vo.setMap(map);
		return vo;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TestPaper> findAllDetails() throws Exception {

		List<TestPaper> tpLs = new ArrayList<TestPaper>();
		List<Integer> idLs = new ArrayList<Integer>();
		String sql = "select id from testPaper";
		this.ps = this.conn.prepareStatement(sql);
		ResultSet rs = this.ps.executeQuery();
		while(rs.next()){
			idLs.add(rs.getInt(1));
		}

		Iterator it = idLs.iterator();
		while(it.hasNext()){
			tpLs.add(findByIdDetails((Integer)it.next()));
		}

		return tpLs;
	}
	
	/**
	 * 从试题库随机抽取各种题型的相应数量
	 * @param choice_num 选择题数
	 * @param gap_fill_num 填空题数
	 * @param answerQues_num 简答题数
	 * @return questions 试题与分值的键值对
	 * @throws SQLException
	 */
	public Map<Questions, Integer> extractRandomQuestionsByNum(int choice_num, int gap_fill_num, int answerQues_num) throws SQLException{
		ResultSet rs = null;
		
		Map<Questions, Integer> questions = new HashMap<Questions, Integer>();
		RandPaperUtils randUtils = new RandPaperUtils();
		
		String sql = "select id, module, type, stem, answer, difdeg from questions "
			    + "where id >= ((select MAX(id) from questions) - (select MIN(id) "
				+ "from questions) + 1) * RAND() + (select MIN(id) from questions) "
				+ "and module in" + randUtils.getTenRandNum() + "and type = ? limit ?";
		this.ps = this.conn.prepareStatement(sql);

		if(0 != choice_num){
		this.ps.setInt(1, 1);  		//   1————>单选题
		this.ps.setInt(2, choice_num);
		rs = this.ps.executeQuery();
		randUtils.addResultSet(questions, rs);
		}

		if(0 != gap_fill_num){
		this.ps.setInt(1, 2);       //   2————>判断题
		this.ps.setInt(2, gap_fill_num);   
		rs = this.ps.executeQuery();
		randUtils.addResultSet(questions, rs);
		}

		if(0 != answerQues_num){
		this.ps.setInt(1, 3);		//   3————>简答题
		this.ps.setInt(2, answerQues_num);
		rs = this.ps.executeQuery();
		randUtils.addResultSet(questions, rs);
		}
		
		return questions;
	}
}
