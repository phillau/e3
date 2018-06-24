package com.itheima.service;

import com.itheima.jedis.JedisClient;
import com.itheima.mapper.ItemMapper;
import com.itheima.pojo.TbItem;
import com.itheima.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void addCart(Long itemId, Long userId, Integer num) {
        //判断商品在redis中是否存在
        Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
        //如果存在，直接从redis中取出，数量相加
        if(hexists) {
            String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
        }else {
            //如果不存在，从mysql中取出
            TbItem tbItem = itemMapper.getItemById(itemId);
            tbItem.setNum(num);
            String image = tbItem.getImage();
            if(!StringUtils.isEmpty(image)){
                String img = image.split(",")[0];
                tbItem.setImage(img);
        }
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
        }
    }

    @Override
    public List<TbItem> mergeCart(List<TbItem> tbItems, Long userId) {
        for (TbItem tbItem:tbItems) {
            addCart(tbItem.getId(),userId,tbItem.getNum());
        }
        List<String> hvals = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String hval:hvals) {
            TbItem tbItem = JsonUtils.jsonToPojo(hval, TbItem.class);
            itemList.add(tbItem);
        }
        return itemList;
    }

    @Override
    public void updateCartNum(Long userId, Long itemId, Integer num) {
        String hget = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        TbItem tbItem = JsonUtils.jsonToPojo(hget, TbItem.class);
        tbItem.setNum(num);
        jedisClient.hset(REDIS_CART_PRE + ":" + userId,itemId+"", JsonUtils.objectToJson(tbItem));
    }

    @Override
    public void deleteCartNum(Long userId, Long itemId) {
        jedisClient.hdel(REDIS_CART_PRE + ":" + userId,itemId+"");
    }
}
