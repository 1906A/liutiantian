package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<SpuVO> findSpuByPage(String key, Integer page, Integer rows, Integer saleable) {
        PageHelper.startPage(page,rows);
        List<SpuVO> spuList = spuMapper.findSpuByPage(key,saleable);
        PageInfo<SpuVO> pageInfo = new PageInfo<>(spuList);
        return new PageResult<SpuVO>(pageInfo.getTotal(),pageInfo.getList());
    }

    //保存spu表---保存spu_detail表---保存sku表---保存stock表
    public void saveGoods(SpuVO spuVO) {
        //保存spu表
        Date nowDate = new Date();
        Spu spu = new Spu();
        spu.setTitle(spuVO.getTitle());
        spu.setSubTitle(spuVO.getSubTitle());
        spu.setBrandId(spuVO.getBrandId());
        spu.setCid1(spuVO.getCid1());
        spu.setCid2(spuVO.getCid2());
        spu.setCid3(spuVO.getCid3());
        spu.setSaleable(false);
        spu.setValid(true);
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);
        spuMapper.insert(spu);

        //保存spu_detail表
        /*SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuVO.getId());
        spuDetail.setAfterService(spuVO.getSpuDetail().getAfterService());
        spuDetail.setDescription(spuVO.getSpuDetail().getDescription());
        spuDetail.setGenericSpec(spuVO.getSpuDetail().getGenericSpec());
        spuDetail.setPackingList(spuVO.getSpuDetail().getPackingList());
        spuDetail.setSpecialSpec(spuVO.getSpuDetail().getSpecialSpec());*/
        SpuDetail spuDetail = spuVO.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);

        //保存sku表
        List<Sku> skus = spuVO.getSkus();
        skus.forEach(sku -> {
            sku.setSpuId(spu.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);
            //保存stock表
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });

    }

    public SpuDetail findSpuDetailBySpuId(Long spuId) {

        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    //修改商品信息
    public void updateGoods(SpuVO spuVO) {
        Date nowDate = new Date();
        //修改spu
        spuVO.setCreateTime(null);
        spuVO.setSaleable(null);
        spuVO.setLastUpdateTime(nowDate);
        spuVO.setValid(null);
        spuMapper.updateByPrimaryKeySelective(spuVO);

        //修改spu_detail
        SpuDetail spuDetail = spuVO.getSpuDetail();
        spuDetail.setSpuId(spuVO.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        //修改sku（先删除原来的数据，在保存新的数据）
        List<Sku> skus = spuVO.getSkus();
        skus.forEach(s -> {
            //把sku设置为无效(逻辑删除)
            s.setEnable(false);
            skuMapper.updateByPrimaryKey(s);
            //删除stock
            stockMapper.deleteByPrimaryKey(s.getId());

        });

        //保存sku表
        List<Sku> skus1 = spuVO.getSkus();
        skus.forEach(sku -> {
            sku.setSpuId(spuVO.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);
            //保存stock表
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
    }

    //删除商品
    public void deleteBySpuId(Long spuId) {
        //删除sku
        List<Sku> skuList = skuMapper.findSkuBySpuId(spuId);
        skuList.forEach(sku -> {
            //把sku设置为无效
            sku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku);
            //删除stock
            stockMapper.deleteByPrimaryKey(sku.getId());
        });
        //删除spu_detail
        spuDetailMapper.deleteByPrimaryKey(spuId);
        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);

    }


    public void upOrDown(Long spuId, int saleable) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable==1?true:false);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    public Spu findSpuById(Long spuId) {
        return spuMapper.selectByPrimaryKey(spuId);
    }
}
