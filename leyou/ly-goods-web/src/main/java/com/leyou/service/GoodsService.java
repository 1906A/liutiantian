package com.leyou.service;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {
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
    @Autowired
    TemplateEngine templateEngine;


    //查询商品详情数据
    public Map<String,Object> item(Long spuId){
        //查询spu
        Spu spu = spuClient.findSpuById(spuId);

        //查询spudetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuId);

        //查询sku
        List<Sku> skuList = skuClient.findSkuBySpuId(spuId);

        //查询spec_group
        List<SpecGroup> specGroupList = specClient.findSpecGroupByCid(spu.getCid3());

        //查询三级分类
        List<Category> categoryList = categoryClient.findCategoryByCids(
                Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3())
        );

        //查询spec_param(对应spec_detail中的特殊参数special_spec)
        List<SpecParam> specParamList = specClient.findParamByCidAndGeneric(spu.getCid3(),false);
        Map<Long,String> paramMap = new HashMap<>();
        specParamList.forEach((param->{
            paramMap.put(param.getId(),param.getName());
        }));

        //查询brand
        Brand brand = brandClient.findBrandById(spu.getBrandId());


        Map<String,Object> map = new HashMap<>();
        map.put("spu",spu);
        map.put("spuDetail",spuDetail);
        map.put("skuList",skuList);
        map.put("specGroupList",specGroupList);
        map.put("categoryList",categoryList);
        map.put("paramMap",paramMap);
        map.put("brand",brand);
        return map;
    }

    //创建html静态页面
    public void createHtml(Long spuId) {

        PrintWriter writer = null;
        try {


            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(this.item(spuId));

            // 创建输出流
            File file = new File("D:\\nginx-1.16.1\\html\\" + spuId + ".html");
            writer = new PrintWriter(file);

            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    //删除静态页面
    public void deleteHtml(Long spuId) {
        File file = new File("D:\\nginx-1.16.1\\html\\" + spuId + ".html");
        if(file!=null && file.exists()){
            file.delete();
        }
    }
}
