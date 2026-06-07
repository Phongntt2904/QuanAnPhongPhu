package com.example.webbanhang.controller;

import com.example.webbanhang.repository.CategoryRepository;
import com.example.webbanhang.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("listProducts", productRepository.findByNameContainingIgnoreCase(keyword));
        } else {
            model.addAttribute("listProducts", productRepository.findAll());
        }
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }
}
