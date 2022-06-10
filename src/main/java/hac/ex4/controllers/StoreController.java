package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("searchedBooks", bookService.listAll(keyword));
        model.addAttribute("keyword", keyword);
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
        return "redirect:/";
    }
    @GetMapping("/viewCart")
    public String storeCart(Model model, HttpSession session)
    {
        Object sessionCart = session.getAttribute("cart");
        List<Book> booksSessionList = (sessionCart == null) ? new ArrayList<>() : (List<Book>) sessionCart;
        double totalPay = 0;
        List<Long> bookIdList = new ArrayList<>();
        for(int i=0; i< booksSessionList.size(); i++) {
            bookIdList.add(booksSessionList.get(i).getId());
            totalPay += booksSessionList.get(i).getPriceAfterDiscount();
        }
        model.addAttribute("books", bookService.getBooksById(bookIdList));
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
        List<Book> booksList =  (List<Book>) session.getAttribute("cart");

        for (int i=0; i<booksList.size(); i++)
            if(booksList.get(i).getId() == id){
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

    //todo delete for testing
    @GetMapping("/decquantity")
    public String decquantity() {
        Book b = bookService
                .getBook(6)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + 6)
                );
        bookService.decQuantity(b);
        return "redirect:/viewCart";
    }

    @GetMapping("/pay")
    public String storePay(Model model, HttpSession session)
    {
        List<Book> booksList;
        if( session.getAttribute("cart") == null) {
            model.addAttribute("message", "The cart is empty!");
            return "successfulPayment";
        }
        else {
            try {
                booksList = (List<Book>) session.getAttribute("cart");
                bookService.pay(booksList);
                session.removeAttribute("cart");
                model.addAttribute("message", "Successful payment!");
                return "successfulPayment";
            }
            catch (Exception e) {
                model.addAttribute("message", "Unsuccessful payment! Please remove the books that are out of stock");
                return "successfulPayment";
            }

        }
    }

}

//    //todo - decrement all books from book db & sum the amount
//    //       add the payment to the payment db
//    //       empty the session
//    //       redirect to successfulpayment html
//    List<Book> booksList;
//        if( session.getAttribute("cart") == null) {
//                //todo redirect to error html page
//                return "redirect:/viewCart";
//                }
//
//                booksList = (List<Book>) session.getAttribute("cart");
//        if(bookService.pay(booksList))
//        {
//        session.removeAttribute("cart");
//        model.addAttribute("message", "The payment was successful!");
//        }
//        else
//        {
//        for (Book book : booksList)
//        if(!bookService.isInStock(book.getId())) // the book is not in stock
//        deleteBookFromCart(book.getId(), model, session);
//
//        model.addAttribute("message", "Oops!There was a problem with the payment");
//        }
//        return "successfulPayment";
