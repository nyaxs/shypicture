package com.nyaxs.shypicture.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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
    LocalDate yesterday = null;

    //处理根路径请求
    @GetMapping("/")
    public String index(ModelMap map){
        map.addAttribute("host","http://localhost:8080/");
        return "index";
    }

    @GetMapping("/daily")
    public String everyday(Model model) throws Exception {
        today = LocalDate.now();
        yesterday = today.minusDays(1);
        restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false);
        String url = "https://api.imjad.cn/pixiv/v1/?type=rank&mode=daily&per_page=2&content=illust&date="+yesterday;

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
