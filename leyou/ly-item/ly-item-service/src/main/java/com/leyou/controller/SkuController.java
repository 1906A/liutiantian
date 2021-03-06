package com.leyou.controller;

import com.leyou.pojo.Sku;
import com.leyou.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    //根据spuId查询sku商品集合
    @RequestMapping("list")
    public List<Sku> findSkuBySpuId(@RequestParam("id") Long id){
        return skuService.findSkuBySpuId(id);
    }
}
