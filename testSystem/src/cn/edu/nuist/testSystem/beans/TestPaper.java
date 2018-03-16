package cn.edu.nuist.testSystem.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**数据库testpaper表的bean类
 * 用以表示该表的各个字段信息
 * @author Timlong
 * @version V1.0
 */
@SuppressWarnings("serial")
public class TestPaper implements Serializable{
	private int id;
	private String name;
	private int type;
	private int score;
	private Timestamp examtime;
	private Map<Questions, Integer> map;  //Integer-->qscore

	/**
	 * 该类的无参构造方法
	 */
	public TestPaper() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Timestamp getExamtime() {
		return examtime;
	}
	public void setExamtime(Timestamp examtime) {
		this.examtime = examtime;
	}
	public Map<Questions, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Questions, Integer> map) {
		this.map = map;
	}
	
	/**
	 * 返回该bean的数据信息
	 * @return 数据信息字符串
	 */
	@Override
	public String toString(){
		String re;
		
		re = "ID:	" + this.getId()
		   + "\nName:" + this.getName()
		   + "\nType:	" + this.getType()
		   + "\nScore:	" + this.getScore()
		   + "\nExam Time Limit:	" +this.getExamtime();
		
		return re;
	}
	

}
