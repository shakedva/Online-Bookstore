package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
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
        model.addAttribute("books", bookService.getBooks());
        Object sessionCart = session.getAttribute("cart");
        List<Long> booksList = (sessionCart == null) ? new ArrayList<>() : (List<Long>) sessionCart;
        model.addAttribute("cartSize", booksList.size());

        return "bookStore";
    }

    @PostMapping("/search")
    public String storeSearch(@RequestParam String keyword, Model model)
    {
        model.addAttribute("searchedBooks", bookService.listAll(keyword));
        model.addAttribute("keyword", keyword);
        return "searchedBooks";
    }

    @PostMapping("/addToCart")
    public String storeAddToCart(@RequestParam("id") long id, Model model, HttpSession session)
    {
        Book book  = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        Object sessionCart = session.getAttribute("cart");
        List<Long> booksList = (sessionCart == null) ? new ArrayList<>() : (List<Long>) sessionCart;
        booksList.add(id);
        session.setAttribute("cart", booksList);
        return "redirect:/";
    }

    @GetMapping("/viewCart")
    public String storeCart(Model model, HttpSession session)
    {
        Object sessionCart = session.getAttribute("cart");
        List<Long> bookSessionList = (sessionCart == null) ? new ArrayList<>() : (List<Long>) sessionCart;
        List<Book> allBooks = bookService.getBooks();
        List<Long> intersectionBooks = new ArrayList<>();
        double totalPay = 0;
        for (Book book : allBooks) {
            for (Long sessionBookId : bookSessionList)
                if (Objects.equals(sessionBookId, book.getId())) {
                    totalPay += book.getPriceAfterDiscount();
                    intersectionBooks.add(sessionBookId);
                }
        }
        session.setAttribute("cart", intersectionBooks);
        model.addAttribute("books", bookService.getBooksById(intersectionBooks));
        model.addAttribute("totalPay", totalPay);

        return "cart";
    }

    @GetMapping("/removeFromCart/{id}")
    public String deleteBookFromCart(@PathVariable("id") long id, Model model, HttpSession session) {
        Book book = bookService
                .getBook(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        Object sessionCart = session.getAttribute("cart");
        List<Long> booksList = (sessionCart == null) ? new ArrayList<>() : (List<Long>) sessionCart;
        for (int i=0; i<booksList.size(); i++)
            if(booksList.get(i) == id)
            {
                booksList.remove(i);
                break;
            }
        if(booksList.size() == 0)
            return "redirect:/emptyCart";

        session.setAttribute("cart", booksList);
        return "redirect:/viewCart";
    }

    @GetMapping("/emptyCart")
    public String deleteBook(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/viewCart";
    }

    // todo postmapping
    @GetMapping("/pay")
    public String storePay(Model model, HttpSession session)
    {
        Object sessionCart = session.getAttribute("cart");
        List<Long> booksList = (sessionCart == null) ? new ArrayList<>() : (List<Long>) sessionCart;
        if( booksList.size() == 0) {
            model.addAttribute("message", "Your cart is empty!");
            return "afterPayment";
        }
        else {
            try {
                bookService.pay(booksList);
                session.removeAttribute("cart");
                model.addAttribute("message", "The payment was successful!");
                return "afterPayment";
            }
            catch (Exception e) {
                model.addAttribute("message", "Unsuccessful payment! Some of your items are not available. Please remove the books that are out of stock");
                return "afterPayment";
            }
        }
    }

}

