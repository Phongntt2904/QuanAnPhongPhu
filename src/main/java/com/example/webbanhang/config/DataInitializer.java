package com.example.webbanhang.config;

import com.example.webbanhang.model.Category;
import com.example.webbanhang.model.Product;
import com.example.webbanhang.model.User;
import com.example.webbanhang.repository.CategoryRepository;
import com.example.webbanhang.repository.ProductRepository;
import com.example.webbanhang.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepo, UserRepository userRepo, CategoryRepository catRepo, PasswordEncoder encoder) {
        return args -> {
            // Nạp Category
            if (catRepo.count() == 0) {
                catRepo.save(new Category(null, "Món Chính"));
                catRepo.save(new Category(null, "Đồ Ăn Nhanh"));
                catRepo.save(new Category(null, "Đồ Uống"));
            }
            
            // Reset/Tạo Admin và User mẫu để đảm bảo đăng nhập được
            if (userRepo.findByUsername("admin").isEmpty()) {
                userRepo.save(new User(null, "admin", encoder.encode("admin123"), "Quản Trị Viên", "admin@food.com", "0988888888", "Hà Nội", "ROLE_ADMIN"));
            }
            if (userRepo.findByUsername("phong").isEmpty()) {
                userRepo.save(new User(null, "phong", encoder.encode("123456"), "Nguyễn Văn Phong", "phong@gmail.com", "0912345678", "123 Đường Láng, Hà Nội", "ROLE_USER"));
            }

            // Nạp đồ ăn nếu trống
            if (productRepo.count() == 0) {
                Category cat = catRepo.findAll().get(0);
                productRepo.save(new Product(null, "Phở Bò Gia Truyền", "Nước dùng đậm đà", 55000.0, "https://vcdn1-dulich.vnecdn.net/2022/03/14/pho-7814-1647241857.jpg", cat));
                productRepo.save(new Product(null, "Bánh Mì Đặc Biệt", "Pate thủ công", 25000.0, "https://vcdn1-dulich.vnecdn.net/2021/04/23/banh-mi-9311-1619163234.jpg", cat));
            }
        };
    }
}
