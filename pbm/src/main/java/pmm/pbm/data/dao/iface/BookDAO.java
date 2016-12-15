package pmm.pbm.data.dao.iface;

import java.util.List;

import org.springframework.stereotype.Repository;

import pmm.pbm.service.params.ListBookDTO;
import pmm.pbm.service.results.BookVO;

@Repository
public interface BookDAO {
    List<BookVO> getBooks(ListBookDTO dto);
}
