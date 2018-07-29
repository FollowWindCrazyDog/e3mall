package com.followwindd.e3mall.controller;

import com.followwindd.e3mall.common.EasyUITreeNode;
import com.followwindd.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getEasyUITreeNodes(@RequestParam(name = "id", defaultValue = "0")Long parentId){
        return itemCatService.getItemCatListByParentId(parentId);
    }
}
