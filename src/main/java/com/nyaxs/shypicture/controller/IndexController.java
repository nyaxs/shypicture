package com.nyaxs.shypicture.controller;

import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.PictureData;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.bean.jsonpojo.picture.FavoritedCount;
import com.nyaxs.shypicture.bean.jsonpojo.picture.ResponseItem;
import com.nyaxs.shypicture.bean.jsonpojo.picture.Stats;
import com.nyaxs.shypicture.bean.jsonpojo.picture.User;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.ImageUrls;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponsePictures;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.Work;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.WorksItem;
import com.nyaxs.shypicture.mapper.PictureMapper;
import com.nyaxs.shypicture.util.AsyncDownload;
import com.nyaxs.shypicture.util.DownloadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Controller
public class IndexController {

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private AsyncDownload asyncDownload;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    RestTemplate restTemplate = null;
    LocalDate today = null;
    LocalDate twoDaysAgo = null;


    public List<PictureData> getAndDownPicture(SearchCondition condition) throws Exception {

        logger.info("设置搜索条件rankPerPage：" + condition.getRankPerPage() + "rankMode：" + condition.getRankMode());
        PictureController pictureController = new PictureController();
        ResponsePictures responsePictures = pictureController.getPictureListWithJsonPojo(condition);
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponseItem responseItem = responsePictures.getResponse().get(0);
        WorksItem worksItem = responseItem.getWorks().get(0);
        List<WorksItem> worksItems = responseItem.getWorks();
        List<Work> works = null;
        logger.info("worksItem的内容:" + worksItem.toString());
        logger.info("worksItem的内容:" + worksItems.toString());
        List<PictureData> pictureDataList = new ArrayList<>();

        com.nyaxs.shypicture.bean.jsonpojo.picturelists.User user = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.Stats stats = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.FavoritedCount favoritedCount = null;
        Work work = null;
        logger.info("遍历workItems,为PictureData注值");
        for (WorksItem item : worksItems) {
            logger.info("遍历worksItems中item的值为：" + item.toString());
            work = item.getWork();
            ImageUrls imageUrls = work.getImageUrls();
            String largeUrl = imageUrls.getLarge();
            user = work.getUser();
            stats = work.getStats();
            favoritedCount = stats.getFavoritedCount();
            PictureData pictureData = new PictureData();
            pictureData.setAuthorId(user.getId());
            pictureData.setAuthorName(user.getName());
            pictureData.setPictureId(work.getId());
            pictureData.setPictureTitle(work.getTitle());
            if (imageUrls.getLarge() != null) {
                pictureData.setLargeUrl(imageUrls.getLarge());
            }
            if (!work.getAgeLimit().equals("all-age")) {
                pictureData.setAgeLimit(1);
            }
            pictureData.setCreatedTime(work.getCreatedTime());
            pictureData.setTags(work.getTags());
            pictureData.setCollectCount(23333);
            if (favoritedCount.getJsonMemberPublic() != null) {
                pictureData.setCollectCount((int) favoritedCount.getJsonMemberPublic());
            }
            pictureData.setScoredCount(stats.getScoredCount());
            pictureData.setViewCount(stats.getViewsCount());
            logger.info("遍历中注入的pictureData值为：" + pictureData.toString());
            pictureDataList.add(pictureData);
        }
        logger.info("注入后的pictureDataList值为" + pictureDataList.toString());
        logger.info("开始调用DownloadUtil执行下载");
        DownloadUtil.get().downloadPicturesByPictureData(pictureDataList);
        logger.info("下载结束");
        logger.info("向数据库中插入下载数据");
        pictureMapper.insertManyPicturesByPictureData(pictureDataList);
        logger.info("数据库处理完毕");
        return pictureDataList;

        /*
        使用pictureList传值的旧方法
        PictureController pictureController = new PictureController();
        logger.info("开始获取pictureList", pictureController.getPictureList(condition));
        List<Picture> pictureList = pictureController.getPictureList(condition);
        logger.info("已获取根据condition要求的pictureList", pictureList);
        logger.info("开始执行download");
        DownloadUtil.get().downloadPicturesByList(pictureList);
        logger.info("已下载pictureList,开始存入下载的id到数据库中");
        pictureMapper.insertManyPictures(pictureList);
        logger.info("存入数据库完毕，返回pictureList");
        return pictureList;*/
    }

    //处理根路径请求
    @GetMapping("/")
    public String index() throws Exception {
        asyncDownload.doTaskOne();
        asyncDownload.doTaskTwo();
        asyncDownload.doTaskThree();
        return "welcome";
    }

    @GetMapping("/index")
    public String index(Model model) throws Exception {
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
        return "index";
    }


