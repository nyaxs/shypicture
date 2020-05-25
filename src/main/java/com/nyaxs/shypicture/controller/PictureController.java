package com.nyaxs.shypicture.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.util.JsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PictureController {

    private final OkHttpClient client = new OkHttpClient();

    LocalDate today = null;
    LocalDate twoDaysAgo = null;

    public void getDailyPictureList() throws Exception {
        today = LocalDate.now();
        twoDaysAgo = today.minusDays(2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false);
        String url = "https://api.imjad.cn/pixiv/v1/" +
                "?type=rank&mode=daily&per_page=5&content=illust&date="+twoDaysAgo;

        JsonNode resultRootNode = objectMapper.readTree(new URL(url));
        //Json node tree , use node.at() to get target node
        JsonNode worksNode = resultRootNode.at("/response").get(0).at("/works");

        String worksString = objectMapper.writeValueAsString(worksNode);

        List<Object> worksList = objectMapper
                .readValue(worksString, new TypeReference<List<Object>>(){});

        List<Picture> pictureList = new ArrayList<Picture>();
        //保存pictureList的大图下载链接
        //List<String>  pictureImageLargeUrls = new ArrayList<String>();

        for (Object obj:worksList) {
            Map<String,Object> workMap = (Map<String, Object>) obj;
            String workStr = JsonUtil.obj2String(workMap.get("work")) ;
            Picture picture = objectMapper.readValue(workStr,Picture.class);
            pictureList.add(picture);
            //pictureImageLargeUrls.add(picture.getImage_urls().get("large"));
        };

        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        byte[] bytes = null;


        for (int i = 0; i < pictureList.size(); i++){
            Picture picture1 = pictureList.get(i);
            Request request = new Request.Builder()
                    .header("referer","https://app-api.pixiv.net/")
                    .url(picture1.getImage_urls().get("large"))
                    .build();
            try(Response response = client.newCall(request).execute()){
                if(!response.isSuccessful()){
                    throw new IOException("Unexpected Code " + response);
                }
                ResponseBody responseBody = response.body();

                file = new File("D:\\springbootProject\\resource\\pictures\\"
                        +picture1.getId()+".jpg");
                long total = responseBody.contentLength();
                inputStream = responseBody.byteStream();
                outputStream = new FileOutputStream(file);
                bytes = new byte[1024 * 8];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1){
                    outputStream.write(bytes,0,length);
                }
                outputStream.flush();
                outputStream.close();
            }
        }

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
    }

    public void getWeeklyPictureList(){

    }

    public void getMonthlyPictureList(){

    }

}
