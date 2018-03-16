package cn.edu.nuist.testSystem.testInterfaceDAO;

import java.util.List;

import java.util.Set;

/**
 * 数据库操作标准公共父接口
 * @author Timlong
 * @version 1.0
 * @param <K>
 * @param <V>
 */
public interface IBase<K, V> {

	/**
	 * 数据库"插"
	 * @param vo 插入数据对象
	 * @return 插入是否成功
	 * @throws Exception
	 */
	public boolean doInsert(V vo) throws Exception;

	/**
	 * 数据库"改"
	 * @param  vo 更改数据对象
	 * @return 更改是否成功
	 * @throws Exception
	 */
	public boolean doUpdate(V vo) throws Exception;


	/**
	 * 数据库"删"
	 * @param ids 批量删除id集合
	 * @return 批量删除是否成功
	 * @throws Exception
	 */
	public boolean doRemoveBtch(Set<K> ids) throws Exception;

	/**
	 * 数据库"查"，通过表的主键id查
	 * @param id 单独删除id
	 * @return 单独删除是否成功
	 * @throws Exception
	 */
	public V findById(K id) throws Exception;

	/**
	 * 数据库"查",查找数据库中所记录
	 * @return 搜索结果列表
	 * @throws Exception
	 */
	public List<V> findAll() throws Exception;

	/**
	 * 查找数据库中相应字段具有相应关键词信息的数量
	 * @param  column 数据库字段
	 * @param   keyWord 字段内容关键词
	 * @return 查询数量
	 * @throws Exception
	 */
	public Integer getAllCount(String column, String keyWord) throws Exception;

}
