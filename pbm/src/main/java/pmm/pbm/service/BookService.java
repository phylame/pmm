package pmm.pbm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Book;
import pmm.pbm.data.base.entity.BookExample;
import pmm.pbm.data.base.iface.BookMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.data.dao.iface.BookDAO;
import pmm.pbm.service.params.ListBookDTO;
import pmm.pbm.service.results.ListBookVO;
import pmm.pbm.service.support.CrudService;
import pmm.pbm.util.cms.GetMethod;
import pmm.pbm.util.cms.ListMethod;

@Service("cmsBookService")
public class BookService implements CrudService<Book, BookExample, Integer> {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookDAO bookDAO;

    @Override
    public Mapper<Book, BookExample, Integer> getMapper() {
        return bookMapper;
    }

    @ListMethod
    public Paged<ListBookVO> getBooks(@NonNull ListBookDTO dto) {
        return selectPaged(() -> bookDAO.getBooks(dto), dto);
    }

    @GetMethod
    public ListBookVO getBookById(@NonNull String id) {
        val dto = new ListBookDTO();
        dto.setId(id);
        val results = bookDAO.getBooks(dto);
        return results.isEmpty() ? null : results.get(0);
    }

}
