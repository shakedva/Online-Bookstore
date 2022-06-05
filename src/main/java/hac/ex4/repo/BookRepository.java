package hac.ex4.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    //@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
    //            + " OR p.brand LIKE %?1%"
    //            + " OR p.madein LIKE %?1%"
    //            + " OR CONCAT(p.price, '') LIKE %?1%")
}
