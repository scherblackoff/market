package ru.scherblackoff.market.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.scherblackoff.market.models.Basket;
import ru.scherblackoff.market.models.Product;
import ru.scherblackoff.market.sevices.ProductService;
import ru.scherblackoff.market.util.ProductFilter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    private Basket basket;


    @Autowired
    public ProductController(ProductService productService, Basket basket) {
        this.productService = productService;
        this.basket = basket;
    }

    @GetMapping
    public String show(Model model, @RequestParam Map<String, String> requestParams){
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));
        ProductFilter productFilter = new ProductFilter(requestParams);
        Page<Product> products = productService.getAll(productFilter.getSpec(), pageNumber);
        model.addAttribute("products", products);
        model.addAttribute("filterDef", productFilter.getFilterDefinition().toString());
        return "show";
    }

    @GetMapping("/{id}")
    public String showId(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productService.get(id));
        return "product";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") Product product){
        productService.saveOrUpdate(product);
        return "redirect:/products";
    }


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("product", productService.get(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("product") Product modifiedProduct){
        productService.saveOrUpdate(modifiedProduct);
        return "redirect:/products";
    }

    @GetMapping("/basket")
    @ResponseBody
    public List<Product> getAllFromBasket(){
        return basket.getAll();
    }


    @PostMapping("/basket")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Product addInBasket(@ModelAttribute("id") Long id){
        return basket.addProduct(productService.get(id));
    }

    @DeleteMapping("/basket/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable Long id) {
        basket.removeProduct(id);
        return "OK";
    }
}
