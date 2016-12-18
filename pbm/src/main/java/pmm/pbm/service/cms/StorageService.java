package pmm.pbm.service.cms;

import static pw.phylame.ycl.util.StringUtils.isNotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Storage;
import pmm.pbm.data.base.entity.StorageExample;
import pmm.pbm.data.base.iface.StorageMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.service.params.ListFileDTO;
import pmm.pbm.service.support.CrudService;

@Service("cmsFileService")
public class StorageService implements CrudService<Storage, StorageExample, Integer> {

    @Autowired
    private StorageMapper mapper;

    @Override
    public Mapper<Storage, StorageExample, Integer> getMapper() {
        return mapper;
    }

    @Transactional
    public Paged<Storage> getFilePaged(@NonNull ListFileDTO dto) {
        val example = new StorageExample();
        val criteria = example.getCriteria().andDeletedEqualTo(false);
        if (isNotEmpty(dto.getId())) {
            try {
                criteria.andIdEqualTo(Integer.valueOf(dto.getId()));
            } catch (NumberFormatException ignored) {

            }
        }
        if (isNotEmpty(dto.getMd5())) {
            criteria.andMd5Like('%' + dto.getMd5() + '%');
        }
        if (isNotEmpty(dto.getName())) {
            criteria.andNameLike('%' + dto.getName() + '%');
        }
        return selectPaged(() -> selectByExample(example), dto);
    }

    @Transactional
    public Storage getFileById(@NonNull String id) {
        try {
            return selectById(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
