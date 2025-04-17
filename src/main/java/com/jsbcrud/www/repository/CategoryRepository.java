package com.jsbcrud.www.repository;

import com.jsbcrud.www.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByStatusOrderByNameAsc(Category.Status status);
}