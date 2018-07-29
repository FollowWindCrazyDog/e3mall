package com.followwindd.e3mall.search.service;

import com.followwindd.e3mall.pojo.TbContent;
import com.followwindd.e3mall.pojo.TbContentCategory;

import java.util.List;

public interface ContentService {
    List<TbContentCategory> getContentCategoryList(Long parentId);
    Long createContentCategory(Long parentId,String name);
    void updateContentCategory(Long id,String name);

    void deleteContentCategory(Long id);

    List<TbContent> queryContentList(Long categoryId);

    void saveContent(TbContent tbContent);

    void deleteContents(Long[] longIds);

}
