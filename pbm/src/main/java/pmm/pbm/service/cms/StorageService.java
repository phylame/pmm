package pmm.pbm.service.cms;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Storage;
import pmm.pbm.data.base.entity.StorageExample;
import pmm.pbm.data.base.iface.StorageMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.service.params.ListFileDTO;
import pmm.pbm.service.support.CrudService;
import pmm.pbm.util.cms.GetMethod;
import pmm.pbm.util.cms.ListMethod;

@Service("cmsFileService")
public class StorageService implements CrudService<Storage, StorageExample, Integer> {

    @Autowired
    private StorageMapper mapper;

    @Override
    public Mapper<Storage, StorageExample, Integer> getMapper() {
        return mapper;
    }

    @ListMethod
    public Paged<Storage> getFiles(@NonNull ListFileDTO dto) {
        val example = new StorageExample();
        val criteria = example.getCriteria().andDeletedEqualTo(false);
        if (StringUtils.isNoneEmpty(dto.getId())) {
            try {
                criteria.andIdEqualTo(Integer.valueOf(dto.getId()));
            } catch (NumberFormatException ignored) {

            }
        }
        if (StringUtils.isNotEmpty(dto.getMd5())) {
            criteria.andMd5Like('%' + dto.getMd5() + '%');
        }
        if (StringUtils.isNotEmpty(dto.getName())) {
            criteria.andNameLike('%' + dto.getName() + '%');
        }
        return selectPaged(() -> selectByExample(example), dto);
    }

    @GetMethod
    public Storage getFileById(@NonNull String id) {
        try {
            return selectById(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
