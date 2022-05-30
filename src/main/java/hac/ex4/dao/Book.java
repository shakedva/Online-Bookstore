package hac.ex4.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String image = "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg";
    private int quantity;
    private double price;
    private double discount = 0;

    public Book() {}

    public Book (String name, String image, int quantity, double price, double discount) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) { this.price = price; }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) { this.discount = discount; }
}