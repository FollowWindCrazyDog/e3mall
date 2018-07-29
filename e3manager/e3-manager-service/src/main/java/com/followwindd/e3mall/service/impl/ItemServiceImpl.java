package com.followwindd.e3mall.service.impl;

import com.followwindd.e3mall.common.E3Result;
import com.followwindd.e3mall.common.EasyUIDataGridResult;
import com.followwindd.e3mall.common.utils.IDUtils;
import com.followwindd.e3mall.mapper.TbItemDescMapper;
import com.followwindd.e3mall.mapper.TbItemMapper;
import com.followwindd.e3mall.pojo.TbItem;
import com.followwindd.e3mall.pojo.TbItemDesc;
import com.followwindd.e3mall.pojo.TbItemExample;
import com.followwindd.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    public TbItem getItemById(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    public EasyUIDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbItem> tbItems = tbItemMapper.selectByExample(new TbItemExample());
        EasyUIDataGridResult<TbItem> tbItemEasyUIDataGridResult = new EasyUIDataGridResult<TbItem>();
        tbItemEasyUIDataGridResult.setRows(tbItems);
        tbItemEasyUIDataGridResult.setTotal(new PageInfo<TbItem>(tbItems).getTotal());
        return tbItemEasyUIDataGridResult;
    }

    public E3Result addItem(TbItem tbItem, TbItemDesc tbItemDesc) {
        long id = IDUtils.genItemId();
        tbItem.setId(id);
        tbItem.setStatus((byte) 1);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemId(id);
        tbItemMapper.insertSelective(tbItem);
        tbItemDescMapper.insertSelective(tbItemDesc);
        return E3Result.ok();
    }

    public E3Result removeItem(Long[] ids){
        if(ids!=null){
            for (Long id : ids) {
                if (id != null) {
                    tbItemMapper.deleteByPrimaryKey(id);
                }
            }
        }
        return E3Result.ok();
    }

    public E3Result putAway(Long[] ids) {
        if(ids!=null){
            for (Long id : ids) {
                if (id != null) {
                    TbItem tbItem = new TbItem();
                    tbItem.setId(id);
                    tbItem.setStatus((byte)1);
                    TbItemExample tbItemExample = new TbItemExample();
                    tbItemExample.createCriteria().andIdEqualTo(id);
                    tbItemMapper.updateByExampleSelective(tbItem,tbItemExample);
                }
            }
        }
        return E3Result.ok();
    }

    public E3Result soldOut(Long[] ids) {
        if(ids!=null){
            for (Long id : ids) {
                if (id != null) {
                    TbItem tbItem = new TbItem();
                    tbItem.setId(id);
                    tbItem.setStatus((byte)0);
                    TbItemExample tbItemExample = new TbItemExample();
                    tbItemExample.createCriteria().andIdEqualTo(id);
                    tbItemMapper.updateByExampleSelective(tbItem,tbItemExample);
                }
            }
        }
        return E3Result.ok();
    }
}
