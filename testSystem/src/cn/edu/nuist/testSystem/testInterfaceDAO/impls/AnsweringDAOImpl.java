 package cn.edu.nuist.testSystem.testInterfaceDAO.impls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.edu.nuist.testSystem.beans.Answering;
import cn.edu.nuist.testSystem.testInterfaceDAO.IAnsweringDAO;

/**
 * answering表的操作类
 * @author Timlong
 * @version 1.0
 */
public class AnsweringDAOImpl implements IAnsweringDAO {

	private Connection conn;
	private	PreparedStatement ps;

	/**
	 * 该类的含参构造方法
	 * @param connection 数据库连接对象
	 */
	public AnsweringDAOImpl(Connection connection) {
		this.conn = connection;
	}

	@Override
	public boolean doInsert(Answering vo) throws Exception {
		String sql = "insert into answering(uID, qID, pID, answer, aTime, score) values(?, ?, ?, ?, ?, ?)";
		this.ps = conn.prepareStatement(sql);

		this.ps.setInt(1, vo.getUserID());
		this.ps.setInt(2, vo.getqID());
		this.ps.setInt(3, vo.getpID());
		this.ps.setString(4, vo.getAnswer());
		this.ps.setTimestamp(5, vo.getaTime());
		this.ps.setInt(6, vo.getScore());

		return ps.executeUpdate() > 0;
	}

	@Override
	public boolean doUpdate(Answering vo) throws Exception {
		String sql = "update answering set uid = ?, qid = ?, pid = ?, answer = ?, atime = ?, score = ? where id = ?";
		this.ps = conn.prepareStatement(sql);

		this.ps.setInt(1, vo.getUserID());
		this.ps.setInt(2, vo.getqID());
		this.ps.setInt(3, vo.getpID());
		this.ps.setString(4, vo.getAnswer());
		this.ps.setTimestamp(5, vo.getaTime());
		this.ps.setInt(6, vo.getScore());

		this.ps.setInt(7, vo.getId());

		return ps.executeUpdate() > 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean doRemoveBtch(Set<Integer> ids) throws Exception {

		int i = 0;
		if(null == ids || ids.size() == 0){
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("delete from answering where id in (");

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
	public Answering findById(Integer id) throws Exception {

		Answering vo = new Answering();
		String sql = "select id, uid, qid, pid, answer, atime, score from answering where id = ?";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setInt(1, id);
		ResultSet rs = this.ps.executeQuery();
		if(rs.next()){
			vo.setId(rs.getInt(1));
			vo.setUserID(rs.getInt(2));
			vo.setqID(rs.getInt(3));
			vo.setpID(rs.getInt(4));
			vo.setAnswer(rs.getString(5));
			vo.setaTime(rs.getTimestamp(6));
			vo.setScore(rs.getInt(7));
		}
		return vo;
	}

	@Override
	public List<Answering> findAll() throws Exception {
		String sql = "select id, uid, qid, pid, answer, atime, score from answering";
		List<Answering> ls = new ArrayList<Answering>();

		this.ps = this.conn.prepareStatement(sql);
		ResultSet rs = this.ps.executeQuery();
		while(rs.next()){
			Answering vo = new Answering();

			vo.setId(rs.getInt(1)); 
			vo.setUserID(rs.getInt(2)); 
			vo.setqID(rs.getInt(3)); 
			vo.setpID(rs.getInt(4)); 
			vo.setAnswer(rs.getString(5));
			vo.setaTime(rs.getTimestamp(6));
			vo.setScore(rs.getInt(7));

			ls.add(vo);
		}
		return ls;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws Exception {
		String sql = "select count(id) from answering where ? like ?";
		this.ps = conn.prepareStatement(sql); 
		this.ps.setString(1, column);
		this.ps.setString(2, "%" + keyWord + "%"); 
		ResultSet rs = this.ps.executeQuery(); 
		if (rs.next()) { 
			return rs.getInt(1); 
		} 
		return null;
	}
}
