package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    //分页查询
    @RequestMapping("page")
    public Object findBrandByPage(@RequestParam("key") String key,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows,
                                  @RequestParam("sortBy") String sortBy,
                                  @RequestParam("desc") boolean desc){
        System.out.println(key+"--"+page+"--"+rows+"--"+sortBy+"--"+desc);
        PageResult<Brand> brandList = brandService.findBrandByPage(key,page,rows,sortBy,desc);
        return brandList;
    }

    //添加
    @RequestMapping("addOrEditBrand")
    public void addOrEditBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        //判断id是否有值
        if(brand.getId()!=null){
            //修改
            brandService.updateBrand(brand,cids);
        }else{
            //添加
            brandService.addOrEditBrand(brand,cids);
        }


    }

    //删除
    @RequestMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id){
        brandService.deleteById(id);
    }

    //根据品牌id查询具体分类
    @RequestMapping("bid/{id}")
    public List<Category> findCategoryById(@PathVariable("id") Long bid){
        return brandService.findCategoryById(bid);
    }

    //根据分类id查询品牌
    @RequestMapping("cid/{cid}")
    public List<Brand> findByCid(@PathVariable("cid") Long cid){
        return brandService.findByCid(cid);
    }

    //根据品牌id查询品牌
    @RequestMapping("findBrandById")
    public Brand findBrandById(@RequestParam("id") Long id){
        return brandService.findBrandById(id);
    }
}
