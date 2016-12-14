package pmm.pbm.data.base.iface;

import pmm.pbm.data.base.entity.Book;
import pmm.pbm.data.base.entity.BookExample;
import pmm.pbm.data.base.support.Mapper;

public interface BookMapper extends Mapper<Book, BookExample, Integer> {
}