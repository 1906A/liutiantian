package com.leyou.service;

import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecParamService {
    @Autowired
    private SpecParamMapper paramMapper;

    public List<SpecParam> findByGroupId(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        return paramMapper.select(specParam);

    }

    public void addparam(SpecParam specParam) {
        paramMapper.insert(specParam);
    }

    public void updateparam(SpecParam specParam) {
        paramMapper.updateByPrimaryKey(specParam);
    }

    public void deleteParam(Long id) {
        paramMapper.deleteByPrimaryKey(id);
    }

    public List<SpecParam> findParamsByCid(Long cid) {
       return paramMapper.findParamsByCid(cid);
    }

    public List<SpecParam> findParamsByCidAndSearching(Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(true);
        return paramMapper.select(specParam);
    }

    public List<SpecParam> findParamByCidAndGeneric(Long cid, boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        return paramMapper.select(specParam);
    }
}
