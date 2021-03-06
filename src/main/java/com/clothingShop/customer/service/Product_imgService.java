package com.clothingShop.customer.service;

import com.clothingShop.customer.entity.Product_img;
import com.clothingShop.customer.repository.Product_imgReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Product_imgService {
    @Autowired private Product_imgReponsitory repo;
    public void save(Product_img product) {
        repo.save(product);
    }

    public List<Product_img> getAllByProductId(long id) {
        return (List<Product_img>) repo.getAllByProductId(id);
    }

    public void deleteProduct_imgByProductId(Long id) {
        repo.deleteProduct_imgByProductId(id);
    }
}
