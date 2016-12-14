package pmm.pbm.data.base.support;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Base abstraction for mapper.
 *
 * @param <E>
 *            the entity
 * @param <M>
 *            the example
 * @param <K>
 *            the primarily key
 *
 * @author PW[<a href="mailto:phylame@163.com">phylame@163.com</a>]
 */
public interface Mapper<E extends Entity, M extends Example, K> {

    int countByExample(M example);

    int deleteByExample(M example);

    int deleteByPrimaryKey(K key);

    int insert(E entity);

    int insertSelective(E entity);

    List<E> selectByExample(M example);

    E selectByPrimaryKey(K key);

    int updateByExampleSelective(@Param("record") E entity, @Param("example") M example);

    int updateByExample(@Param("record") E entity, @Param("example") E example);

    int updateByPrimaryKeySelective(E entity);

    int updateByPrimaryKey(E entity);
}
