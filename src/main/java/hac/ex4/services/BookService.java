package hac.ex4.services;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import hac.ex4.repo.Payment;
import hac.ex4.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * This service handles the repositories for the books and payments
 */
@Service
public class BookService {

    /**
     * The access the Book database is through repository
     */
    @Autowired
    private BookRepository repository;

    /**
     * The access the Payment database is through paymentRepository
     */
    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * receives a book and save it in the database
     * @param book - to save
     */
    public void saveBook(Book book) {
        repository.save(book);
    }

    /**
     * @return - list of all the books in the database
     */
    public List<Book> getBooks() {
        return repository.findAll();
    }

    /**
     * receives book id and returns the book id exist
     * @param id - of a book
     * @return - the book which has the same id
     */
    public Optional<Book> getBook(long id) {
        return repository.findById(id);
    }
//
//  todo delete
//    public List<Book> getBooksById(List<Long> bookIds) {
//        return repository.findByIdIn(bookIds);
//    }
//    public void deleteBook(long id) {
//        repository.deleteById(id);
//    }

    /**
     * @param b - delete the book b from the database
     */
    public void deleteBook(Book b) {
        repository.delete(b);
    }

    /**
     * @return - a list of 5 books with the biggest discount in the database ordered by descending
     */
    public List<Book> get5topDiscount() {
        return repository.findFirst5ByOrderByDiscountDesc();
    }

    /**
     * @return - all the payments
     */
    public List<Payment> getPayments() {
        return paymentRepository.findAllByOrderByDateCreatedAsc();
    }

    /**
     * @return - sum of all the payments
     */
    public double sumPayments() { return paymentRepository.sumPayments(); }

    /**
     * @param keyword - string of a keyword
     * @return - a list of books where the name of the book contains the keyword
     */
    public List<Book> listAll(String keyword) {

        return (keyword != null) ? repository.search(keyword) : repository.findAll();
    }

    /**
     * @param id - of a book
     * @return - if the book is in stock or not
     */
    public boolean isInStock(long id) {
        Book book = getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        return book.getQuantity() > 0;
    }

    /**
     * Makes the payment, if there is a problem with the books or the stock it throws an exception.
     * @param bookIds - list of book ids that the user want to purchase
     * @param name - the user's name
     * @throws Exception - if the book is not in stock anymore or deleted by the admin.
     */
    @Transactional(rollbackOn = Exception.class)
    public void pay(List<Long> bookIds, String name) throws Exception {
        double totalPay = 0;
        // iterate throw the user's shopping cart
        for (Long bookId : bookIds) {
            // check if the book exist in the db
            Book book  = getBook(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book Id"));
            totalPay += book.getPriceAfterDiscount();
            if (isInStock(bookId)) // check if the book is in stock
                repository.updateQuantity(bookId);
            else
                throw new Exception("Book " + bookId + " is not in quantity");
            // save the quantity changes in the db
            saveBook(repository.getById(bookId));
        }
        // save the payment
        Payment p = new Payment(totalPay, name);
        paymentRepository.save(p);
    }
}
