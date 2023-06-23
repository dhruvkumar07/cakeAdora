package com.cakeadora.cakeadora.services;

import com.cakeadora.cakeadora.model.Product;
import com.cakeadora.cakeadora.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void removeProductById(long id){
        productRepository.deleteById(id);
    }

    public Optional<Product> getProduct(long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProductByCategory_Id(int id){
        return productRepository.findAllByCategory_Id(id);
    }
}
