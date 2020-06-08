package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    //根据节点id查询所有分类信息
    public List<Category> findCategory(Category category){
        return categoryMapper.select(category);
    }

    //测试
    public Category findCate(int id){
        return categoryMapper.findCate(id);
    }

    //添加
    public void addCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    //修改
    public void editCategory(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    public void deleteById(Long id) {
        Category category = new Category();
        category.setId(id);
        categoryMapper.deleteByPrimaryKey(category);
    }

    public Category findCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<Category> findCategoryByCids(List<Long> ids) {
        List<Category> categoryList = new ArrayList<>();
        ids.forEach(cid->{
            categoryList.add(categoryMapper.selectByPrimaryKey(cid));
        });
        return categoryList;
    }
}
