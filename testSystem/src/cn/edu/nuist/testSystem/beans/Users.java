package cn.edu.nuist.testSystem.beans;

import java.io.Serializable;

/**数据库User表的bean类
 * 用以表示该表的各个字段信息
 * @author Timlong
 * @version V1.0
 */
@SuppressWarnings("serial")
public class Users implements Serializable{
	private int id;
	private String username;
	private String password;
	private boolean active;
	
	/**
	 * 该类的无参构造方法
	 */
	public Users() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * 返回该bean的数据信息
	 * @return 数据信息字符串
	 */
	public String toString(){
		String re;
		
		re = "ID:	" + this.getId()
		   + "\nUser Name:	" + this.getUserName()
		   + "\nPassword:	" + this.getPassword()
		   + "\nIs Actived?	" + this.isActive();
		
		return re;		
	}

}
