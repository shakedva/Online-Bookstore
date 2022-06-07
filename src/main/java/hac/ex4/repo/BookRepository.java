package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>  {

    @Query("select b from Book b order by b.discount DESC")
    List<Book> findFirst5ByDiscount();

    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%")
    public List<Book> search(String keyword);

}
