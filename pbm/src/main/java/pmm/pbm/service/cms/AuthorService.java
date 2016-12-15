package pmm.pbm.service.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Author;
import pmm.pbm.data.base.entity.AuthorExample;
import pmm.pbm.data.base.iface.AuthorMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.data.dao.iface.AuthorDAO;
import pmm.pbm.service.params.ListAuthorDTO;
import pmm.pbm.service.results.AuthorVO;
import pmm.pbm.service.support.CrudService;
import pmm.pbm.util.cms.GetMethod;
import pmm.pbm.util.cms.ListMethod;

@Service("cmsAuthorService")
public class AuthorService implements CrudService<Author, AuthorExample, Integer> {
    @Autowired
    private AuthorMapper mapper;

    @Autowired
    private AuthorDAO authorDAO;

    @Override
    public Mapper<Author, AuthorExample, Integer> getMapper() {
        return mapper;
    }

    @ListMethod
    public Paged<AuthorVO> getAuthors(@NonNull ListAuthorDTO dto) {
        return selectPaged(() -> authorDAO.getAuthors(dto), dto);
    }

    @GetMethod
    public AuthorVO getAuthorById(@NonNull String id) {
        val dto = new ListAuthorDTO();
        dto.setId(id);
        val results = authorDAO.getAuthors(dto);
        return results.isEmpty() ? null : results.get(0);
    }

}
