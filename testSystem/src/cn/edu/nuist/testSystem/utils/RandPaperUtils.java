package cn.edu.nuist.testSystem.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.edu.nuist.testSystem.beans.Questions;

/**
 * 随机生成试卷的工具类
 * @author Timlong
 * @version 1.0
 */
public class RandPaperUtils {

	/**
	 * 获取10个随机数
	 * @return 十个随机生成数的字符串
	 */
	public String getTenRandNum(){
		Set<Integer> results = new HashSet<Integer>();
		while(results.size() < 10){
			int num = (int)(Math.random() * 12) + 1;
			results.add(num);
		}
		return results.toString().replace('[', '(').replace(']', ')');
	}

	/**
	 * 将数据库查询的结果，放到试题和其所占分值的键值对中
	 * @param map 试题和其所占分值的键值
	 * @param rs 数据库查询的结果集
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void addResultSet(Map map, ResultSet rs) throws SQLException{
		while(rs.next()){
			Questions vo = new Questions();
			vo.setId(rs.getInt(1));
			vo.setModule(rs.getInt(2));
			vo.setType(rs.getInt(3));
			vo.setStem(rs.getString(4));
			vo.setAnswer(rs.getString(5));
			vo.setDifdeg(rs.getInt(6));
			//试题的分值'3'待修改
			map.put(vo, 3);
		}
	}
}
