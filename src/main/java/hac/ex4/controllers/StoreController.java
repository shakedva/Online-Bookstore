package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class StoreController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String store(Model model, HttpSession session)
    {
        model.addAttribute("discountBooks", bookService.get5topDiscount());
        model.addAttribute("searchedBooks", bookService.listAll(""));

        List<Book> booksList = (List<Book>) session.getAttribute("cart");
        if(session.getAttribute("cart") == null)
            model.addAttribute("cartSize", 0);
        else
            model.addAttribute("cartSize", booksList.size());

        return "bookStore";
    }

    @PostMapping("/search")
    public String storeSearch(@RequestParam String keyword, Model model)
    {
        System.out.println("keyword received: " + keyword );
        model.addAttribute("searchedBooks", bookService.listAll(keyword));
//        if (result.hasErrors()) {
//
//            model.addAttribute("errors", true);
//            return "bookStore";
//        }
//        model.addAttribute("books", bookService.getBooks());
//        model.addAttribute("errors", false);
//        return "redirect:/admin/"; //todo edit
        return "searchedBooks";
    }

    @PostMapping("/addToCart")
    public String storeAddToCart(@RequestParam("id") long id, Model model, HttpSession session)
    {
        Book book  = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        List<Book> booksList;
        if( session.getAttribute("cart") == null) {
            booksList = new ArrayList<>();
        }
        else {
            booksList = (List<Book>) session.getAttribute("cart");
        }
        booksList.add(book);
        session.setAttribute("cart", booksList);

        System.out.println("booksList: " + session.getAttribute("cart"));

        return "redirect:/";

    }
    @GetMapping("/viewCart")
    public String storeCart(Model model, HttpSession session)
    {
        List<Book> booksList;
        if( session.getAttribute("cart") == null)
            booksList = new ArrayList<>();
        else
            booksList = (List<Book>) session.getAttribute("cart");

        model.addAttribute("books", booksList);
        return "cart";
    }

    @GetMapping("/removeFromCart/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model, HttpSession session) {
        Book book = bookService
                .getBook(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        List<Book> booksList =  (List<Book>) session.getAttribute("cart");

        for (int i=0; i<booksList.size(); i++)
            if(booksList.get(i).getId() == id){
                booksList.remove(i);
                break;
        }
        return "redirect:/viewCart";
    }

    @GetMapping("/emptyCart")
    public String deleteBook(Model model, HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/viewCart";
    }
}