    @GetMapping("/daily")
    public String everyday(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("daily");
        logger.info("访问/" + condition.getRankMode());
        condition.setRankPerPage(5);
        logger.info("设置搜索条件为perpage=" + condition.getRankPerPage() + ";rankMode=" + condition.getRankMode());

        logger.info("开始执行getAndDownPicture(Condetion)", getAndDownPicture(condition));
        List<PictureData> pictureList = getAndDownPicture(condition);
        logger.info("下载完毕,获得返回的pictureList" + pictureList.toString());
        model.addAttribute("pictures", pictureList);
        logger.info("添加对象到model", model);
        logger.info("返回random视图");
        return "random";
    }

    @GetMapping("/weekly")
    public String weekly(Model model) throws Exception {
        logger.info("访问/weekly");
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("weekly");
        condition.setRankPerPage(3);
        logger.info("设置搜索条件rankPerPage：" + condition.getRankPerPage() + "rankMode：" + condition.getRankMode());
        PictureController pictureController = new PictureController();
        ResponsePictures responsePictures = pictureController.getPictureListWithJsonPojo(condition);
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponseItem responseItem = responsePictures.getResponse().get(0);
        WorksItem worksItem = responseItem.getWorks().get(0);
        List<WorksItem> worksItems = responseItem.getWorks();
        List<Work> works = null;
        logger.info("worksItem的内容:" + worksItem.toString());
        logger.info("worksItem的内容:" + worksItems.toString());
        List<PictureData> pictureDataList = new ArrayList<>();

        com.nyaxs.shypicture.bean.jsonpojo.picturelists.User user = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.Stats stats = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.FavoritedCount favoritedCount = null;
        Work work = null;
        logger.info("遍历workItems,为PictureData注值");
        for (WorksItem item : worksItems) {
            logger.info("遍历worksItems中item的值为：" + item.toString());
            work = item.getWork();
            ImageUrls imageUrls = work.getImageUrls();
            String largeUrl = imageUrls.getLarge();
            user = work.getUser();
            stats = work.getStats();
            favoritedCount = stats.getFavoritedCount();
            PictureData pictureData = new PictureData();
            pictureData.setAuthorId(user.getId());
            pictureData.setAuthorName(user.getName());
            pictureData.setPictureId(work.getId());
            pictureData.setPictureTitle(work.getTitle());
            if (imageUrls.getLarge() != null) {
                pictureData.setLargeUrl(imageUrls.getLarge());
            }
            if (!work.getAgeLimit().equals("all-age")) {
                pictureData.setAgeLimit(1);
            }
            pictureData.setCreatedTime(work.getCreatedTime());
            pictureData.setTags(work.getTags());
            pictureData.setCollectCount(23333);
            if (favoritedCount.getJsonMemberPublic() != null) {
                pictureData.setCollectCount((int) favoritedCount.getJsonMemberPublic());
            }
            pictureData.setScoredCount(stats.getScoredCount());
            pictureData.setViewCount(stats.getViewsCount());
            logger.info("遍历中注入的pictureData值为：" + pictureData.toString());
            pictureDataList.add(pictureData);
        }
        logger.info("注入后的pictureDataList值为" + pictureDataList.toString());
        logger.info("开始调用DownloadUtil执行下载");
        DownloadUtil.get().downloadPicturesByPictureData(pictureDataList);
        logger.info("下载结束");
        logger.info("向数据库中插入下载数据");
        pictureMapper.insertManyPicturesByPictureData(pictureDataList);
        logger.info("数据库处理完毕");
        model.addAttribute("pictures", pictureDataList);
        logger.info("model放入pictureDataList");
        return "random";

    }

    @GetMapping("/monthly")
    public String monthly(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("monthly");
        logger.info("访问/" + condition.getRankMode());
        condition.setRankPerPage(5);
        logger.info("设置搜索条件为perpage=" + condition.getRankPerPage() + ";rankMode=" + condition.getRankMode());

        logger.info("开始执行getAndDownPicture(Condetion)", getAndDownPicture(condition));
        List<PictureData> pictureList = getAndDownPicture(condition);
        logger.info("下载完毕,获得返回的pictureList" + pictureList.toString());
        model.addAttribute("pictures", pictureList);
        logger.info("添加对象到model", model);
        logger.info("返回random视图");
        return "random";
    }


