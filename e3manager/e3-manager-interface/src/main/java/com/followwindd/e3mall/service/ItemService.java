package com.followwindd.e3mall.service;

import com.followwindd.e3mall.common.E3Result;
import com.followwindd.e3mall.common.EasyUIDataGridResult;
import com.followwindd.e3mall.pojo.TbItem;
import com.followwindd.e3mall.pojo.TbItemDesc;

public interface ItemService {
    TbItem getItemById(Long itemId);
    EasyUIDataGridResult getItemList(int page,int row);
    E3Result addItem(TbItem tbItem,TbItemDesc tbItemDesc);
    E3Result removeItem(Long[] ids);
    E3Result putAway(Long[] ids);
    E3Result soldOut(Long[] ids);
}
