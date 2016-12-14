package pmm.pbm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Tag;
import pmm.pbm.data.base.entity.TagExample;
import pmm.pbm.data.base.iface.TagMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.data.dao.iface.TagDAO;
import pmm.pbm.service.params.ListTagDTO;
import pmm.pbm.service.results.ListTagVO;
import pmm.pbm.service.support.CrudService;

@Service
public class TagService implements CrudService<Tag, TagExample, Integer> {

    @Autowired
    private TagMapper mapper;

    @Autowired
    private TagDAO tagDAO;

    @Override
    public Mapper<Tag, TagExample, Integer> getMapper() {
        return mapper;
    }

    public Paged<ListTagVO> getTags(@NonNull ListTagDTO dto) {
        return selectPaged(() -> tagDAO.getTags(dto), dto);
    }

    public ListTagVO getTagById(@NonNull String id) {
        val dto = new ListTagDTO();
        dto.setId(id);
        val results = tagDAO.getTags(dto);
        return results.isEmpty() ? null : results.get(0);
    }

}
