package com.nyaxs.shypicture.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.bean.jsonpojo.picture.ResponseItem;
import com.nyaxs.shypicture.bean.jsonpojo.picture.ResponsePicture;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponsePictures;
import com.nyaxs.shypicture.util.AsyncDownload;
import com.nyaxs.shypicture.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class PictureController {

    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    private AsyncDownload asyncDownload;

    LocalDate today = null;
    LocalDate twoDaysAgo = null;



    public ResponseItem getPictureById(int id) throws Exception {
        log.info("进入getPictureById(int id)");
        String url = "https://api.imjad.cn/pixiv/v1/?type=illust"
                + "&id=" + id;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        ResponsePicture responsePicture = objectMapper.readValue(new URL(url), ResponsePicture.class);
        ResponseItem item = responsePicture.getResponse().get(0);
        return item;
    }

    public ResponsePictures getPictureListWithJsonPojo(SearchCondition condition) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        String url = "https://api.imjad.cn/pixiv/v1/" +
                "?type=" + condition.getType() +
                "&mode=" + condition.getRankMode() +
                "&per_page=" + condition.getRankPerPage() +
                "&content=" + condition.getContent() +
                "&date=" + condition.getDate();

        log.info("使用json2pojo的对象来接收pictures信息");

        ResponsePictures responsePictures = objectMapper.readValue(new URL(url), ResponsePictures.class);


        return responsePictures;
    }

    public List<Picture> getPictureList(SearchCondition condition) throws Exception {
        today = LocalDate.now();
        twoDaysAgo = today.minusDays(2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        String url = "https://api.imjad.cn/pixiv/v1/" +
                "?type=" + condition.getType() +
                "&mode=" + condition.getRankMode() +
                "&per_page=" + condition.getRankPerPage() +
                "&content=" + condition.getContent() +
                "&date=" + condition.getDate();

        JsonNode resultRootNode = objectMapper.readTree(new URL(url));
        //Json node tree , use node.at() to get target node
        JsonNode worksNode = resultRootNode.at("/response").get(0).at("/works");

        String worksString = objectMapper.writeValueAsString(worksNode);

        List<Object> worksList = objectMapper
                .readValue(worksString, new TypeReference<List<Object>>() {
                });

        List<Picture> pictureList = new ArrayList<Picture>();
        //保存pictureList的大图下载链接
        //List<String>  pictureImageLargeUrls = new ArrayList<String>();

        for (Object obj : worksList) {
            Map<String, Object> workMap = (Map<String, Object>) obj;
            String workStr = JsonUtil.obj2String(workMap.get("work"));
            Picture picture = objectMapper.readValue(workStr, Picture.class);
            picture.setLargeUrl(picture.getImage_urls().get("large"));
            pictureList.add(picture);
            //pictureImageLargeUrls.add(picture.getImage_urls().get("large"));
        }
        ;
        return pictureList;
    }


}
