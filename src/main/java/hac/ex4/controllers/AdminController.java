package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

//TODO ADD A JAVADOC FOLDER

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String adminEditStore(Book book, @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model)
    {
        double totalPayments = bookService.getPayments().size() == 0 ? 0 : bookService.sumPayments();
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("payments", bookService.getPayments());
        model.addAttribute("totalPayments", String.format("%.2f", totalPayments));
        model.addAttribute("errors", false);
        return "admin/adminEdit";
    }


    @PostMapping("/addBook")
    public String adminAddBook(@Valid Book book, BindingResult result, Model model)
    {
        if (result.hasErrors()) {
            double totalPayments = bookService.getPayments().size() == 0 ? 0 : bookService.sumPayments();
            model.addAttribute("books", bookService.getBooks());
            model.addAttribute("payments", bookService.getPayments());
            model.addAttribute("totalPayments", String.format("%.2f", totalPayments));
            model.addAttribute("errors", true);
            return "admin/adminEdit";
        }
        bookService.saveBook(book);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = bookService
                .getBook(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        bookService.deleteBook(book);
        return "redirect:/admin";
    }

    @PostMapping("/editBook")
    public String editUser(@RequestParam("id") long id, Model model) {
        Book book  = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "admin/updateBook";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            model.addAttribute("errors", true);
            return "admin/updateBook";
        }

        bookService.saveBook(book);
        return "redirect:/admin";
    }

}
