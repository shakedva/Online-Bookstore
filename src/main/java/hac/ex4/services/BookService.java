package hac.ex4.services;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import hac.ex4.repo.Payment;
import hac.ex4.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;

    public void saveBook(Book book) {
        repository.save(book);
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Optional<Book> getBook(long id) {
        return repository.findById(id);
    }

    public List<Book> getBooksById(List<Long> bookIds) {
        return repository.findByIdIn(bookIds);
    }

    public void deleteBook(long id) {
        repository.deleteById(id);
    }

    public void deleteBook(Book b) {
        repository.delete(b);
    }

    public List<Book> get5topDiscount() {
        return repository.findFirst5ByOrderByDiscountDesc();
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAllByOrderByDateCreatedAsc();
    }

    public double sumPayments() { return paymentRepository.sumPayments(); }

    @Transactional
    public void decQuantity(Book b) {
        repository.updateQuantity(b.getId());
    }


    public List<Book> listAll(String keyword) {

        return (keyword != null) ? repository.search(keyword) : repository.findAll();
    }

    public boolean isInStock(long id) {
        Book book = getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        return book.getQuantity() > 0;
    }

//    @Transactional(rollbackOn = Exception.class)
//    public void pay(List<Book> books) throws Exception {
//        double totalPay = 0;
//        for (Book book : books) {
//            totalPay += book.getPriceAfterDiscount();
//            if (isInStock(book.getId()))
//                repository.updateQuantity(book.getId());
//            else
//                throw new Exception("Book " + book.getId() + " is not in quantity");
//
//            saveBook(repository.getById(book.getId()));
//        }
//        Payment p = new Payment(totalPay);
//        paymentRepository.save(p);
//    }

    @Transactional(rollbackOn = Exception.class)
    public void pay(List<Long> bookIds) throws Exception {
        double totalPay = 0;
        for (Long bookId : bookIds) {
            Book book  = getBook(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book Id"));
            totalPay += book.getPriceAfterDiscount();
            if (isInStock(bookId))
                repository.updateQuantity(bookId);
            else
                throw new Exception("Book " + bookId + " is not in quantity");

            saveBook(repository.getById(bookId));
        }
        Payment p = new Payment(totalPay);
        paymentRepository.save(p);
    }
}
