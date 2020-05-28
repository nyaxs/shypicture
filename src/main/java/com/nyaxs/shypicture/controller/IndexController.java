package com.nyaxs.shypicture.controller;

import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.bean.jsonpojo.picture.FavoritedCount;
import com.nyaxs.shypicture.bean.jsonpojo.picture.ResponseItem;
import com.nyaxs.shypicture.bean.jsonpojo.picture.Stats;
import com.nyaxs.shypicture.bean.jsonpojo.picture.User;
import com.nyaxs.shypicture.service.PictureMapper;
import com.nyaxs.shypicture.util.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

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
        List<Picture> pictures = pictureMapper.findRandomPicture(1);
        Picture picture = pictures.get(0);

        ResponseItem item = pictureController.getPictureById(picture.getId());
        User user = item.getUser();
        Stats stats = item.getStats();
        FavoritedCount favoritedCount = stats.getFavoritedCount();
        List<String> tags = item.getTags();
        model.addAttribute("tags", tags);
        model.addAttribute("picture", item);
        model.addAttribute("author", user);
        model.addAttribute("stats", stats);
        model.addAttribute("favoritedCount", favoritedCount);
        System.out.println("item:" + item.toString());
        System.out.println("user:" + user.toString());

        //DownloadUtil.get().downloadPictureById(picture);
        //List<Picture> pictureList = pictureController.getPictureList(condition1);
        //DownloadUtil.get().downloadPicturesByList(pictureList);
        //pictureMapper.insertManyPictures(pictureList);
        //model.addAttribute("pictureList", pictureList);
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

        return 0;

    }


    public int randomPic(){
        return 0;
    }


}
