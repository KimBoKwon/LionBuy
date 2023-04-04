package com.ateam.lionbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ateam.lionbuy.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
