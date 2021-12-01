package com.clothingShop.customer.repository;

import com.clothingShop.customer.entity.Oder;
import com.clothingShop.customer.entity.OderDetail;
import com.clothingShop.customer.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OderDetailReponsitory extends CrudRepository<OderDetail,Long> {
    public OderDetail findByProductAndOder(Product product,Oder oder);

    @Query(value = "select * from OderDetail where oderId = ?1", nativeQuery = true)
    public List<OderDetail> findAllByOderDetailId(long id);

}
