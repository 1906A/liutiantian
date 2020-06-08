package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    BrandMapper brandMapper;

    public PageResult<Brand> findBrandByPage(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        PageHelper.startPage(page,rows);
        List<Brand> brandList = brandMapper.findBrandByPage(key,sortBy,desc);
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());
    }

    public void addOrEditBrand(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);
        cids.forEach(id->{
            brandMapper.addType(brand.getId(),id);
        });
    }

    public void deleteById(Long id) {
        //先删除brand表
        Brand brand = new Brand();
        brand.setId(id);
        brandMapper.deleteByPrimaryKey(brand);
        //再删除中间表tb_category_brand
        brandMapper.deleteCategoryAndBrand(id);
    }

    public List<Category> findCategoryById(Long bid) {
        return brandMapper.findCategoryById(bid);
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        //修改品牌表
        brandMapper.updateByPrimaryKey(brand);
        //修改分类品牌表（先删掉之前的所有分类，再重新添加）
        brandMapper.deleteCategoryAndBrand(brand.getId());
        cids.forEach(cid->{
            brandMapper.addType(brand.getId(),cid);
        });
    }

    public List<Brand> findByCid(Long cid) {
        return brandMapper.findByCid(cid);
    }

    public Brand findBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
