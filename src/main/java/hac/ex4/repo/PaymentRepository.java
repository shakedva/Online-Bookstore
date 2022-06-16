package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is the repository that handles the access to the database 'Payment'.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * @return - The query returns all the payments ordered by date
     */
    List<Payment> findAllByOrderByDateCreatedAsc();

    /**
     * @return - The query returns the sum of all the payments
     */
    @Query("SELECT SUM(p.amount) FROM Payment p")
    double sumPayments();
}
