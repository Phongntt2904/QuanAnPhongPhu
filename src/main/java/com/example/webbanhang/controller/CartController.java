package com.example.webbanhang.controller;

import com.example.webbanhang.model.CartItem;
import com.example.webbanhang.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String viewCart(Model model, Authentication authentication) {
        if (authentication == null) return "redirect:/login";
        
        List<CartItem> cartItems = cartService.getCartByUser(authentication.getName());
        double total = cartItems.stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) return "redirect:/login";
        
        cartService.addToCart(authentication.getName(), id);
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }
}
