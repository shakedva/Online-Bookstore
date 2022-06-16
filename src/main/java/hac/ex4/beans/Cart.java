package hac.ex4.beans;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;

@Component
public class Cart implements Serializable {


    private ArrayList<Long> cart;

    public Cart() {
        this.cart = new ArrayList<>();
    }

    public ArrayList<Long>  getCart() { return cart; }

    public void setCart(ArrayList<Long>  cart) {this.cart = cart; }

    public void add (Long bookId) { cart.add(bookId); }

    public void clear () { cart.clear(); }



    /* BEAN using ctor - session scope */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS) // @sessionscope
    public Cart sessionCartBean () {
        return new Cart();
    }
}
