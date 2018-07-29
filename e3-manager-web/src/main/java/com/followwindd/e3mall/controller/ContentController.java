package com.followwindd.e3mall.controller;

import com.followwindd.e3mall.common.E3Result;
import com.followwindd.e3mall.common.EasyUIDataGridResult;
import com.followwindd.e3mall.common.EasyUITreeNode;
import com.followwindd.e3mall.search.service.ContentService;
import com.followwindd.e3mall.pojo.TbContent;
import com.followwindd.e3mall.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ContentController {
//    /search/category/list
    @Autowired
    private ContentService contentService;
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam( value = "id",defaultValue = "0") Long parentId){
        List<TbContentCategory> contentCategoryList = contentService.getContentCategoryList(parentId);
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<EasyUITreeNode>();
        if (contentCategoryList != null) {
            for (TbContentCategory tbContentCategory : contentCategoryList) {
                EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
                easyUITreeNode.setId(tbContentCategory.getId());
                easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
                easyUITreeNode.setText(tbContentCategory.getName());
                easyUITreeNodes.add(easyUITreeNode);
            }
        }
        return easyUITreeNodes;
    }

//    /search/category/create
    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result createContentCategory(Long parentId,String name){
        Long id = contentService.createContentCategory(parentId, name);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id",id+"");
        E3Result e3Result = new E3Result(200, null, data);
        return e3Result;
    }
    @RequestMapping("/content/category/update")
    @ResponseBody
    public E3Result updateContentCategory(Long id,String name){
        contentService.updateContentCategory(id, name);
        return E3Result.ok();
    }

    @RequestMapping("/content/category/delete")
    @ResponseBody
    public E3Result updateContentCategory(Long id){
        contentService.deleteContentCategory(id);
        return E3Result.ok();
    }
//    /search/query/list
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult queryContentList(Long categoryId){
        List<TbContent> tbContents = contentService.queryContentList(categoryId);
        EasyUIDataGridResult<TbContent> tbContentEasyUIDataGridResult = new EasyUIDataGridResult<TbContent>();
        tbContentEasyUIDataGridResult.setTotal(tbContents.size());
        tbContentEasyUIDataGridResult.setRows(tbContents);
        return tbContentEasyUIDataGridResult;
    }

///search/save
    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result saveContent(TbContent tbContent){
        contentService.saveContent(tbContent);
        return E3Result.ok();
    }

//    /search/delete
    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteContent(String ids){
        String[] split = ids.split(",");
        Long[] longIds = null;
        longIds = new Long[split.length];
        for (int i = 0; i < split.length; i++) {
            longIds[i] = Long.parseLong(split[i]);
        }
        contentService.deleteContents(longIds);
        return E3Result.ok();
    }
}
