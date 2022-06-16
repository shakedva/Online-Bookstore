package hac.ex4.repo;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.Double.parseDouble;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "The amount must be a positive number")
    private double amount;

    @CreationTimestamp()
    private LocalDateTime dateCreated;

    private String name;

    public Payment(double amount, String name) {
//        this.amount = amount;
        this.amount =  parseDouble(String.format("%.2f", amount));
        this.name = name;
    }

    public Payment() {
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount =  parseDouble(String.format("%.2f",amount));
    }

    public void setDateCreated(LocalDateTime dateTime) { this.dateCreated = dateTime; }

    public LocalDateTime getDateCreated() {return this.dateCreated;}

    public void setName(String name) { this.name = name; }

    public String getName() {return this.name;}

}
