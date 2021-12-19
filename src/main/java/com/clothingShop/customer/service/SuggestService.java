package com.clothingShop.customer.service;

import com.clothingShop.customer.entity.Product;
import com.clothingShop.customer.entity.Suggest;
import com.clothingShop.customer.entity.User;
import com.clothingShop.customer.repository.SuggestReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SuggestService {
    @Autowired private SuggestReponsitory repo;

    public Suggest findByUserAndProduct(Long user_id,Long product_id){
        return repo.findByUserAndProduct(user_id,product_id);
    }

    public void save(Suggest suggest) {
        repo.save(suggest);
    }

    public void suggest(Product product, User user){
        Suggest suggest = findByUserAndProduct(user.getId(),product.getId());
        if (suggest != null){
            suggest.setViews(suggest.getViews() + 1);
            repo.updateSuggest(suggest.getViews(),suggest.getId());
        }
        else {
            suggest = new Suggest();
            suggest.setUser_id(user.getId());
            suggest.setProduct_id(product.getId());
            suggest.setViews(1);
            repo.save(suggest);
        }
    }
}
