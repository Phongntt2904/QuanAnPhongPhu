package com.example.webbanhang.service;

import com.example.webbanhang.model.CartItem;
import com.example.webbanhang.model.Product;
import com.example.webbanhang.model.User;
import com.example.webbanhang.repository.CartItemRepository;
import com.example.webbanhang.repository.ProductRepository;
import com.example.webbanhang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CartItem> getCartByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return cartItemRepository.findByUser(user);
    }

    public void addToCart(String username, Long productId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem cartItem = cartItemRepository.findByUserAndProduct_Id(user, productId)
                .orElse(new CartItem(null, user, product, 0));

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
    }

    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
