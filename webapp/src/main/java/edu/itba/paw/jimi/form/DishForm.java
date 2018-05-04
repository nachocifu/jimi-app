package edu.itba.paw.jimi.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DishForm {

    @Size(min = 1, max = 100)
    //@Pattern(regexp = "[a-zA-Z0-9.]+[a-zA-Z0-9. ]+")
    private String name;

    @Digits(integer = 6, fraction = 2)
    private Float price;

    private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }
}
