package com.clothingShop.customer.controller.customer;

import com.clothingShop.customer.entity.Oder;
import com.clothingShop.customer.entity.OderDetail;
import com.clothingShop.customer.entity.User;
import com.clothingShop.customer.service.*;
import com.clothingShop.customer.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired private CategoryService categoryService;
    @Autowired private BrandService brandService;
    @Autowired private OderService oderService;
    @Autowired private OderDetailService oderDetailService;
    @Autowired private ProductService productService;
    @Autowired private UserService userService;
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


    @GetMapping("/trang-chu")
    public ModelAndView home(HttpSession session){
        ModelAndView mav = new ModelAndView("public/index");
        mav.addObject("listProduct",productService.listAll());
        return mav;
    }

    @GetMapping("/thong-tin")
    public ModelAndView info(){
        ModelAndView mav = new ModelAndView("public/info");
        return mav;
    }

    @RequestMapping(value = "/thong-tin" ,method = RequestMethod.POST)
    public String infom(@RequestParam("oldpass") String oldpass,
                        @RequestParam("password")String password,
                        @RequestParam("repassword")String repassword,
                        HttpSession session,
                        RedirectAttributes re){
        User user = (User) session.getAttribute("user");
        if (new StringUtil().md5(oldpass).equals(user.getPassword())){
            if (password.equals(repassword)){
                userService.editPass(new StringUtil().md5(password),user.getId());
                re.addFlashAttribute("msg","S???a m???t kh???u th??nh c??ng");
            }else {
                re.addFlashAttribute("err","M???t kh???u kh??ng tr??ng nhau");
            }
        }else {
            re.addFlashAttribute("err","M???t kh???u c?? kh??ng ch??nh x??c");
        }
        return "redirect:/thong-tin";
    }
}
