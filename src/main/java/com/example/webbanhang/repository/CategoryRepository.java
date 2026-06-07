package com.example.webbanhang.repository;

import com.example.webbanhang.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
