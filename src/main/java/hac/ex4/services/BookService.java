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
public class BookService
{
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
    public void deleteBook(long id) {
        repository.deleteById(id);
    }
    public void deleteBook(Book b) {
        repository.delete(b);
    }

    public List<Book> get5topDiscount()
    {
        return repository.findFirst5ByOrderByDiscountDesc();
    }

    @Transactional
    public void decQuantity(Book b)
    {
        repository.updateQuantity(b.getId());
    }


    public List<Book> listAll(String keyword) {
        if (keyword != null) {
            return repository.search(keyword);
        }
        return repository.findAll();
    }
    public boolean isInStock (long id) // todo delete?
    {
        Book book = getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        return book.getQuantity() != 0;
    }

    @Transactional
    public void pay(List<Book> books)
    {
        double totalPay = 0;
        for (Book book : books)
        {
            totalPay+=book.getPriceAfterDiscount();
            repository.updateQuantity(book.getId());
//            if(checkBookValidity(book.getId()))
            saveBook(repository.getById(book.getId()));
        }
        System.out.println("total pay: " + totalPay);
        Payment p = new Payment(totalPay);
        paymentRepository.save(p);
    }
//    public boolean checkBookValidity(long id, @Valid Book book, BindingResult result)
//    {
//        return !result.hasErrors();
//    }

}


//    double totalPay = 0;
//        for (Book book : books)
//                {
//                totalPay += book.getPriceAfterDiscount();
////            Book dbbook  = getBook(book.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + book.getId()));
////            if(dbbook.getQuantity() == 0)
////                return false;
//
////            if(!isInStock(book.getId()))
////                return false;
//////            book.decrementQuantity();
//
//                repository.updateQuantity(book); // todo check if works
//                saveBook(book);
//                }
//                paymentRepository.save(new Payment(totalPay));
//                return true;