package cn.edu.nuist.testSystem.testInterfaceDAO.impls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.edu.nuist.testSystem.beans.PqConnection;

/**
 * pqconnection表的操作类
 * @author Timlong
 * @version 1.0
 */
public class PqConnectionDAO{
	Connection conn;
	PreparedStatement ps;

	/**
	 * 该类的含参构造方法
	 * @param connection 数据库连接类
	 */
	public PqConnectionDAO(Connection connection) {
		this.conn = connection;
	}

	/**
	 * 数据库"插"
	 * @param vo pqconnection表对应的bean对象
	 * @return 表示插入是否成功
	 * @throws Exception
	 */
	public boolean doInsert(PqConnection vo) throws Exception{
		String sql = "insert into pqconnection(qid, qscore, pid) values( ?, ?, ?)";
		this.ps = this.conn.prepareStatement(sql);

		this.ps.setInt(1, vo.getQid());
		this.ps.setInt(2, vo.getQscore());
		this.ps.setInt(3, vo.getPid());
		return this.ps.executeUpdate() > 0;
	}

	/**
	 * 数据库"改" 
	 * @param vo pqconnection表对应的bean对象
	 * @return 表示更新是否成功
	 * @throws Exception
	 */
	public boolean doUpdate(PqConnection vo) throws Exception {
		String sql = "update pqconnection set qid = ?, qscore = ?, pid = ? where id = ?";
		this.ps = this.conn.prepareStatement(sql);

		this.ps.setInt(1, vo.getQid());
		this.ps.setInt(2, vo.getQscore());
		this.ps.setInt(3, vo.getPid());
		this.ps.setInt(4, vo.getId());

		return this.ps.executeUpdate() > 0;
	}

	/**
	 * 数据库批量"删"
	 * @param  ids 批删除id集合对象
	 * @return 表示批量删除是否成功
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
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
	
	/**
	 * 删除pqconnection表的相应pid字段记录
	 * @param pid testpaper表的主键id
	 * @return 表示删除是否成功
	 * @throws SQLException
	 */
	public boolean doremoveByPID(int pid) throws SQLException{
		String sql = "delete from pqconnection where pid = ?";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setInt(1, pid);
		
		return this.ps.executeUpdate() > 0;
	}

	/**
	 * 查找pqconnection表的相应pid字段记录
	 * @param pid testpaper表的主键id
	 * @return pqconnection表对应的bean对象
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PqConnection> findByPid(int pid) throws Exception {
		List ls = new ArrayList<PqConnection>();
		String sql = "select id, qid, qscore, pid from pqconnection where pid = ?";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setInt(1, pid);

		ResultSet rs = this.ps.executeQuery();
		while(rs.next()){
			PqConnection vo = new PqConnection();
			vo.setId(rs.getInt(1));
			vo.setQid(rs.getInt(2));
			vo.setQscore(rs.getInt(3));
			vo.setPid(rs.getInt(4));
			ls.add(vo);
		}
		return ls;
	}

	/**
	 * 查找pqconnection表的相应qid字段记录
	 * @param qid questions表的主键id
	 * @return 表示查找是否成功
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PqConnection> findByQid(int qid) throws Exception {
		List ls = new ArrayList<PqConnection>();
		String sql = "select id, qid, qscore, pid from pqconnection where qid = ?";
		this.ps = this.conn.prepareStatement(sql);
		this.ps.setInt(1, qid);

		ResultSet rs = this.ps.executeQuery();
		while(rs.next()){
			PqConnection vo = new PqConnection();
			vo.setId(rs.getInt(1));
			vo.setQid(rs.getInt(2));
			vo.setQscore(rs.getInt(3));
			vo.setPid(rs.getInt(4));
			ls.add(vo);
		}
		return ls;
	}
}