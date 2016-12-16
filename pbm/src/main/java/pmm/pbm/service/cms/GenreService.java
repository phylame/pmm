package pmm.pbm.service.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pmm.pbm.data.base.entity.Genre;
import pmm.pbm.data.base.entity.GenreExample;
import pmm.pbm.data.base.iface.GenreMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.service.support.CrudService;
import pmm.pbm.util.cms.GetMethod;

@Service("cmsGenreService")
public class GenreService implements CrudService<Genre, GenreExample, Integer> {
    @Autowired
    private GenreMapper genreMapper;

    @Override
    public Mapper<Genre, GenreExample, Integer> getMapper() {
        return genreMapper;
    }

    @GetMethod
    public Genre getGenre(String id) {
        return selectById(Integer.valueOf(id));
    }

}
