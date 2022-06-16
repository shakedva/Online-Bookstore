package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is the repository that handles the access to the database 'Book'.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>  {

    /**
     * @return The query returns a list of 5 books with the biggest discount in the database ordered by descending
     */
    List<Book> findFirst5ByOrderByDiscountDesc();

    /**
     * @param keyword - string of a keyword
     * @return - The query returns a list of books where the name of the book contains the keyword
     */
    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%")
    List<Book> search(String keyword);

    /**
     * The query decrement the quantity of the book received in the database
     * @param id of a book
     */
    @Modifying
    @Query("UPDATE Book b set b.quantity = b.quantity - 1 where b.id = :id")
    void updateQuantity(@Param(value = "id") long id);

    /**
     * @param bookIds - list of book ids
     * @return - The query returns a list of books, whose ids received.
     */
    List<Book> findByIdIn(List<Long> bookIds);

}