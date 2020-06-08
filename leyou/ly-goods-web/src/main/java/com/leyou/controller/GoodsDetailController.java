package com.leyou.controller;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsDetailController {
    @Autowired
    SpuClient spuClient;
    @Autowired
    SkuClient skuClient;
    @Autowired
    SpecClient specClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;

    //请求商品详情微服务
    /*用到的表
    * spu
    * spudetail
    * sku
    * spec_group
    * spec_param
    * 三级分类category
    * brand
    * */
    @RequestMapping("item/{spuId}.html")
    public String item(@PathVariable("spuId") Long spuId,Model model){
        //查询spu
        Spu spu = spuClient.findSpuById(spuId);
        model.addAttribute("spu",spu);
        //查询spudetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuId);
        model.addAttribute("spuDetail",spuDetail);
        //查询sku
        List<Sku> skuList = skuClient.findSkuBySpuId(spuId);
        model.addAttribute("skuList",skuList);
        //查询spec_group
        List<SpecGroup> specGroupList = specClient.findSpecGroupByCid(spu.getCid3());
        model.addAttribute("specGroupList",specGroupList);
        //查询三级分类
        List<Category> categoryList = categoryClient.findCategoryByCids(
                Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3())
        );
        model.addAttribute("categoryList",categoryList);
        //查询spec_param(对应spec_detail中的特殊参数special_spec)
        List<SpecParam> specParamList = specClient.findParamByCidAndGeneric(spu.getCid3(),false);
        Map<Long,String> paramMap = new HashMap<>();
        specParamList.forEach((param->{
            paramMap.put(param.getId(),param.getName());
        }));
        model.addAttribute("paramMap",paramMap);
        //查询brand
        Brand brand = brandClient.findBrandById(spu.getBrandId());
        model.addAttribute("brand",brand);
        return "item";
    }
}
