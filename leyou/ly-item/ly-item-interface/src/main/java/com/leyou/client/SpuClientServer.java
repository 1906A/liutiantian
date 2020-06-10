package com.leyou.client;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.SpuVO;
import org.springframework.web.bind.annotation.*;

@RequestMapping("spu")
public interface SpuClientServer {
    @RequestMapping(value = "page",method = RequestMethod.GET)
    public PageResult<SpuVO> findSpuByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "rows",required = false) Integer rows,
            @RequestParam(value = "saleable",required = false) Integer saleable);
    //通过spuId查询spudetail
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId);
    //根据id查找spu
    @RequestMapping("findSpuById")
    public Spu findSpuById(@RequestParam("spuId") Long spuId);
    //根据spuId查询spuVo
    @RequestMapping("findSpuBySpuId")
    public SpuVO findSpuBySpuId(@RequestParam("spuId")Long spuId);
}
