package cn.edu.nuist.testSystem.beans;

import java.io.Serializable;

/**数据库questions表的bean类
 * 用以表示该表的各个字段信息
 * @author Timlong
 * @version V1.0
 */
@SuppressWarnings("serial")
public class Questions implements Serializable{

	private int id;      //not null
	private int module = 10;//模块 not null,enum(1~12		default 100)
	private int type;    //试题的类型,  enum(1~10		default 10)
	private String stem;
	private String answer;
	private int difdeg = 1;
	
	/**
	 * 该类的无参构造方法
	 */
	public Questions() {
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getDifdeg() {
		return difdeg;
	}

	public void setDifdeg(int difdeg) {
		this.difdeg = difdeg;
	}

	/**
	 * 返回该bean的数据信息
	 * @return 数据信息字符串
	 */
	public String toString(){
		String re;
		re = "ID:	" + this.getId() 
		+ "\nModule:	" + this.getModule()
		+ "\nType:	" + this.getType() 
		+ "\nStem:	" + this.getStem()
		+ "\nAnswer:	" + this.getAnswer()
		+ "\nDifficulty Level(1~5):	" + this.getDifdeg(); 
		return re;
	}
}
