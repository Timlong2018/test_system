package cn.edu.nuist.testSystem.testInterfaceDAO.impls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.edu.nuist.testSystem.beans.Questions;
import cn.edu.nuist.testSystem.testInterfaceDAO.IQuestionsDAO;

/**
 * questions表的操作类
 * @author Timlong
 * @version 1.0
 */
public class QuestionsDAOImpl implements IQuestionsDAO {

	private Connection conn;
	private PreparedStatement ps;

	/**
	 * 该类的含参构造方法
	 * @param conn 数据库连接类
	 */
	public QuestionsDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doInsert(Questions vo) throws Exception {
		String sql = "insert into questions(module, type, stem, answer, difdeg) values(?, ?, ?, ?, ?)";
		this.ps = conn.prepareStatement(sql);

		this.ps.setInt(1, vo.getModule());
		this.ps.setInt(2, vo.getType());
		this.ps.setString(3, vo.getStem());
		this.ps.setString(4, vo.getAnswer());
		this.ps.setInt(5, vo.getDifdeg());

		return ps.executeUpdate() > 0;
	}
	
	@Override
	public boolean doUpdate(Questions vo) throws Exception {
		String sql = "update questions set module = ?, type = ?, stem = ?, answer = ?, difdeg = ? where id = ?"; 
		this.ps = conn.prepareStatement(sql);

		this.ps.setInt(1, vo.getModule());
		this.ps.setInt(2, vo.getType());
		this.ps.setString(3, vo.getStem());
		this.ps.setString(4, vo.getAnswer());
		this.ps.setInt(5, vo.getDifdeg());
		this.ps.setInt(6, vo.getId());

		return this.ps.executeUpdate() > 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean doRemoveBtch(Set<Integer> ids) throws Exception {
		
		if(null == ids || ids.size() == 0){
			return false;
		}

		int i = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("delete from questions where id in (");

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
	public Questions findById(Integer id) throws Exception {

		Questions vo = new Questions();
		String sql = "select id, module, type, stem, answer, difdeg from questions where id = ?";
		this.ps = this.conn.prepareStatement(sql);

		this.ps.setInt(1, id);
		ResultSet rs = this.ps.executeQuery();
		if(rs.next()){
			vo.setId(rs.getInt(1));	
			vo.setModule(rs.getInt(2));
			vo.setType(rs.getInt(3));
			vo.setStem(rs.getString(4));
			vo.setAnswer(rs.getString(5));
			vo.setDifdeg(rs.getInt(6));
		}
		return vo;
	}

	@Override
	public List<Questions> findAll() throws Exception {
		List<Questions> all = new ArrayList<Questions>(); 
		String sql = "select id, module, type, stem, answer, difdeg from questions"; 
		this.ps = this.conn.prepareStatement(sql); 
		ResultSet rs = this.ps.executeQuery(); 
		while (rs.next()) { 
			Questions vo = new Questions(); 
			vo.setId(rs.getInt(1)); 
			vo.setModule(rs.getInt(2));
			vo.setType(rs.getInt(3)); 
			vo.setStem(rs.getString(4)); 
			vo.setAnswer(rs.getString(5)); 
			vo.setDifdeg(rs.getInt(6)); 

			all.add(vo);
		}
		return all;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws Exception {
		String sql = "select count(id) from questions where ? like ?";
		this.ps = this.conn.prepareStatement(sql); 
		this.ps.setString(1, column);
		this.ps.setString(2, "%" + keyWord + "%"); 
		ResultSet rs = this.ps.executeQuery(); 
		while (rs.next()) { 
			return rs.getInt(1); 
		} 
		return null;
	}
	
	/**
	 * 根据试题类型与题干关键字搜索试题
	 * @param type 试题类型
	 * @param keyWord 试题题干关键字
	 * @return 数据库搜索的试题列表集
	 * @throws SQLException
	 */
	public List<Questions> getQuestions(String type, String keyWord) throws SQLException{
		String sql = "select id, module, type, stem, answer, difdeg from questions where type = ? and stem like ?";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setString(1, type);
		this.ps.setString(2, "%" + keyWord + "%");
		ResultSet rs = this.ps.executeQuery();
		List<Questions> ls = new ArrayList<Questions>();
		while(rs.next()){
			Questions vo = new Questions();
			vo.setId(rs.getInt(1)); 
			vo.setModule(rs.getInt(2));
			vo.setType(rs.getInt(3)); 
			vo.setStem(rs.getString(4)); 
			vo.setAnswer(rs.getString(5)); 
			vo.setDifdeg(rs.getInt(6)); 
			ls.add(vo);
		}
		return ls;
	}
	
	/**
	 * 根据试题题干关键字搜索试题
	 * @param key 试题题干关键字
	 * @return 数据库搜索的试题列表集
	 * @throws SQLException
	 */
	public List<Questions> getQuestions(String key) throws SQLException{
		
		String sql = "select id, module, type, stem, answer, difdeg from questions where stem like ?";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setString(1, key);
		ResultSet rs = this.ps.executeQuery();
		List<Questions> ls = new ArrayList<Questions>();
		while(rs.next()){
			Questions vo = new Questions();
			vo.setId(rs.getInt(1)); 
			vo.setModule(rs.getInt(2));
			vo.setType(rs.getInt(3)); 
			vo.setStem(rs.getString(4)); 
			vo.setAnswer(rs.getString(5)); 
			vo.setDifdeg(rs.getInt(6)); 
			ls.add(vo);
		}
		return ls;
	}
}
