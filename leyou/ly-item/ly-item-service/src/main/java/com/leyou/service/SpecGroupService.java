package com.leyou.service;


import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    SpecGroupMapper groupMapper;
    @Autowired
    SpecParamMapper specParamMapper;

    public void updateSpec(SpecGroup specGroup) {
        groupMapper.updateByPrimaryKey(specGroup);
    }

    public void addSpec(SpecGroup specGroup) {
        groupMapper.insert(specGroup);
    }

    public List<SpecGroup> findSpecGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        //根据分类id查询规格参数组及参数列表
        List<SpecGroup> groupList = new ArrayList<>();
        groupList = groupMapper.select(specGroup);
        groupList.forEach(group->{
            SpecParam param= new SpecParam();
            param.setGroupId(group.getId());
            group.setParams(specParamMapper.select(param));
        });
        return  groupList;
    }

    public void deleteSpec(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        groupMapper.deleteByPrimaryKey(specGroup);
        
    }


}
