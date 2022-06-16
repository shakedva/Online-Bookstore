package hac.ex4.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the book store cart of a user, who's represented by a session
 */
@Component
public class Cart implements Serializable {

    /**
     * a cart is an array of book id's
     */
    private ArrayList<Long> cart;

    /**
     * the Cart ctor
     */
    public Cart() {
        this.cart = new ArrayList<>();
    }

    /**
     * cart getter
     * @return the cart
     */
    public ArrayList<Long>  getCart() { return cart; }
    /**
     * cart setter - sets a cart
     */
    public void setCart(ArrayList<Long>  cart) {this.cart = cart; }

    /**
     * @param bookId - adding a new book to the cart
     */
    public void add (Long bookId) { cart.add(bookId); }

    /**
     * empty all the books that are in the cart
     */
    public void clear () { cart.clear(); }

    /**
     *  BEAN using ctor - session scope
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Cart sessionCartBean () {
        return new Cart();
    }
}