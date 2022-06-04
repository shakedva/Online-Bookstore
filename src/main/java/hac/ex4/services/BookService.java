package hac.ex4.services;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService
{
    @Autowired
    private BookRepository repository;

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
        return repository.findFirst5ByDiscount();
    }

}
