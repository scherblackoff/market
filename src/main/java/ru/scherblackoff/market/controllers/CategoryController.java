package ru.scherblackoff.market.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.scherblackoff.market.models.Category;
import ru.scherblackoff.market.sevices.CategoryService;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String allCategories(Model model){
        model.addAttribute("categories", categoryService.getAll());
        return "category";
    }

    @GetMapping("/category/{id}")
    public String showProductsByCategory(Model model, @PathVariable("id") Long id){
        Category category = categoryService.get(id);
        model.addAttribute("products", category.getProducts());
        model.addAttribute("category", category.getName());
        return "category_products";
    }
}
