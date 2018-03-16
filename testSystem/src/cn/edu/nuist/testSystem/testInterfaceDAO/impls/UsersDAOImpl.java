package cn.edu.nuist.testSystem.testInterfaceDAO.impls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.edu.nuist.testSystem.beans.Users;
import cn.edu.nuist.testSystem.testInterfaceDAO.IUsersDAO;

/**
 * users表的操作类
 * @author Timlong
 * @version 1.0
 */
public class UsersDAOImpl implements IUsersDAO {

	Connection conn;
	PreparedStatement ps;

	/**
	 * 该类的含参构造方法
	 * @param conn 数据库连接类
	 */
	public UsersDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doInsert(Users vo) throws Exception {
		String sql = "insert into users(username, password, active) values(?, ?, ?)";
		this.ps = conn.prepareStatement(sql);

		this.ps.setString(1, vo.getUserName());
		this.ps.setString(2, vo.getPassword());
		this.ps.setBoolean(3, vo.isActive());

		return ps.executeUpdate() > 0;
	}

	@Override
	public boolean doUpdate(Users vo) throws Exception {
		String sql = "update users set username = ?, password = ?, active = ? where id = ?";
		this.ps = conn.prepareStatement(sql);

		this.ps.setString(1, vo.getUserName());
		this.ps.setString(2, vo.getPassword());
		this.ps.setBoolean(3, vo.isActive());
		this.ps.setInt(4, vo.getId());


		return ps.executeUpdate() > 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean doRemoveBtch(Set<Integer> ids) throws Exception {
		if(null == ids || ids.size() == 0){
			return false;
		}

		int count = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("delete from users where id in (");

		while(count < ids.size() - 1){
			sql.append("?, ");
		}
		sql.append("?)");
		this.ps = this.conn.prepareStatement(sql.toString());
		Iterator it = ids.iterator();

		count = 1;
		while(it.hasNext()){
			this.ps.setInt(count, (Integer)it.next());
			count ++;
		}
		return this.ps.executeUpdate() == ids.size();
	}

	@Override
	public Users findById(Integer id) throws Exception {
		Users vo = new Users();
		String sql = "select id, username, password, active from users where id = ?";

		this.ps = this.conn.prepareStatement(sql);
		this.ps.setInt(1, id);

		ResultSet rs = this.ps.executeQuery();
		if(rs.next()){
			vo.setId(rs.getInt(1));
			vo.setUserName(rs.getString(2));
			vo.setPassword(rs.getString(3));
			vo.setActive(rs.getBoolean(4));
		}
		return vo;
	}
	
	/**
	 * 通过用户名查找用户具体信息
	 * @param uname 用户名
	 * @return vo 与用户名对应users表的bean对象
	 * @throws SQLException
	 */
	@SuppressWarnings("null")
	public Users findByUsername(String uname) throws SQLException{
		Users vo = null;
		String sql = "select id, username, password, active from users where username = ?";

		this.ps = this.conn.prepareStatement(sql);
		this.ps.setString(1, uname);

		ResultSet rs = this.ps.executeQuery();
		if(rs.next()){
			vo.setId(rs.getInt(1));
			vo.setUserName(rs.getString(2));
			vo.setPassword(rs.getString(3));
			vo.setActive(rs.getBoolean(4));
		}
		return vo;
	}

	@Override
	public List<Users> findAll() throws Exception {
		String sql = "select id, username, password, active from users";
		this.ps = this.conn.prepareStatement(sql);
		ResultSet rs = this.ps.executeQuery();
		List<Users> ls = new ArrayList<Users>();
		while(rs.next()){
			Users vo = new Users(); 
			vo.setId(rs.getInt(1));
			vo.setUserName(rs.getString(2));
			vo.setPassword(rs.getString(3));
			vo.setActive(rs.getBoolean(4));

			ls.add(vo);
		}

		return ls;
	}

	@Override
	public Integer getAllCount(String column, String keyWord) throws Exception {
		String sql = "select count(id) from users where ? like ?";
		this.ps = this.conn.prepareStatement(sql); 
		this.ps.setString(1, column);
		this.ps.setString(2, "%" + keyWord + "%"); 
		ResultSet rs = this.ps.executeQuery(); 
		if (rs.next()) { 
			return rs.getInt(1); 
		} 
		return null;
	}

}
