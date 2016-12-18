package pmm.pbm.data.dao.iface;

import java.util.List;

import org.springframework.stereotype.Repository;

import pmm.pbm.service.params.ListGenreDTO;
import pmm.pbm.service.results.GenreVO;

@Repository
public interface GenreDAO {
    List<GenreVO> getGenres(ListGenreDTO dto);
}
