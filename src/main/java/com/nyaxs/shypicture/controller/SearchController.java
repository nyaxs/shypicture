package com.nyaxs.shypicture.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.PictureData;
import com.nyaxs.shypicture.bean.jsonpojo.picture.ResponseItem;
import com.nyaxs.shypicture.util.AsyncDownload;
import com.nyaxs.shypicture.util.ConvertToPictureData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nyaxs
 * @version V1.0
 * @Package com.nyaxs.shypicture.controller
 * @date 2020/6/3 8:41
 */
@Slf4j
@Controller
public class SearchController {

    @Autowired
    private AsyncDownload asyncDownload;

    @GetMapping("/search")
    public String search(String searchString, Model model) throws Exception {
        log.info("进入search方法");
        if (searchString == null || searchString.length() == 0) {
            log.warn("搜索字符串为空！");
            return "index";
        }
        log.info("携带的search字符串为：" + searchString);
        PictureController pictureController = new PictureController();
        List<PictureData> pictureDataList = new ArrayList<>();
        String preSearch = searchString.substring(0, searchString.indexOf("="));
        String sufSearch = searchString.substring(searchString.indexOf("=") + 1);
        log.info("=前为：" + preSearch);
        log.info("=后为：" + sufSearch);
        if ("id".equals(preSearch)) {
            log.info("进入searchId判断");
            int pictureId = Integer.parseInt(sufSearch);
            log.info("pictureId值为：" + pictureId);
            ResponseItem item = pictureController.getPictureById(pictureId);
            PictureData pictureData1 = ConvertToPictureData.fromResponseItem(item);
            pictureDataList.add(pictureData1);
            IndexController indexController = new IndexController();
            indexController.asyncDownloadPictures(pictureDataList);
            model.addAttribute("pictures", pictureDataList);
            return "index";
        } else if ("text".equals(preSearch)) {
            log.info("进入searchText判断");
            String url = "https://api.imjad.cn/pixiv/v1/?type=search&word=" +
                    preSearch +
                    "&mode=text" +
                    "&per_page=5";
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
            log.info("使用json2pojo的对象来接收pictures信息");
            return "index";
        }

        return "random";
    }

}
