package com.cakeadora.cakeadora.controller;

import com.cakeadora.cakeadora.services.CategoryService;
import com.cakeadora.cakeadora.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping({"/" , "/home"})
    public String home(){
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories" , categoryService.getCategories());
        model.addAttribute("products" , productService.getAllProducts());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(@PathVariable int id , Model model){
        model.addAttribute("categories" , categoryService.getCategories());
        model.addAttribute("products" , productService.getAllProductByCategory_Id(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable long id , Model model){
        model.addAttribute("product" , productService.getProduct(id).get());
        return "viewProduct";
    }
}
