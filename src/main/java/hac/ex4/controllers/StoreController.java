package hac.ex4.controllers;

import hac.ex4.beans.Cart;
import hac.ex4.listeners.SessionListenerCounter;
import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.*;

/**
 * a controller for all the actions that can be made by the book store user
 */
@Controller
@RequestMapping("/")
public class StoreController {

    /**
     * with this parameter we can get access to the session
     */
    @Resource(name = "sessionListenerWithMetrics")
    private ServletListenerRegistrationBean<SessionListenerCounter> metrics;

    /**
     * with this variable we can perform actions on our books and payments
     */
    @Autowired
    private BookService bookService;

    /**
     * with this variable we can perform actions on our session bean
     */
    @Resource(name = "sessionCartBean")
    private Cart sessionCartBean;

    /**
     * @param model through this param, we transfer information to other pages
     * @return the main book store page
     */
    @GetMapping("")
    public String store(Model model) {
        model.addAttribute("discountBooks", bookService.get5topDiscount());
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("cartSize", sessionCartBean.getCart().size());

        return "bookStore";
    }

    /**
     * @param keyword a keyword to represent a partial book name to find
     * @param model   through this param, we transfer information to other pages
     * @return the resulted books after the search in a new page
     */
    @PostMapping("/search")
    public String storeSearch(@RequestParam String keyword, Model model) {
        model.addAttribute("searchedBooks", bookService.listAll(keyword));
        model.addAttribute("keyword", keyword);
        return "searchedBooks";
    }

    /**
     * @param id the id of the book whom the user wants to add to the cart
     * @return the main page refreshed, with the cart items updated
     */
    @PostMapping("/addToCart")
    public String storeAddToCart(@RequestParam("id") long id) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        sessionCartBean.add(id);
        return "redirect:/";
    }

    /**
     * @param model through this param, we transfer information to other pages
     * @return the cart page with all the items the user added
     */
    @GetMapping("/viewCart")
    public String storeCart(Model model) {
        List<Long> bookSessionList = sessionCartBean.getCart();
        List<Book> allBooks = bookService.getBooks();
        ArrayList<Long> intersectionBooks = new ArrayList<>();
        LinkedHashMap<Book, Integer> cart = new LinkedHashMap<Book, Integer>();
        double totalPay = 0; //calculates the total sum of all payments
        for (Book book : allBooks) {
            int amount = 0;
            for (Long sessionBookId : bookSessionList)
                if (Objects.equals(sessionBookId, book.getId())) {
                    totalPay += book.getPriceAfterDiscount();
                    intersectionBooks.add(sessionBookId);
                    amount++;
                }
            if (amount > 0)
                cart.put(book, amount);
        }
        sessionCartBean.setCart(intersectionBooks); //update the cart bean session
        model.addAttribute("books", cart);
        model.addAttribute("totalPay", String.format("%.2f", totalPay));
        return "cart";
    }

    /**
     * @param id the book that the user wants to remove from the cart
     * @return the cart page after the book removal - could be an empty cart
     */
    @GetMapping("/removeFromCart/{id}")
    public String deleteBookFromCart(@PathVariable("id") long id) {
        Book book = bookService
                .getBook(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        ArrayList<Long> booksList = sessionCartBean.getCart(); //update the cart bean session
        for (int i = 0; i < booksList.size(); i++)
            if (booksList.get(i) == id) {
                booksList.remove(i); //find the book to remove
                break;
            }
        if (booksList.size() == 0)
            return "redirect:/emptyCart";
        sessionCartBean.setCart(booksList);
        return "redirect:/viewCart";
    }

    /**
     * @return the cart page after it was cleared of all books
     */
    @GetMapping("/emptyCart")
    public String deleteBook() {
        sessionCartBean.clear();
        return "redirect:/viewCart";
    }

    /**
     * @param model     through this param, we transfer information to other pages
     * @param principal holds the spring security username that's currently paying
     * @return a page that shows whether the payment was successfully made or not
     */
    @GetMapping("/pay")
    public String storePay(Model model, Principal principal) {
        ArrayList<Long> booksList = sessionCartBean.getCart();

        if (booksList.size() == 0) {
            model.addAttribute("message", "Your cart is empty!");
            return "afterPayment";
        } else {
            try {
                bookService.pay(booksList, principal.getName());
                sessionCartBean.clear(); //removes all items from cart / session bean
                model.addAttribute("message", "The payment was successful!");
                return "afterPayment";
            } catch (Exception e) {
                model.addAttribute("message", "Unsuccessful payment! Some of your items are not available. Please remove the books that are out of stock");
                return "afterPayment";
            }
        }
    }

}

