package com.followwindd.e3mall.service.impl;

import com.followwindd.e3mall.common.EasyUITreeNode;
import com.followwindd.e3mall.mapper.TbItemCatMapper;
import com.followwindd.e3mall.pojo.TbItemCat;
import com.followwindd.e3mall.pojo.TbItemCatExample;
import com.followwindd.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    public List<EasyUITreeNode> getItemCatListByParentId(Long parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        tbItemCatExample.createCriteria().andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<EasyUITreeNode>();
        for (TbItemCat tbItemCat : tbItemCats) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbItemCat.getId());
            easyUITreeNode.setText(tbItemCat.getName());
            System.out.println(tbItemCat.getIsParent());
            easyUITreeNode.setState((tbItemCat.getIsParent())?"closed":"open");
            easyUITreeNodes.add(easyUITreeNode);
        }
        return easyUITreeNodes;
    }
}
