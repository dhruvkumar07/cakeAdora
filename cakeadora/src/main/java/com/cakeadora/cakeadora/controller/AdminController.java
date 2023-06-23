package com.cakeadora.cakeadora.controller;

import com.cakeadora.cakeadora.dto.ProductDTO;
import com.cakeadora.cakeadora.model.Category;
import com.cakeadora.cakeadora.model.Product;
import com.cakeadora.cakeadora.services.CategoryService;
import com.cakeadora.cakeadora.services.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(@NotNull Model model){
        String categories = "categories";
        model.addAttribute(categories, categoryService.getCategories());
        return categories;
    }

    @GetMapping("/admin/categories/add")
    public String getCategoryAdd(@NotNull Model model){
        model.addAttribute("category" , new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoryAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String removeById(@PathVariable int id){
        categoryService.removeById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateById(@PathVariable int id , Model model){
        Optional<Category> category = categoryService.updateById(id);
        if(category.isPresent()){
            model.addAttribute("category" , category.get());
            return "categoriesAdd";
        }
        else return "404";
    }

    //Product Layer Control
    @GetMapping("/admin/products")
    public String getProducts(@NotNull Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String addProduct(@NotNull Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories" , categoryService.getCategories());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProductPost(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException{


        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path path = Paths.get(uploadDir , imageUUID);
            Files.write(path , file.getBytes());
        }
        else{
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id , Model model){
        Product product = productService.getProduct(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories" , categoryService.getCategories());
        model.addAttribute("productDTO" , productDTO);
        return "productsAdd";
    }
}
