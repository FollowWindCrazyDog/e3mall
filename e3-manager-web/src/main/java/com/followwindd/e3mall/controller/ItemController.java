package com.followwindd.e3mall.controller;

import com.followwindd.e3mall.common.E3Result;
import com.followwindd.e3mall.common.EasyUIDataGridResult;
import com.followwindd.e3mall.pojo.TbItem;
import com.followwindd.e3mall.pojo.TbItemDesc;
import com.followwindd.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        return itemService.getItemById(itemId);
    }
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        return itemService.getItemList(page,rows);
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public E3Result addItem(TbItem tbItem,String desc){
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        return itemService.addItem(tbItem,tbItemDesc);
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result removeItem(String ids){
        String[] split = ids.split(",");
        Long[] longs = new Long[split.length];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = Long.parseLong(split[i]);
        }
        return itemService.removeItem(longs);
    }

    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public E3Result reShelf(String ids){
        String[] split = ids.split(",");
        Long[] longs = new Long[split.length];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = Long.parseLong(split[i]);
        }
        return itemService.putAway(longs);
    }

    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public E3Result inStock(String ids){
        String[] split = ids.split(",");
        Long[] longs = new Long[split.length];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = Long.parseLong(split[i]);
        }
        return itemService.soldOut(longs);
    }
}
