package com.nyaxs.shypicture.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.service.PictureMapper;
import com.nyaxs.shypicture.util.DownloadUtil;
import com.nyaxs.shypicture.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private PictureMapper pictureMapper;

    RestTemplate restTemplate = null;
    LocalDate today = null;
    LocalDate twoDaysAgo = null;


    public List<Picture> getAndDownPicture(SearchCondition condition) throws Exception {
        PictureController pictureController = new PictureController();
        List<Picture> pictureList = pictureController.getPictureList(condition);
        DownloadUtil.get().downloadPicturesByList(pictureList);
        pictureMapper.insertManyPictures(pictureList);
        return pictureList;
    }

    //处理根路径请求
    @GetMapping("/")
    public String index() {
        return "welcome";
    }

    @GetMapping("/index")
    public String random(Model model) throws Exception {
        PictureController pictureController = new PictureController();
        SearchCondition condition1 = new SearchCondition();
        System.out.println("########" + condition1 + JsonUtil.obj2String(condition1));

        int picNum = 2;
        //List<Picture> pictureList = pictureMapper.findRandomPicture(picNum);
        List<Picture> pictureList = pictureController.getPictureList(condition1);
        DownloadUtil.get().downloadPicturesByList(pictureList);
        pictureMapper.insertManyPictures(pictureList);
        model.addAttribute("pictureList", pictureList);
        return "index";
    }

    @GetMapping("/daily")
    public String everyday(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("daily");
        condition.setRankPerPage(3);
        condition.setDate("2020-03-03");
        List<Picture> pictureList = getAndDownPicture(condition);
        model.addAttribute("pictureList", pictureList);
        return "index";
    }

    @GetMapping("/weekly")
    public int weekly(){

        restTemplate = new RestTemplate();
        return 0;

    }


    public int randomPic(){
        return 0;
    }


}
