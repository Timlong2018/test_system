package cn.edu.nuist.testSystem.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**数据库answering表的bean类
 * 用以表示该表的各个字段信息
 * @author Timlong
 * @version V1.0
 */
@SuppressWarnings("serial")
public class Answering implements Serializable{
	private int id;
	private int userID;
	private int qID;
	private int pID;
	private String answer;
	private Timestamp aTime;
	private int score;

	/**
	 * 该类的无参构造方法
	 */
	public Answering() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getqID() {
		return qID;
	}
	public void setqID(int qID) {
		this.qID = qID;
	}
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Timestamp getaTime() {
		return aTime;
	}
	public void setaTime(Timestamp aTime) {
		this.aTime = aTime;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * 返回该bean的数据信息
	 * @return 数据信息字符串
	 */
	public String toString(){
		String re;
		
		re = "ID:" + this.getId()
		   + "\nUser's ID:	" + this.getUserID()
		   + "\nQuestion's ID:	" + this.getqID()
		   + "\nTest paper's ID:	" + this.getpID()
		   + "\nThis question's answer:	" + this.getAnswer()
		   + "\nScore:" + this.getScore();
		
		return re;
	}
 
	
}
