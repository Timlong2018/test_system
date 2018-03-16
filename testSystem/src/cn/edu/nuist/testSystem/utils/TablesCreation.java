package cn.edu.nuist.testSystem.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 后台数据库创建类
 * @author Timlong
 * @version 1.0
 */
public class TablesCreation {
	private Connection conn;
	private	PreparedStatement ps;

	/**
	 * 该类的无参构造方法
	 */
	public TablesCreation(){
	}

	/**
	 * 该类的含参构造方法，以实例化成员变量conn
	 * @param conn 数据库连接对象
	 */
	public TablesCreation(Connection conn){
		this.conn = conn;
	}

	/**
	 * 创建系统后台数据库全部表格方法
	 * @return flag 表示数据库是否创建成功
	 * @throws SQLException
	 */
	public boolean createTables() throws SQLException{
		boolean flag = false;
		String creQuestions = 
				"create table questions(id int not null auto_increment primary key, "
						+ "module enum('1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12') not null, "
						+ "type enum('1', '2', '3', '4', '5', '6', '7', '8', '9', '10'), "
						+ "stem text, answer text,  "
						+ "difdeg enum('1', '2', '3', '4', '5')  "
						+ ")";
		String creTestPaper = 
				"create table testpaper(id int not null auto_increment primary key,  "
						+ "type enum('1', '2', '3', '4', '5', '6', '7', '8', '9', '10's),"
						+ "score int, "
						+ "examtime timestamp default CURRENT_TIMESTAMP   "
						+ ")";
		String crePqConnection = 
				"create table pqconnection(qid int, "
						+ "qscore int, "
						+ "pid int, "
						+ "foreign key (qid) references questions(id), "
						+ "foreign key (pid) references testpaper(id)  "
						+ ")";
		String creUsers = 
				"create table users(id int not null auto_increment primary key, "
						+ "username varchar(20), "
						+ "password varchar(50), "
						+ "active boolean   "
						+ ")";
		String creAnswering = 
				"create table answering("
						+ "id int not null auto_increment primary key,"
						+ "uid int not null,"
						+ "qid int,"
						+ "pid int,"
						+ "answer text,"
						+ "atime timestamp default CURRENT_TIMESTAMP,"
						+ " score int,"
						+ "foreign key (uid) references users(id),"
						+ "foreign key (qid) references questions(id),"
						+ "foreign key (pid) references testpaper(id)"
						+ ")";

		this.ps = this.conn.prepareStatement(creQuestions);
		flag = this.ps.execute();

		this.ps = this.conn.prepareStatement(creTestPaper);
		flag = this.ps.execute();

		this.ps = this.conn.prepareStatement(crePqConnection);
		flag = this.ps.execute();

		this.ps = this.conn.prepareStatement(creUsers);
		flag = this.ps.execute();

		this.ps = this.conn.prepareStatement(creAnswering);
		flag = this.ps.execute();

		return flag;
	}
}
