package hac.ex4.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * a class to represent a book at the book store
 */
@Entity
public class Book {
    /**
     * the generated id for a book
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * the book title / name
     */
    @NotEmpty(message = "Name is mandatory")
    private String name;

    /**
     * the book image
     */
    @NotEmpty(message = "Image is mandatory")
    private String image;

    /**
     * the quantity of that book at the book store
     */
    @Min(value = 0,
            message = "The quantity must be an integer number")
    private int quantity;

    /**
     * the price for the book
     */
    @DecimalMin(value = "0.0", inclusive = false,
            message = "The price must be a positive floating point number")
    private double price;

    /**
     * the discount on the book
     */
    @DecimalMin(value = "0.0",
            message = "The discount must be a floating point number")
    @DecimalMax(value = "100.0", inclusive = false,
            message = "The discount cannot be bigger than 100%")
    private double discount = 0;

    /**
     * init ctor
     */
    public Book() {
    }

    /**
     * constructor for a Book
     *
     * @param name     the book title / name
     * @param image    the book image
     * @param quantity the quantity of that book at the book store
     * @param price    the price for the book
     * @param discount the discount on the book
     */
    public Book(String name, String image, int quantity, double price, double discount) {
        this.name = name.trim();
        this.image = image.trim();
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    /**
     * @return the id of a book
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id to set for a book
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the title of a book
     */
    public String getName() {
        return name;
    }

    /**
     * @param name to set for a book
     */
    public void setName(String name) {
        this.name = name.trim();
    }

    /**
     * @return the image of a book
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image to set for a book. If there isn't any, a default one will be assigned
     */
    public void setImage(String image) {

        this.image = image.trim().equals("") ? "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg" : image.trim();
    }

    /**
     * @return the quantity of a book in the book store
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity to set for a book
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price of a book
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price to set for a book
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the discount of a book
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * @param discount to set for a book
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * @return the price of a book after calculating its discount
     */
    public double getPriceAfterDiscount() {
        return this.price - this.price * this.discount / 100;
    }

}