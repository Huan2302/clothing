package com.clothingShop.customer.repository;

import com.clothingShop.customer.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryReponsitory extends CrudRepository<Category, Long> {
}
