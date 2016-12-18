package pmm.pbm.service.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.val;
import pmm.pbm.data.base.entity.Book;
import pmm.pbm.data.base.entity.BookExample;
import pmm.pbm.data.base.iface.BookMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.data.dao.iface.BookDAO;
import pmm.pbm.service.params.ListBookDTO;
import pmm.pbm.service.results.BookVO;
import pmm.pbm.service.support.CrudService;

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

    @Transactional
    public Paged<BookVO> getBookPaged(@NonNull ListBookDTO dto) {
        return selectPaged(() -> bookDAO.getBooks(dto), dto);
    }

    @Transactional
    public BookVO getBookById(@NonNull String id) {
        val dto = new ListBookDTO();
        dto.setId(id);
        dto.setLimit(1);

        val results = bookDAO.getBooks(dto);
        return results.isEmpty() ? null : results.get(0);
    }

}
