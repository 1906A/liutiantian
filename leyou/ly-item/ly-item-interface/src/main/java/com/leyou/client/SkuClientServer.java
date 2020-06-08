package com.leyou.client;

import com.leyou.pojo.Sku;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("sku")
public interface SkuClientServer {
    //根据spuId查询sku商品集合
    @RequestMapping("list")
    public List<Sku> findSkuBySpuId(@RequestParam("id") Long id);
}
