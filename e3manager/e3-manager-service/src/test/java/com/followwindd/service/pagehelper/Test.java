package com.followwindd.service.pagehelper;

import com.alibaba.dubbo.container.page.PageHandler;
import com.followwindd.e3mall.mapper.TbItemMapper;
import com.followwindd.e3mall.pojo.TbItem;
import com.followwindd.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Test {
    @org.junit.Test
    public void test(){
         ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application-dao.xml");
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        PageHelper.startPage(1,10);
        List<TbItem> tbItems = itemMapper.selectByExample(new TbItemExample());
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(tbItems);
        System.out.println(pageInfo.getPages());
        System.out.println(pageInfo.getTotal());
    }
}
