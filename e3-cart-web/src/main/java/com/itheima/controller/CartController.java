package com.itheima.controller;

import com.itheima.pojo.TbItem;
import com.itheima.pojo.TbUser;
import com.itheima.service.CartService;
import com.itheima.service.ItemService;
import com.itheima.utils.CookieUtils;
import com.itheima.utils.E3Result;
import com.itheima.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;
    
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable("itemId") Long itemId, @RequestParam(defaultValue = "1")Integer num, HttpServletRequest request, HttpServletResponse response){
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(!StringUtils.isEmpty(user)){
            cartService.addCart(itemId, user.getId(), num);
            return "cartSuccess";
        }
        //根据itemId查询到对应商品信息
        TbItem itemById = itemService.getItemById(itemId);
        //从cookie中得到购物车信息
        List<TbItem> tbItems = getCartList(request);
        boolean hasItem = false;
        for (TbItem tbItem:tbItems) {
            //如果购物车里面存在此商品则数量加一
            if(itemId.longValue()==tbItem.getId().longValue()){
                hasItem = true;
                tbItem.setNum(tbItem.getNum()+num);
                break;
            }
        }
        //如果hasItem为false说明是新商品
        if(!hasItem){
            String image = itemById.getImage();
            if(!StringUtils.isEmpty(image)){
                String img = image.split(",")[0];
                itemById.setImage(img);
            }
            itemById.setNum(num);
            tbItems.add(itemById);
        }
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItems),true);
        return "cartSuccess";
    }

    @RequestMapping("/cart/cart")
    public String cartList(HttpServletRequest request,HttpServletResponse response){
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> tbItems = getCartList(request);
        if(!StringUtils.isEmpty(user)){
            tbItems = cartService.mergeCart(tbItems,user.getId());
            //删除cookie
            CookieUtils.setCookie(request,response,"cart","",30000,true);
        }
        request.setAttribute("cartList",tbItems);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num, HttpServletRequest request,HttpServletResponse response){
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(!StringUtils.isEmpty(user)){
            cartService.updateCartNum(user.getId(),itemId,num);
            return E3Result.ok();
        }
        List<TbItem> tbItems = getCartList(request);
        for (TbItem tbItem:tbItems) {
            if(itemId.longValue()==tbItem.getId()){
                tbItem.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItems),true);
        return E3Result.ok();
    }

    @RequestMapping("cart/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId, HttpServletRequest request, HttpServletResponse response){
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(!StringUtils.isEmpty(user)){
            cartService.deleteCartNum(user.getId(),itemId);
            return"redirect:/cart/cart.html";
        }
        List<TbItem> tbItems = getCartList(request);
        for (TbItem tbItem:tbItems) {
            if(itemId.longValue()==tbItem.getId()){
                tbItems.remove(tbItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(tbItems),true);
        return "redirect:/cart/cart.html";
    }

    public List<TbItem> getCartList(HttpServletRequest request){
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        if(!StringUtils.isEmpty(cart)) {
            //将购物车信息转为对象集合
            List<TbItem> tbItems = JsonUtils.jsonToList(cart, TbItem.class);
            return tbItems;
        }
        return new ArrayList<>();
    }
}
