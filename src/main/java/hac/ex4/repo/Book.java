package hac.ex4.repo;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.sql.SQLException;

@Entity
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "Image is mandatory")
    private String image;

    @Min(value = 0,
        message = "The quantity must be an integer number")
    private int quantity;

    @DecimalMin(value = "0.0",  inclusive = false,
                message = "The price must be a positive floating point number")
    private double price;

    @DecimalMin(value = "0.0",
            message = "The discount must be a floating point number")
    @DecimalMax(value = "100.0", inclusive = false,
            message = "The discount cannot be bigger than 100%")
    private double discount = 0;

    public Book() {}

    public Book (String name, String image, int quantity, double price, double discount) {
        this.name = name.trim();
        this.image = image.trim();
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name.trim(); }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        this.image = image.trim().equals("") ? "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg" : image.trim();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) { this.price = price;}

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) { this.discount = discount; }

    public void setId(long id) { this.id = id; }

    public double getPriceAfterDiscount() {
        return this.price - this.price * this.discount / 100;
    }


}