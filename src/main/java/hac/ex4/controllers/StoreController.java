package hac.ex4.controllers;

import hac.ex4.beans.Cart;
import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/")
public class StoreController {

    @Autowired
    private BookService bookService;

    @Resource(name = "sessionCartBean")
    private Cart sessionCartBean;

    @GetMapping("")
    public String store(Model model)
    {
        model.addAttribute("discountBooks", bookService.get5topDiscount());
        model.addAttribute("books", bookService.getBooks());
        model.addAttribute("cartSize", sessionCartBean.getCart().size());

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
    public String storeAddToCart(@RequestParam("id") long id)
    {
        Book book  = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        sessionCartBean.add(id);
        return "redirect:/";
    }

    @GetMapping("/viewCart")
    public String storeCart(Model model)
    {
        List<Long> bookSessionList = sessionCartBean.getCart();
        List<Book> allBooks = bookService.getBooks();
        ArrayList<Long> intersectionBooks = new ArrayList<>();
        LinkedHashMap<Book, Integer> cart = new LinkedHashMap<Book, Integer>();
        double totalPay = 0;
        for (Book book : allBooks) {
            int amount = 0;
            for (Long sessionBookId : bookSessionList)
                if (Objects.equals(sessionBookId, book.getId())) {
                    totalPay += book.getPriceAfterDiscount();
                    intersectionBooks.add(sessionBookId);
                    amount++;
                }
            if(amount > 0)
                cart.put(book, amount);
        }
        sessionCartBean.setCart(intersectionBooks);
        model.addAttribute("books", cart);
        model.addAttribute("totalPay", String.format("%.2f", totalPay));
        return "cart";
    }

    @GetMapping("/removeFromCart/{id}")
    public String deleteBookFromCart(@PathVariable("id") long id) {
        Book book = bookService
                .getBook(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        ArrayList<Long> booksList = sessionCartBean.getCart();
        for (int i=0; i<booksList.size(); i++)
            if(booksList.get(i) == id)
            {
                booksList.remove(i);
                break;
            }
        if(booksList.size() == 0)
            return "redirect:/emptyCart";
        sessionCartBean.setCart(booksList);
        return "redirect:/viewCart";
    }

    @GetMapping("/emptyCart")
    public String deleteBook() {
        sessionCartBean.clear();
        return "redirect:/viewCart";
    }

    @PostMapping("/pay")
    public String storePay(Model model)
    {
        ArrayList<Long> booksList = sessionCartBean.getCart();

        if( booksList.size() == 0) {
            model.addAttribute("message", "Your cart is empty!");
            return "afterPayment";
        }
        else {
            try {
                bookService.pay(booksList);
                sessionCartBean.clear();
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

