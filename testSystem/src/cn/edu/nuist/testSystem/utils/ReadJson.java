package cn.edu.nuist.testSystem.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest请求对象携带数据处理类
 * @author Timlong
 * @version 1.0
 */
public class ReadJson {
	/**
	 * 读取HttpServletRequest 请求对象携带的json数据
	 * @param request HttpServletRequest对象
	 * @return 前端传回的json字符串
	 */
	public static String getJsonString(HttpServletRequest request){
		StringBuffer json = new StringBuffer();
		BufferedReader reader;
		try {
			reader = request.getReader();
			String line = null;
			while(null != (line = reader.readLine())){
				json.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
