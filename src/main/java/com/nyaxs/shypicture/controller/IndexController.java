package com.nyaxs.shypicture.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.util.JsonUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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

    RestTemplate restTemplate = null;
    LocalDate today = null;
    LocalDate twoDaysAgo = null;

    //处理根路径请求
    @GetMapping("/")
    public String index(ModelMap map){
        map.addAttribute("host","http://localhost:8080/");
        return "index";
    }

    @GetMapping("/daily")
    public String everyday(Model model) throws Exception {
        today = LocalDate.now();
        twoDaysAgo = today.minusDays(2);
        restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false);
        String url = "https://api.imjad.cn/pixiv/v1/" +
                "?type=rank&mode=daily&per_page=2&content=illust&date="+twoDaysAgo;

        JsonNode resultRootNode = objectMapper.readTree(new URL(url));
        //Json node tree , use node.at() to get target node
        JsonNode worksNode = resultRootNode.at("/response").get(0).at("/works");

        String worksString = objectMapper.writeValueAsString(worksNode);

        List<Object> worksList = objectMapper
                .readValue(worksString, new TypeReference<List<Object>>(){});

        List<Picture> pictureList = new ArrayList<Picture>();

        for (Object obj:worksList) {
            Map<String,Object> workMap = (Map<String, Object>) obj;
            String workStr = JsonUtil.obj2String(workMap.get("work")) ;
            pictureList.add(objectMapper.readValue(workStr,Picture.class));
        };
        model.addAttribute("pictureList",pictureList);

        String getPictureLarge1 = pictureList.get(0).getImage_urls().get("large");
        HttpHeaders headers = new HttpHeaders();
        headers.set("referer","https://app-api.pixiv.net/");
        ResponseEntity<byte[]> entity = restTemplate
                .exchange(getPictureLarge1, HttpMethod.GET,new HttpEntity<>(headers),byte[].class);
        byte[] picBody1 = entity.getBody();
        OutputStream outputStream =
                new FileOutputStream(new File("D:\\springbootProject\\resource\\pictures\\"+pictureList.get(0).getId()+".jpg"));
        outputStream.write(picBody1);
        outputStream.flush();
        outputStream.close();

        String getPictureLarge2 = pictureList.get(1).getImage_urls().get("large");

        ResponseEntity<byte[]> entity2 = restTemplate
                .exchange(getPictureLarge2, HttpMethod.GET,new HttpEntity<>(headers),byte[].class);
        byte[] picBody2 = entity.getBody();
        OutputStream outputStream2 =
                new FileOutputStream(new File("D:\\springbootProject\\resource\\pictures\\"+pictureList.get(1).getId()+".jpg"));
        outputStream2.write(picBody2);
        outputStream2.flush();
        outputStream2.close();
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
