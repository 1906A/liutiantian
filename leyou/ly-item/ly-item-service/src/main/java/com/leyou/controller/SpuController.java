package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.SpuVO;
import com.leyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @RequestMapping(value = "page",method = RequestMethod.GET)
    public PageResult<SpuVO> findSpuByPage(@RequestParam(value = "key",required = false) String key,
                                           @RequestParam(value = "page",required = false) Integer page,
                                           @RequestParam(value = "rows",required = false) Integer rows,
                                           @RequestParam(value = "saleable",required = false) Integer saleable){
        PageResult<SpuVO> spuList = spuService.findSpuByPage(key,page,rows,saleable);
        return spuList;
    }

    //添加修改商品
    @RequestMapping("saveOrUpdateGoods")
    public void saveGoods(@RequestBody SpuVO spuVO){
        if(spuVO.getId()!=null){
            spuService.updateGoods(spuVO);
        }else{
            spuService.saveGoods(spuVO);
        }
    }

    //通过spuId查询spudetail
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        return spuService.findSpuDetailBySpuId(spuId);
    }

    //根据spuId删除商品
    @RequestMapping("deleteBySpuId/{spuId}")
    public void deleteBySpuId(@PathVariable("spuId") Long spuId){
        spuService.deleteBySpuId(spuId);
    }

    //根据spuId修改上架下架状态
    @RequestMapping("upOrDown")
    public void upOrDown(@RequestParam("spuId") Long spuId,@RequestParam("saleable") int saleable){
        spuService.upOrDown(spuId,saleable);
    }

    //根据id查找spu
    @RequestMapping("findSpuById")
    public Spu findSpuById(@RequestParam("spuId") Long spuId){
        return spuService.findSpuById(spuId);
    }

}
