package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("list")
    public List<Category> list(@RequestParam("pid") Long pid){
        Category category = new Category();
        category.setParentId(pid);
        return categoryService.findCategory(category);
    }

    @RequestMapping("id")
    public Object findCate(){
        return categoryService.findCate(5);
    }

    @RequestMapping("addCategory")
    public String addCategory(@RequestBody Category category){
        String result = "success";
        try {
            categoryService.addCategory(category);
        }catch (Exception e){
            result="fail";
        }
        return result;
    }

    @RequestMapping("editCategory")
    public String editCategory(@RequestBody Category category){
        String result = "success";
        try {
            categoryService.editCategory(category);
        }catch (Exception e){
            result="fail";
        }
        return result;
    }

    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id") Long id){
        String result = "success";
        try {
            categoryService.deleteById(id);
        }catch (Exception e){
            result="fail";
        }
        return result;
    }

    //根据id查找分类
    @RequestMapping("findCategoryById")
    public Category findCategoryById(@RequestParam("id") Long id){
        return categoryService.findCategoryById(id);
    }

    //根据id查询三级分类
    @RequestMapping("findCategoryByCids")
    public List<Category> findCategoryByCids(@RequestBody List<Long> ids){
        return categoryService.findCategoryByCids(ids);
    }
}
