package com.followwindd.e3mall.controller;

import com.followwindd.e3mall.common.utils.FastDFSClient;
import com.followwindd.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.HashMap;

@Controller
public class PictureController {
    @Value("${IMG_SERVER_URL}")
    private String IMG_SERVER_URL;

    @RequestMapping(value = "/pic/upload" ,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String uploadPicture(MultipartFile uploadFile){
        HashMap map = new HashMap();
        try{
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fastDFS.properties");
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), uploadFile.getOriginalFilename());
            map.put("error",0);
            map.put("url",IMG_SERVER_URL+path);
        }catch (Exception e){
            e.printStackTrace();
            map.put("error",0);
            map.put("message",e.getMessage());
        }
        return JsonUtils.objectToJson(map);
    }
}
