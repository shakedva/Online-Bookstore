package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class StoreController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String adminEditStore(Book book, Model model)
    {
        model.addAttribute("discountBooks", bookService.get5topDiscount());
        return "bookStore";
    }
}
