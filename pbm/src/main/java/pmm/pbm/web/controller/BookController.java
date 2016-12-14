package pmm.pbm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import pmm.pbm.service.BookService;
import pmm.pbm.service.params.ListBookDTO;

@Controller
@RequestMapping("/cms/books")
public class BookController extends BaseController {
    @Autowired
    private BookService bookService;

    @RequestMapping({"", "/"})
    public String getBooks(@ModelAttribute("dto") ListBookDTO dto, ModelMap mm) {
        mm.put("books", bookService.getBooks(dto));
        return "books";
    }
}
