package com.followwindd.e3mall.search.service.impl;

import com.followwindd.e3mall.common.JedisClient;
import com.followwindd.e3mall.common.utils.JsonUtils;
import com.followwindd.e3mall.search.service.ContentService;
import com.followwindd.e3mall.mapper.TbContentCategoryMapper;
import com.followwindd.e3mall.mapper.TbContentMapper;
import com.followwindd.e3mall.pojo.TbContent;
import com.followwindd.e3mall.pojo.TbContentCategory;
import com.followwindd.e3mall.pojo.TbContentCategoryExample;
import com.followwindd.e3mall.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
//    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT = "INDEX_CONTENT";
    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private JedisClient jedisClient;

    public List<TbContentCategory> getContentCategoryList(Long parentId) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andParentIdEqualTo(parentId);
        return tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
    }

    public Long createContentCategory(Long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setName(name);
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andIdEqualTo(parentId);
        TbContentCategory parentContentCategory = new TbContentCategory();
        parentContentCategory.setIsParent(true);
        tbContentCategoryMapper.updateByExampleSelective(parentContentCategory,tbContentCategoryExample);
        return tbContentCategory.getId();
    }

    public void updateContentCategory(Long id, String name) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andIdEqualTo(id);
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setName(name);
        tbContentCategoryMapper.updateByExampleSelective(tbContentCategory,tbContentCategoryExample);
    }

    public void deleteContentCategory(Long id) {

        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = tbContentCategory.getParentId();
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andParentIdEqualTo(parentId);
        int count = tbContentCategoryMapper.countByExample(tbContentCategoryExample);
        if (count==1){
            TbContentCategory parentContentCategory = new TbContentCategory();
            parentContentCategory.setIsParent(false);
            parentContentCategory.setId(parentId);
            tbContentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
        }
        tbContentCategoryMapper.deleteByPrimaryKey(id);
    }

    public List<TbContent> queryContentList(Long categoryId) {
//        查询redis缓存,如果没有才查询数据库
        try{
            String result = jedisClient.hget(INDEX_CONTENT, "" + categoryId);
            if(result!=null&&!"".equals(result.trim())){
                List<TbContent> tbContents = JsonUtils.jsonToList(result, TbContent.class);
                return tbContents;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbContentExample tbContentExample = new TbContentExample();
        tbContentExample.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExample(tbContentExample);
//        将查询结果放入缓存\
        try{
            String jsonResult = JsonUtils.objectToJson(tbContents);
            jedisClient.hset(INDEX_CONTENT, "" + categoryId,jsonResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbContents;
    }

    public void saveContent(TbContent tbContent) {
        Date date = new Date();
        jedisClient.hdel(INDEX_CONTENT,""+tbContent.getCategoryId());
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        tbContentMapper.insertSelective(tbContent);
    }

    public void deleteContents(Long[] longIds) {
        for (Long longId : longIds) {
            tbContentMapper.deleteByPrimaryKey(longId);
            TbContent tbContent = tbContentMapper.selectByPrimaryKey(longId);
            jedisClient.hdel(INDEX_CONTENT,""+tbContent.getCategoryId());
        }
    }


}
