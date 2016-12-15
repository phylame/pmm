package pmm.pbm.data.dao.iface;

import java.util.List;

import org.springframework.stereotype.Repository;

import pmm.pbm.service.params.ListTagDTO;
import pmm.pbm.service.results.TagVO;

@Repository
public interface TagDAO {
    List<TagVO> getTags(ListTagDTO dto);
}
