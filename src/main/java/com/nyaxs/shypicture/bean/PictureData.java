package com.nyaxs.shypicture.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nyaxs
 * @version V1.0
 * @Package com.nyaxs.shypicture.bean
 * @date 2020/5/29 16:49
 */
@Slf4j
@Data
public class PictureData {
    private int pictureId;
    private String pictureTitle;
    private String createdTime;
    private String authorName;
    private int authorId;
    private int scoredCount;
    private int collectCount;
    private int viewCount;
    private List<String> tags;
    private String largeUrl;
    private int ageLimit;

    public PictureData() {
        this.pictureId = 23333;
        this.pictureTitle = "空";
        this.createdTime = "1919-05-04";
        this.authorName = "空";
        this.authorId = 23333;
        this.scoredCount = 23333;
        this.collectCount = 23333;
        this.viewCount = 23333;
        this.tags = new ArrayList<String>();
        this.tags.add("空空如也");
        this.largeUrl = "largeUrl为空";
        this.ageLimit = 0;
    }


}
