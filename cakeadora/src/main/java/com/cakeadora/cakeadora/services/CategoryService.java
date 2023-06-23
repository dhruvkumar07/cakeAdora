package com.cakeadora.cakeadora.services;

import com.cakeadora.cakeadora.model.Category;
import com.cakeadora.cakeadora.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }
    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void removeById(int id){
        categoryRepository.deleteById(id);
    }

    public Optional<Category> updateById(int id){
        return categoryRepository.findById(id);
    }
}
