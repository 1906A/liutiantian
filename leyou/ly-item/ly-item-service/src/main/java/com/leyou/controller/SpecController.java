package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    SpecGroupService groupService;

    @Autowired
    SpecParamService paramService;

    //添加修改分组
    @RequestMapping("group")
    public void add(@RequestBody SpecGroup specGroup){
        if(specGroup.getId()!=null){
            groupService.updateSpec(specGroup);
        }else {
            groupService.addSpec(specGroup);
        }
    }

    //根据分类id查询分组信息
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroupByCid(@PathVariable("cid") Long cid){
        return groupService.findSpecGroupByCid(cid);
    }

    //删除分组
    @RequestMapping("group/{id}")
    public void deleteSpec(@PathVariable("id") Long id){
        groupService.deleteSpec(id);
    }

    //根据分组查询规格参数
    @RequestMapping("params")
    public List<SpecParam> findByGroupId(@RequestParam("gid") Long gid){
        return paramService.findByGroupId(gid);
    }
    //新增规格参数
    @RequestMapping("param")
    public void addOrEditparam(@RequestBody SpecParam specParam){
        if(specParam.getId()!=null){
            paramService.updateparam(specParam);
        }else{
            paramService.addparam(specParam);
        }
    }

    //删除规格参数
    @RequestMapping("param/{id}")
    public void deleteParam(@PathVariable("id") Long id){
        paramService.deleteParam(id);
    }

    //根据分类id查询规格参数
    @RequestMapping("specparams")
    public List<SpecParam> findParamsByCid(@RequestParam("cid") Long cid){
        return paramService.findParamsByCid(cid);
    }

    //根据分类id和是否可搜索字段searching进行查询
    @RequestMapping("specparamsByCid")
    public List<SpecParam> findParamsByCidAndSearching(@RequestParam("cid") Long cid){
        return paramService.findParamsByCidAndSearching(cid);
    }

    //根据分类id和是否通用参数值generic_param查询
    @RequestMapping("findParamByCidAndGeneric")
    public List<SpecParam> findParamByCidAndGeneric(@RequestParam("cid") Long cid,
                                                    @RequestParam("generic") boolean generic){
        return paramService.findParamByCidAndGeneric(cid,generic);
    }
}
