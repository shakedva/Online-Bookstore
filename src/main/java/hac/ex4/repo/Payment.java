package hac.ex4.repo;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDateTime;

import static java.lang.Double.parseDouble;

/**
 * Database for Payments
 */
@Entity
public class Payment {

    /**
     * Serial number of the payment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The amount paid
     */
    @DecimalMin(value = "0.0", inclusive = false,
            message = "The amount must be a positive number")
    private double amount;

    /**
     * The date and time the payment occurred
     */
    @CreationTimestamp()
    private LocalDateTime dateCreated;

    /**
     * The username of the person who made the payment
     */
    private String name;

    /**
     *  Constructor
     * @param amount - the amount paid
     * @param name - the username of the person who made the payment
     */
    public Payment(double amount, String name) {
        this.amount =  parseDouble(String.format("%.2f", amount));
        this.name = name;
    }

    /**
     * Default constructor
     */
    public Payment() {}

    /**
     * @return payment id
     */
    public long getId() { return id; }

    /**
     * @return the amount paid
     */
    public double getAmount() { return amount; }

    /**
     * sets the amount
     * @param amount - the amount paid
     */
    public void setAmount(double amount) {
        this.amount =  parseDouble(String.format("%.2f",amount));
    }

    /**
     * sets the date and time
     * @param dateTime - the date and time of the payment
     */
    public void setDateCreated(LocalDateTime dateTime) { this.dateCreated = dateTime; }

    /**
     * @return - the date and time of the payment
     */
    public LocalDateTime getDateCreated() {return this.dateCreated;}

    /**
     * sets the name
     * @param name - the name of the person who made the payment
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return - the name of the person who made the payment
     */
    public String getName() {return this.name;}

}
