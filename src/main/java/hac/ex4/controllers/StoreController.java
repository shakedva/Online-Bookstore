package hac.ex4.controllers;

import hac.ex4.repo.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class StoreController {

    @GetMapping("")
    public String adminEditStore(Book book, @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model)
    {
        return "bookStore";
    }
}
