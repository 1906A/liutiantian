package com.leyou.dao;

import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@org.apache.ibatis.annotations.Mapper
public interface SpecParamMapper extends Mapper<SpecParam> {

    @Select("SELECT p.* FROM tb_spec_param p,tb_category c WHERE p.cid=c.id AND c.id=#{cid} ")
    List<SpecParam> findParamsByCid(Long cid);
}
