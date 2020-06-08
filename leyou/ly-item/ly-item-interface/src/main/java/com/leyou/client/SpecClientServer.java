package com.leyou.client;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("spec")
public interface SpecClientServer {
    //根据是否可搜索字段searching进行查询
    @RequestMapping("specparamsByCid")
    public List<SpecParam> findParamsByCidAndSearching(@RequestParam("cid") Long cid);
    //根据分类id查询分组信息
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroupByCid(@PathVariable("cid") Long cid);
    //根据分类id和是否通用参数值generic_param查询
    @RequestMapping("findParamByCidAndGeneric")
    public List<SpecParam> findParamByCidAndGeneric(@RequestParam("cid") Long cid,
                                                    @RequestParam("generic") boolean generic);
}
