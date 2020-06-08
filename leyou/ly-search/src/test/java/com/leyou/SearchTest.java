package com.leyou;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leyou.common.PageResult;
import com.leyou.client.SpuClient;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SpuVO;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchTest {
    @Autowired
    SpuClient spuClient;
    @Autowired
    GoodsService goodsService;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    public void test1(){
        // 创建索引库，以及映射
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        PageResult<SpuVO> list = spuClient.findSpuByPage("", 1, 200,2);
        list.getItems().forEach(spuVO -> {
            System.out.println(spuVO.getId());
            try {
                Goods goods = goodsService.convert(spuVO);
                goodsRepository.save(goods);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

    }
}
