package com.leyou.dao;


import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {

    List<Brand> findBrandByPage(@Param("key") String key,
                                @Param("sortBy") String sortBy,
                                @Param("desc") boolean desc);


    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    void addType(Long bid, Long cid);

    @Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteCategoryAndBrand(Long id);

    @Select("SELECT * FROM tb_category_brand t,tb_category c WHERE t.category_id=c.id AND t.brand_id=#{bid}")
    List<Category> findCategoryById(Long bid);

    @Select("SELECT b.* FROM tb_brand b,tb_category_brand c WHERE b.id=c.brand_id AND c.category_id=#{cid} ")
    List<Brand> findByCid(Long cid);
}
