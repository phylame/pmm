package pmm.pbm.service.support;

import java.util.List;
import java.util.function.Supplier;

import com.github.pagehelper.PageHelper;

import lombok.val;
import pmm.pbm.data.base.support.Entity;
import pmm.pbm.data.base.support.Example;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.PageHelperAdapter;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.service.params.PagingDTO;

public interface CrudService<E extends Entity, M extends Example, I> extends CommonService {
    Mapper<E, M, I> getMapper();

    default int countByExample(M example) {
        return getMapper().countByExample(example);
    }

    default int deleteByExample(M example) {
        val entity = selectOneByExample(example);
        if (entity == null || entity.getDeleted()) {
            return 0;
        }
        entity.setDeleted(true);
        return updateByIdSelective(entity);
    }

    default int deleteById(I id) {
        val entity = selectById(id);
        if (entity == null || entity.getDeleted()) {
            return 0;
        }
        entity.setDeleted(true);
        return updateByIdSelective(entity);
    }

    default int insert(E entity) {
        return getMapper().insert(entity);
    }

    default int insertSelective(E entity) {
        return getMapper().insertSelective(entity);
    }

    default List<E> selectByExample(M example) {
        return getMapper().selectByExample(example);
    }

    default E selectOneByExample(M example) {
        example.setLimit(1);
        val results = selectByExample(example);
        return results.isEmpty() ? null : results.get(0);
    }

    default E selectById(I id) {
        val entity = getMapper().selectByPrimaryKey(id);
        return isExisted(entity) ? entity : null;
    }

    default int updateByExampleSelective(E entity, M example) {
        return getMapper().updateByExampleSelective(entity, example);
    }

    default int updateByExample(E entity, E example) {
        return getMapper().updateByExample(entity, example);
    }

    default int updateByIdSelective(E entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    default int updateById(E entity) {
        return getMapper().updateByPrimaryKey(entity);
    }

    @SuppressWarnings("hiding")
    default <E> List<E> selectLimited(Supplier<List<E>> query, int limit) {
        if (limit > 0) {
            PageHelper.startPage(1, limit);
        }
        return query.get();
    }

    @SuppressWarnings("hiding")
    default <E> Paged<E> selectPaged(Supplier<List<E>> query, PagingDTO dto) {
        PageHelper.startPage(dto.getOffset(), dto.getLimit());
        return new PageHelperAdapter<>(query.get());
    }

    @SuppressWarnings("hiding")
    default <E> Paged<E> selectPaged(Supplier<List<E>> query, int offset, int limit) {
        PageHelper.startPage(offset, limit);
        return new PageHelperAdapter<>(query.get());
    }

}
