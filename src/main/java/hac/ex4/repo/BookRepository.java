package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>  {

    List<Book> findFirst5ByOrderByDiscountDesc();

    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%")
    List<Book> search(String keyword);

    @Modifying
    @Query("UPDATE Book b set b.quantity = b.quantity - 1 where b.id = :id") //where quantity > 0  ~~ and b.quantity > 0
    void updateQuantity(@Param(value = "id") long id);

}