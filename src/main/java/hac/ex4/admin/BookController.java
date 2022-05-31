package hac.ex4.admin;

import ch.qos.logback.core.net.SyslogOutputStream;
import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.Document;

import javax.validation.Valid;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/admin")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/edit")
    public String adminEditStore(Book book, @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model)
    {
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("errors", false);
        return "adminEdit";
    }

    @PostMapping("/addBook")
    public String adminAddBook(@Valid Book book, BindingResult result, Document doc, Model model)
    {
        if (result.hasErrors()) {
            model.addAttribute("books", bookService.getBooks());
            model.addAttribute("errors", true);
            return "adminEdit";
        }

        bookService.saveBook(book);
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("errors", false);
        return "redirect:/admin/edit";
    }

//
//    @PostMapping("/edit")
//    public String addUser(Book user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "cannot add book";
//        }
//
//        bookRepository.save(user);
//        return "redirect:/admin/edit";
//    }
}
