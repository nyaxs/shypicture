package com.nyaxs.shypicture.util;

import com.nyaxs.shypicture.bean.PictureData;
import com.nyaxs.shypicture.bean.jsonpojo.picture.*;

/**
 * 将获取到的实体信息转化为PictureData，用于传输到前端
 *
 * @author nyaxs
 * @version V1.0
 * @Package com.nyaxs.shypicture.util
 * @date 2020/6/3 9:18
 */
public class ConvertToPictureData {

    public static PictureData fromResponseItem(ResponseItem item) {

        PictureData pictureData = new PictureData();
        User user = item.getUser();
        Stats stats = item.getStats();
        FavoritedCount favoritedCount = stats.getFavoritedCount();
        ImageUrls imageUrls = item.getImageUrls();

        pictureData.setPictureId(item.getId());
        pictureData.setTags(item.getTags());
        pictureData.setPictureTitle(item.getTitle());
        if ("r18".equals(item.getAgeLimit())) {
            pictureData.setAgeLimit(1);
        }
        pictureData.setLargeUrl(imageUrls.getLarge());
        pictureData.setCreatedTime(item.getCreatedTime());
        pictureData.setAuthorId(user.getId());
        pictureData.setAuthorName(user.getName());
        pictureData.setCollectCount(favoritedCount.getJsonMemberPublic());
        pictureData.setViewCount(stats.getViewsCount());
        pictureData.setScoredCount(stats.getScoredCount());

        return pictureData;

    }
}
