package pmm.pbm.data.dao.iface;

import java.util.List;

import org.springframework.stereotype.Repository;

import pmm.pbm.service.params.ListTagDTO;
import pmm.pbm.service.results.ListTagVO;

@Repository
public interface TagDAO {
    List<ListTagVO> getTags(ListTagDTO dto);
}
