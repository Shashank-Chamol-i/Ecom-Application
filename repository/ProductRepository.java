package com.example.ecom_Application.repository;

import com.example.ecom_Application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product>findByActiveTrue();

    @Query("SELECT p FROM product p where p.active = true AND p.stockQuantity>0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword,'%'))")
    List<Product>searchProduct(@Param("keyword") String keyword);


}
