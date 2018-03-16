package cn.edu.nuist.testSystem.beans;


/**数据库pqconnection表的bean类
 * 用以表示该表的各个字段信息
 * @author Timlong
 * @version V1.0
 */
public class PqConnection {
	private int id;
	private int qid;
	private int qscore;
	private int pid;
	
	/**
	 * 该类的无参构造方法
	 */
	public PqConnection() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public int getQscore() {
		return qscore;
	}
	public void setQscore(int qscore) {
		this.qscore = qscore;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	/**
	 * 返回该bean的数据信息
	 * @return 数据信息字符串
	 */
	@Override
	public String toString(){
		return "ID:" + this.getId() + 
			   "Question ID:" + this.getQid() + 
			   "Question's score in test paper:" + this.getQscore() + 
			   "TestPaper ID:" + this.getPid();
	}
}
