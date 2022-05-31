package hac.ex4.admin;

import ch.qos.logback.core.net.SyslogOutputStream;
import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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
        return "adminEdit";
    }

    @PostMapping("/addBook")
//    public String adminAddBook(@RequestParam MultiValueMap<String, String> values)
    public String adminAddBook(Book book, Model model)
    {
        try {
            bookService.saveBook(book);
            model.addAttribute("books", bookService.getBooks());
        } catch (Exception e)
        {
            System.out.println("cannot add book: " + book.getName());
        }
//        System.out.println("~~Book name: = " + values.get("bookName"));
//        Validator v = new Validator();
//        if(!v.validateBook(values)) {
//            System.out.println("book is not correct");
//            return "adminEdit";
//        }
//        Book b = new Book(String.valueOf(values.get("bookName").get(0)).trim(),
//                    String.valueOf(values.get("bookImage").get(0)),
//                    parseInt(String.valueOf(values.get("bookQuantity").get(0))),
//                    parseDouble(String.valueOf(values.get("bookPrice").get(0))),
//                    parseDouble(String.valueOf(values.get("bookDiscount").get(0))));
//
//        bookRepository.save(b);
//        return new RedirectView("admin/edit");
        return "adminEdit";
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
