package com.leyou.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.client.SkuClient;
import com.leyou.client.SpecClient;
import com.leyou.client.SpuClient;
import com.leyou.pojo.*;
import com.leyou.repository.GoodsRepository;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {
    @Autowired
    private SkuClient skuClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private SpuClient spuClient;
    @Autowired
    GoodsRepository goodsRepository;

    private static final ObjectMapper MAPPER=new ObjectMapper();

    public Goods convert(SpuVO spuVO) throws JsonProcessingException {
        Goods goods = new Goods();
        //把查询到的spu转换为goods实体
        //可以用goods elasticsearch - repository导入索引库

        //基础数据
        goods.setId(spuVO.getId());
        goods.setSubTitle(spuVO.getSubTitle());
        goods.setBrandId(spuVO.getBrandId());
        goods.setCid1(spuVO.getCid1());
        goods.setCid2(spuVO.getCid2());
        goods.setCid3(spuVO.getCid3());
        goods.setCreateTime(spuVO.getCreateTime());

        //all存放的是可搜索的词条 标题+分类+品牌
        goods.setAll(spuVO.getTitle()+""+spuVO.getCname().replace("/","")+""+spuVO.getBname());

        //复杂数据
        //根据spuid查询sku
        List<Sku> skuList = skuClient.findSkuBySpuId(spuVO.getId());

        //把sku价格封装到goods中的price中
        List<Long> price =new ArrayList<>();
        skuList.forEach(sku->{
            price.add(sku.getPrice());
        });

        goods.setPrice(price);
        goods.setSkus(MAPPER.writeValueAsString(skuList));

        Map<String, Object> specs =new HashMap<>();

        //根据三级分类id和可搜索条件查询规格参数
        List<SpecParam> specParamList = specClient.findParamsByCidAndSearching(spuVO.getCid3());

        //根据spuid查询spudetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuVO.getId());
        specParamList.forEach(sp ->{
            // 判断是否通用规格参数
            if(sp.getGeneric()){
                try {
                    // 获取通用的规格参数
                    Map<Long,Object> genericSpec = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>(){}) ;
                    String value = genericSpec.get(sp.getId()).toString();
                    // 判断是否是数值类型
                    if(sp.getNumeric()){
                        // 如果是数值的话，判断该数值落在那个区间
                        value = chooseSegment(value,sp);
                    }
                    // 把参数名和值放入结果集中
                    specs.put(sp.getName(),value);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                // 定义map接收{规格参数名，规格参数值}
                Map<Long,Object> specialSpec = null;
                try {
                    // 获取特殊的规格参数
                    specialSpec = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, Object>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String value = specialSpec.get(sp.getId()).toString();

                specs.put(sp.getName(),value);
            }

        });
        goods.setSpecs(specs);
        return goods;


    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    //rabbitmq监听修改消息
    public void editEsData(Long spuId) throws JsonProcessingException {
        //根据spuid查询spu
        SpuVO spuVO = spuClient.findSpuBySpuId(spuId);
        //spu转换成goods
        Goods goods = this.convert(spuVO);
        //持久化到es
        goodsRepository.save(goods);
    }

    public void deleteEsData(Long spuId) {
        goodsRepository.deleteById(spuId);
    }
}