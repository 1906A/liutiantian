package com.leyou.controller;

import com.leyou.common.JwtUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    JwtProperties jwtProperties;

    public String prefix = "ly_carts_";

    public void add(@CookieValue("token") String token, @RequestBody Sku sku){
        UserInfo userInfo = this.getUserInfoByToken(token);
        if (userInfo!=null){
            //添加购物车
            BoundHashOperations<String, Object, Object> hashOps =
                    stringRedisTemplate.boundHashOps(prefix + userInfo.getId());
            //判断redis中信息
            if (hashOps.hasKey(sku.getId())){
                Sku skuRedis = (Sku) hashOps.get(sku.getId().toString());
            }else {
                hashOps.put(sku.getId(),sku);
            }
        }
    }

    public UserInfo getUserInfoByToken(String token){
        UserInfo userInfo = new UserInfo();
        try {
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
