package pmm.pbm.service.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Genre;
import pmm.pbm.data.base.entity.GenreExample;
import pmm.pbm.data.base.iface.GenreMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.data.dao.iface.GenreDAO;
import pmm.pbm.service.params.ListGenreDTO;
import pmm.pbm.service.results.GenreVO;
import pmm.pbm.service.support.CrudService;

@Service("cmsGenreService")
public class GenreService implements CrudService<Genre, GenreExample, Integer> {
    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private GenreDAO genreDAO;

    @Override
    public Mapper<Genre, GenreExample, Integer> getMapper() {
        return genreMapper;
    }

    @Transactional
    public Paged<GenreVO> getGenrePaged(@NonNull ListGenreDTO dto) {
        return selectPaged(() -> genreDAO.getGenres(dto), dto);
    }

    @Transactional
    public GenreVO getGenreById(@NonNull String id) {
        val dto = new ListGenreDTO();
        dto.setId(id);
        dto.setLimit(1);
        val results = genreDAO.getGenres(dto);
        return results.isEmpty() ? null : results.get(0);
    }

}