    @GetMapping("/asyncDaily")
    public String someday(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("daily");
        condition.setDate("2020-02-25");
        logger.info("搜索时间：" + condition.getDate());
        logger.info("访问/" + condition.getRankMode());
        condition.setRankPerPage(5);
        logger.info("设置搜索条件为perpage=" + condition.getRankPerPage() + ";rankMode=" + condition.getRankMode());

        logger.info("开始获取pictureList信息");

        logger.info("设置搜索条件rankPerPage：" + condition.getRankPerPage() + "rankMode：" + condition.getRankMode());
        PictureController pictureController = new PictureController();
        ResponsePictures responsePictures = pictureController.getPictureListWithJsonPojo(condition);
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponseItem responseItem = responsePictures.getResponse().get(0);
        WorksItem worksItem = responseItem.getWorks().get(0);
        List<WorksItem> worksItems = responseItem.getWorks();
        List<Work> works = null;
        logger.info("worksItem的内容:" + worksItem.toString());
        logger.info("worksItem的内容:" + worksItems.toString());
        List<PictureData> pictureDataList = new ArrayList<>();

        com.nyaxs.shypicture.bean.jsonpojo.picturelists.User user = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.Stats stats = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.FavoritedCount favoritedCount = null;
        Work work = null;
        logger.info("遍历workItems,为PictureData注值");
        for (WorksItem item : worksItems) {
            logger.info("遍历worksItems中item的值为：" + item.toString());
            work = item.getWork();
            ImageUrls imageUrls = work.getImageUrls();
            String largeUrl = imageUrls.getLarge();
            user = work.getUser();
            stats = work.getStats();
            favoritedCount = stats.getFavoritedCount();
            PictureData pictureData = new PictureData();
            pictureData.setAuthorId(user.getId());
            pictureData.setAuthorName(user.getName());
            pictureData.setPictureId(work.getId());
            pictureData.setPictureTitle(work.getTitle());
            if (imageUrls.getLarge() != null) {
                pictureData.setLargeUrl(imageUrls.getLarge());
            }
            if (!"all-age".equals(work.getAgeLimit())) {
                pictureData.setAgeLimit(1);
            }
            pictureData.setCreatedTime(work.getCreatedTime());
            pictureData.setTags(work.getTags());
            pictureData.setCollectCount(23333);
            if (favoritedCount.getJsonMemberPublic() != null) {
                pictureData.setCollectCount((int) favoritedCount.getJsonMemberPublic());
            }
            pictureData.setScoredCount(stats.getScoredCount());
            pictureData.setViewCount(stats.getViewsCount());
            logger.info("遍历中注入的pictureData值为：" + pictureData.toString());
            pictureDataList.add(pictureData);
        }
        logger.info("注入后的pictureDataList值为" + pictureDataList.toString());
        for (PictureData pictureData :
                pictureDataList) {
            logger.info("各个id为：" + pictureData.getPictureId());
        }
        long startTime = System.currentTimeMillis();
        logger.info("开始执行异步下载");
        PictureData pictureData1 = pictureDataList.get(0);
        PictureData pictureData2 = pictureDataList.get(1);
        PictureData pictureData3 = pictureDataList.get(2);
        PictureData pictureData4 = pictureDataList.get(3);
        PictureData pictureData5 = pictureDataList.get(4);
        Future<String> task1 = asyncDownload.asyncDownloadOnePicture1(pictureData1.getLargeUrl(), pictureData1.getPictureId());
        Future<String> task2 = asyncDownload.asyncDownloadOnePicture1(pictureData2.getLargeUrl(), pictureData2.getPictureId());
        Future<String> task3 = asyncDownload.asyncDownloadOnePicture1(pictureData3.getLargeUrl(), pictureData3.getPictureId());
        Future<String> task4 = asyncDownload.asyncDownloadOnePicture1(pictureData4.getLargeUrl(), pictureData4.getPictureId());
        Future<String> task5 = asyncDownload.asyncDownloadOnePicture1(pictureData5.getLargeUrl(), pictureData5.getPictureId());
        for (; ; ) {
            if (task1.isDone() && task2.isDone() && task3.isDone() && task4.isDone() && task5.isDone()) {
                break;
            }
            Thread.sleep(1000);
        }


        /*for (int i = 0; i < pictureDataList.size(); i++) {
            logger.info("第"+i+"次下载");
            PictureData pictureData1 = pictureDataList.get(i);
            Future<String> task =  asyncDownload.asyncDownloadOnePicture1(pictureData1.getLargeUrl(),pictureData1.getPictureId());
        }*/
        long endTime = System.currentTimeMillis();
        logger.info("下载耗时：" + (endTime - startTime));
        logger.info("下载完毕,获得返回的pictureList" + pictureDataList.toString());
        model.addAttribute("pictures", pictureDataList);
        logger.info("添加对象到model");
        logger.info("返回random视图");
        return "random";
    }

     /*  @GetMapping("/random")
    public String random(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("daily");
        logger.info("访问/"+condition.getRankMode());
        condition.setRankPerPage(5);
        logger.info("设置搜索条件为perpage="+condition.getRankPerPage()+";rankMode="+condition.getRankMode());

        logger.info("开始执行getAndDownPicture(Condetion)", getAndDownPicture(condition));
        List<PictureData> pictureList = getAndDownPicture(condition);
        logger.info("下载完毕,获得返回的pictureList" + pictureList.toString());
        model.addAttribute("pictures", pictureList);
        logger.info("添加对象到model", model);
        logger.info("返回random视图");
        return "random";
    }*/


}
