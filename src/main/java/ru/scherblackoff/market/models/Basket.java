package ru.scherblackoff.market.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.scherblackoff.market.exeptions.ProductNotFoundException;
import ru.scherblackoff.market.sevices.ProductService;

import java.util.ArrayList;
import java.util.List;


@Component
public class Basket {

    private List<Product> basket;

    private int totalPrice = 0;

    private int quantityProducts = 0;

    public Basket() {
        basket = new ArrayList<>();
    }

    public Product addProduct(Product product) {
        if (product == null){
            throw new ProductNotFoundException("Продукт для добавления не был указан");
        }
        totalPrice += product.getPrice();
        quantityProducts++;
        product.setId((long) quantityProducts);
        basket.add(product);
        return product;
    }

    public void removeProduct(Long id) {
        totalPrice -= basket.get(Math.toIntExact(id - 1)).getPrice();
        basket.remove(Math.toIntExact(id - 1));
        quantityProducts--;
    }

    public List<Product> getAll(){
        return basket;
    }
}

