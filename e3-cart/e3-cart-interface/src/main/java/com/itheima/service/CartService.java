package com.itheima.service;

import com.itheima.pojo.TbItem;

import java.util.List;

public interface CartService {
    void addCart(Long itemId, Long userId, Integer num);

    List<TbItem> mergeCart(List<TbItem> tbItems, Long id);

    void updateCartNum(Long id, Long itemId, Integer num);

    void deleteCartNum(Long id, Long itemId);
}
