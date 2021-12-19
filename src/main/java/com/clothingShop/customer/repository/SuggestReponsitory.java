package com.clothingShop.customer.repository;

import com.clothingShop.customer.entity.Suggest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SuggestReponsitory extends CrudRepository<Suggest, Long> {
    @Query(value = "SELECT * FROM Suggest WHERE user_id = ?1 AND product_id = ?2", nativeQuery = true)
    Suggest findByUserAndProduct(Long user_id, Long article_id);

    @Modifying
    @Query(value = "UPDATE Suggest SET views = ?1 WHERE id = ?2",nativeQuery = true)
    void updateSuggest(long views,long id);
}
