package pmm.pbm.data.dao.iface;

import java.util.List;

import org.springframework.stereotype.Repository;

import pmm.pbm.service.params.ListAuthorDTO;
import pmm.pbm.service.results.AuthorVO;

@Repository
public interface AuthorDAO {
    List<AuthorVO> getAuthors(ListAuthorDTO dto);
}
