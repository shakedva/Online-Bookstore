package hac.ex4.admin;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;

import javax.validation.Valid;


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
//        model.addAttribute("currEdit", 0);

        return "adminEdit";
    }

    @PostMapping("/addBook")
    public String adminAddBook(@Valid Book book, BindingResult result, Document doc, Model model)
    {
//        model.addAttribute("currEdit", 0);
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

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = bookService
                .getBook(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        bookService.deleteBook(book);
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("errors", false);
//        model.addAttribute("currEdit", 0);

        return "redirect:/admin/edit";
    }

    @PostMapping("/editBook")
    public String editUser(@RequestParam("id") long id, Model model) {
        Book book  = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "updateBook";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            model.addAttribute("errors", true);
            return "updateBook";
        }

        bookService.saveBook(book);
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("errors", false);
        return "redirect:/admin/edit";
    }

//
//    @GetMapping("/edit/{id}")
//    public String editBook(@PathVariable("id") long id, Model model) {
//        Book book = bookService
//                .getBook(id)
//                .orElseThrow(
//                        () -> new IllegalArgumentException("Invalid book Id:" + id)
//                );
//        model.addAttribute("currEdit", id);
//        model.addAttribute("books", bookService.getBooks());
//        model.addAttribute("errors", false);
//        return "adminEdit";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("books", bookService.getBooks());
//            model.addAttribute("errors", true);
//            model.addAttribute("currEdit", id);
//            return "adminEdit";
//        }
//
//        bookService.saveBook(book);
//        model.addAttribute("books", bookService.getBooks());
//        model.addAttribute("errors", false);
//        model.addAttribute("currEdit", 0);
//        return "redirect:/admin/edit";
//    }

}
