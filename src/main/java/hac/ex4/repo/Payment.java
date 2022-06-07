package hac.ex4.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @DecimalMin(value = "0", inclusive = false,
        message = "The amount must be a positive number")
    private double amount;

//    @CreationTimestamp
//    private java.sql.Timestamp datetime;

    public Payment(double amount) {
        this.amount = amount;
    }
    public Payment() {}

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

}
