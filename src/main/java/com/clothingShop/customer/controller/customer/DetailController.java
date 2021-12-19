package com.clothingShop.customer.controller.customer;

import com.clothingShop.customer.JSON.JsonReader;
import com.clothingShop.customer.entity.Oder;
import com.clothingShop.customer.entity.OderDetail;
import com.clothingShop.customer.entity.Product;
import com.clothingShop.customer.entity.User;
import com.clothingShop.customer.service.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DetailController {
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;
    @Autowired private BrandService brandService;
    @Autowired private OderService oderService;
    @Autowired private OderDetailService oderDetailService;
    @Autowired private SuggestService suggestService;
    @ModelAttribute
    public void modelAtr(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OderDetail> list = new ArrayList<>();
        Oder oder = new Oder();
        if (user!=null){
            oder = oderService.findOderByUserId(user.getId());
            if ( oder!= null && oder.getStatus()==0){
                list = oderDetailService.findAllByOderDetailId(oder.getId());
                model.addAttribute("oder",oder);
            }
        }else {
            list=null;
        }
        model.addAttribute("listCart",list);
        model.addAttribute("listCategory", categoryService.listAll());
        model.addAttribute("listBrand",brandService.listAll());
    }

    @GetMapping("/san-pham/{id}")
    public ModelAndView home(@PathVariable(name = "id") Long id, HttpSession session){
        ModelAndView mav = new ModelAndView("public/detail-product");
        productService.updateviews(id);
        Product p =productService.get(id);
        User user = (User) session.getAttribute("user");
        List<Product> listSuggest = new ArrayList<>();
        if (user != null){
            //suggest
            suggestService.suggest(p,user);
            JsonReader jsonReader = new JsonReader();
            try {
                List<JSONObject> list = jsonReader.listSuggest(user.getId());
                if (list == null){
                    listSuggest = productService.getAllSuggest(p.getCategory().getId(),id);
                }else {
                    for (JSONObject item:list){
                        long ids = Long.parseLong(String.valueOf(item.get("id")));
                        Product product = productService.get(ids);
                        listSuggest.add(product);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            listSuggest = productService.getAllSuggest(p.getCategory().getId(),id);
        }
        mav.addObject("suggest",listSuggest);
        mav.addObject("product",p);
        return mav;
    }
}
