package cn.edu.nuist.testSystem.utils;

/**
 * 用来根据用户上传的文件名判断试题类型的类
 * @author Timlong
 * @version 1.0
 */
public class QuestionTypeTransition {

	/**
	 * 根据用户上传的excel文件的名称判断该试题的类型
	 * @param typeStr 上传文件的名称
	 * @return typeInt 试题类型 
	 */
	public static Integer judgeQuestionType(String typeStr) {
		String quesType = typeStr.substring(0, typeStr.indexOf(".")).trim();
		Integer typeInt;

		switch(quesType) {
		case "单选题" : typeInt = 1; break;
		case "多选题" : typeInt = 2; break;
		case "填空题" : typeInt = 3; break;
		case "简答题" : typeInt = 4; break;
		case "问答题" : typeInt = 5; break;
		case "计算题" : typeInt = 6; break;
		default:
			typeInt = 10; break;
		}
		return typeInt;
	}
}
