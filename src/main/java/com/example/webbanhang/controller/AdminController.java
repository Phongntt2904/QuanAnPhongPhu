package com.example.webbanhang.controller;

import com.example.webbanhang.model.Product;
import com.example.webbanhang.repository.CategoryRepository;
import com.example.webbanhang.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/product/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product_form";
    }

    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute Product product) {
        try {
            productRepository.save(product);
            return "redirect:/admin";
        } catch (Exception e) {
            System.err.println("LỖI KHI LƯU SẢN PHẨM: " + e.getMessage());
            return "redirect:/admin/product/add?error";
        }
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin";
    }
}
