package pmm.pbm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import pmm.pbm.data.base.entity.Book;
import pmm.pbm.data.base.entity.BookExample;
import pmm.pbm.data.base.iface.BookMapper;
import pmm.pbm.data.base.support.Mapper;
import pmm.pbm.data.base.support.Paged;
import pmm.pbm.data.dao.iface.BookDAO;
import pmm.pbm.service.params.ListBookDTO;
import pmm.pbm.service.results.ListBookVO;
import pmm.pbm.service.support.CrudService;

@Service
public class BookService implements CrudService<Book, BookExample, Integer> {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookDAO bookDAO;

    @Override
    public Mapper<Book, BookExample, Integer> getMapper() {
        return bookMapper;
    }

    public Paged<ListBookVO> getBooks(@NonNull ListBookDTO dto) {
        return selectPaged(() -> bookDAO.getBooks(dto), dto);
    }

}
