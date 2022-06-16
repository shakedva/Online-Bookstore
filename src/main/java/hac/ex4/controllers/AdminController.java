package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * a controller for all the actions that can be made by the book store admin
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * with this variable we can perform actions on our books and payments
     */
    @Autowired
    private BookService bookService;

    /**
     * @param book  parameter to help show the admin page
     * @param name  parameter to help show the admin page
     * @param model through this param, we transfer information to other pages
     * @return the updated html page "admin/adminEdit"
     */
    @GetMapping("")
    public String adminEditStore(Book book, @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        double totalPayments = bookService.getPayments().size() == 0 ? 0 : bookService.sumPayments();
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("payments", bookService.getPayments());
        model.addAttribute("totalPayments", String.format("%.2f", totalPayments));
        model.addAttribute("errors", false);
        return "admin/adminEdit";
    }

    /**
     * @param book   parameter to help show the admin page
     * @param result holds if the action had errors
     * @param model  through this param, we transfer information to other pages
     * @return the relevant page according to the result
     */
    @PostMapping("/addBook")
    public String adminAddBook(@Valid Book book, BindingResult result, Model model) {
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

    /**
     * @param id    the id of the book to delete
     * @param model through this param, we transfer information to other pages
     * @return the main admin page
     */
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

    /**
     * @param id    the id of the book to edit
     * @param model through this param, we transfer information to other pages
     * @return the update page, where we can edit the book attributes
     */
    @PostMapping("/editBook")
    public String editUser(@RequestParam("id") long id, Model model) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "admin/updateBook";
    }

    /**
     * @param id     the book id whom we want to edit
     * @param book the book itself in order to validate its new attributes
     * @param result holds if the action had errors
     * @param model through this param, we transfer information to other pages
     * @return the main admin page after the update has successfully been made
     */
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
