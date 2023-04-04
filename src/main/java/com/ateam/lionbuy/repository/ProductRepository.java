package com.ateam.lionbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.lionbuy.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
    
}
