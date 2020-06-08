package com.leyou.dao;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SkuMapper extends Mapper<Sku> {
    @Select("SELECT s.*,t.stock FROM tb_sku s,tb_stock t WHERE s.id=t.sku_id and s.spu_id=#{id} and s.enable=1")
    List<Sku> findSkuBySpuId(Long id);
}
