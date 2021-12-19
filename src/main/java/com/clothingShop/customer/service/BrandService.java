package com.clothingShop.customer.service;

import com.clothingShop.customer.entity.Brand;
import com.clothingShop.customer.repository.BrandReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrandService {
    @Autowired private BrandReponsitory repo; //DI IOC
    public void save(Brand brand) {
        repo.save(brand);
    }

    public List<Brand> listAll() {
        return (List<Brand>) repo.findAll();
    }

    public Brand get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
