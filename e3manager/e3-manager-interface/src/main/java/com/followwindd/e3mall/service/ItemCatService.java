package com.followwindd.e3mall.service;

import com.followwindd.e3mall.common.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getItemCatListByParentId(Long parentId);
}
