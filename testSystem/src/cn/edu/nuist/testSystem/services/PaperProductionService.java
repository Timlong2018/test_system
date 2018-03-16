package cn.edu.nuist.testSystem.services;

/**
 * 处理随机生成试卷业务的公共接口
 * @author Timmlong
 * @version 1.0
 */
public interface PaperProductionService {
	
	/**
	 * 随机从试题库收取每种题型相应数量试题的方法
	 * @param choice_num 选择题数量
	 * @param gap_fill_num 填空题数量
	 * @param answerQues_num 简答题数量
	 * @return 布尔类型，表示方法体中操作是否成功
	 * @throws Exception
	 */
	public boolean extractRandQuestions(Integer choice_num, Integer gap_fill_num, Integer answerQues_num) throws Exception;
}
