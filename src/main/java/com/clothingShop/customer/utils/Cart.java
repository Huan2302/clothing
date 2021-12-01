package com.clothingShop.customer.utils;

import com.clothingShop.customer.entity.Oder;
import com.clothingShop.customer.entity.OderDetail;
import com.clothingShop.customer.entity.User;
import com.clothingShop.customer.service.OderDetailService;
import com.clothingShop.customer.service.OderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    @Autowired
    private OderService oderService;
    @Autowired private OderDetailService oderDetailService;

    public List<OderDetail> showCart(User user){
        List<OderDetail> list = new ArrayList<>();
        Oder oder = oderService.findOderByUser(user);
        return oderDetailService.findAllByOderDetailId(oder.getId());
    }
}
