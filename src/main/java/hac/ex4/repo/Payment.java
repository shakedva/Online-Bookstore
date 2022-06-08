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

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "The amount must be a positive number")
    private double amount;

    @CreationTimestamp()
//    @Default(current_timestamp)
    private LocalDateTime dateCreated;

    public Payment(double amount) {

        this.amount = amount;
    }

    public Payment() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateCreated(LocalDateTime dateTime) {
        this.dateCreated = dateTime;
//        System.out.println("in setDateCreated");
//        ZoneId z = ZoneId.systemDefault();
//        this.dateCreated = ZonedDateTime.now( z );
    }

    public LocalDateTime getDateCreated() {return this.dateCreated;}

}

//  @CreationTimestamp
//    private LocalDateTime dateCreated;
//
//    @CreationTimestamp
////    private java.sql.Timestamp datetime;