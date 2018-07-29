package com.followwindd.e3mall.portal.controller;

import com.followwindd.e3mall.search.service.ContentService;
import com.followwindd.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/index")
    public String index(Model model){
        Long categoryId = 89L;
        List<TbContent> tbContentList = contentService.queryContentList(categoryId);
        model.addAttribute("ad1List",tbContentList);
        return "index";
    }
}
