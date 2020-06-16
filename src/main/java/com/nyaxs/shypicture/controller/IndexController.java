package com.nyaxs.shypicture.controller;

import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.PictureData;
import com.nyaxs.shypicture.bean.SearchCondition;
import com.nyaxs.shypicture.bean.jsonpojo.picture.ResponseItem;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.ImageUrls;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponsePictures;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.Work;
import com.nyaxs.shypicture.bean.jsonpojo.picturelists.WorksItem;
import com.nyaxs.shypicture.mapper.PictureMapper;
import com.nyaxs.shypicture.util.AsyncDownload;
import com.nyaxs.shypicture.util.ConvertToPictureData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private AsyncDownload asyncDownload;

    public List<PictureData> getPictureDataList(SearchCondition condition) throws Exception {
        log.info("开始执行getPictureDataList方法，获取目标图片信息");
        log.info("设置搜索条件: " + condition.toString());
        PictureController pictureController = new PictureController();
        ResponsePictures responsePictures = pictureController.getPictureListWithJsonPojo(condition);
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponseItem responseItem = responsePictures.getResponse().get(0);
        WorksItem worksItem = responseItem.getWorks().get(0);
        List<WorksItem> worksItems = responseItem.getWorks();
        List<Work> works = null;
        log.info("worksItem的内容:" + worksItem.toString());
        List<PictureData> pictureDataList = new ArrayList<>();
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.User user = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.Stats stats = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.FavoritedCount favoritedCount = null;
        Work work = null;
        log.info("遍历workItems,为PictureData注值");
        for (WorksItem item : worksItems) {
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
            log.info("遍历中注入的pictureData值为：" + pictureData.toString());
            pictureDataList.add(pictureData);
        }
        log.info("注入后的pictureDataList值为" + pictureDataList.toString());
        return pictureDataList;
    }

    public List<String> asyncDownloadPictures(List<PictureData> pictures) throws Exception {
        log.info("开始调用AsyncDownload执行异步下载");
        for (PictureData pictureData :
                pictures) {
            log.info("pictureDataList各个id为：" + pictureData.getPictureId());
        }
        long startTime = System.currentTimeMillis();
        log.info("开始执行异步下载");
        List<Future<String>> taskList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        int taskCount = 0;
        for (PictureData pictureData :
                pictures) {
            taskCount++;
            log.info("第" + taskCount + "次下载");
            Future<String> task = asyncDownload.asyncDownloadOnePicture1(pictureData.getLargeUrl(), pictureData.getPictureId());
            taskList.add(task);
        }
        for (Future<String> future :
                taskList) {
            log.info(future + "线程在这里阻塞，等待该任务完成");
            String result = future.get();
            log.info("线程任务获取到结果：" + result);
            resultList.add(result);
        }
        long endTime = System.currentTimeMillis();
        log.info("下载完成,下载耗时：" + (endTime - startTime));

        log.info("向数据库中插入下载数据");
        pictureMapper.insertManyPicturesByPictureData(pictures);
        log.info("数据库处理完毕");

        return resultList;
    }

    public List<PictureData> getAndDownLoadWithCondition(SearchCondition condition) throws Exception {
        log.info("访问/" + condition.getRankMode());
        log.info("设置搜索条件为" + condition.toString());
        log.info("开始执行GetAndDownPicture");
        List<PictureData> pictureList = getPictureDataList(condition);
        List<String> resultList = asyncDownloadPictures(pictureList);
        log.info("下载完毕,获得返回的resultList" + resultList.toString());
        return pictureList;
    }

    /*public List<PictureData> getAndDownPicture(SearchCondition condition) throws Exception {

        log.info("设置搜索条件rankPerPage：" + condition.getRankPerPage() + "rankMode：" + condition.getRankMode());
        PictureController pictureController = new PictureController();
        ResponsePictures responsePictures = pictureController.getPictureListWithJsonPojo(condition);
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponseItem responseItem = responsePictures.getResponse().get(0);
        WorksItem worksItem = responseItem.getWorks().get(0);
        List<WorksItem> worksItems = responseItem.getWorks();
        List<Work> works = null;
        log.info("worksItem的内容:" + worksItem.toString());
        log.info("worksItem的内容:" + worksItems.toString());
        List<PictureData> pictureDataList = new ArrayList<>();

        com.nyaxs.shypicture.bean.jsonpojo.picturelists.User user = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.Stats stats = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.FavoritedCount favoritedCount = null;
        Work work = null;
        log.info("遍历workItems,为PictureData注值");
        for (WorksItem item : worksItems) {
            log.info("遍历worksItems中item的值为：" + item.toString());
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
            log.info("遍历中注入的pictureData值为：" + pictureData.toString());
            pictureDataList.add(pictureData);
        }
        log.info("注入后的pictureDataList值为" + pictureDataList.toString());
        log.info("开始调用DownloadUtil执行下载");
        DownloadUtil.get().downloadPicturesByPictureData(pictureDataList);
        log.info("下载结束");
        log.info("向数据库中插入下载数据");
        pictureMapper.insertManyPicturesByPictureData(pictureDataList);
        log.info("数据库处理完毕");
        return pictureDataList;
    }
*/
    //处理根路径请求
    @GetMapping("/")
    public String index() throws Exception {
        return "welcome";
    }

    @GetMapping("/random")
    public String random(Model model) throws Exception {
        PictureController pictureController = new PictureController();
        List<Picture> pictures = pictureMapper.findRandomPicture(1);
        Picture picture = pictures.get(0);
        ResponseItem item = pictureController.getPictureById(picture.getId());
        PictureData pictureData = ConvertToPictureData.fromResponseItem(item);
        List<PictureData> pictureDataList = new ArrayList<>();
        pictureDataList.add(pictureData);
        model.addAttribute("pictures", pictureDataList);
        return "index";
    }


    @GetMapping("/daily")
    public String daily(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("daily");
        condition.setRankPerPage(2);
        model.addAttribute("pictures", getAndDownLoadWithCondition(condition));
        log.info("添加对象到model", model);
        log.info("返回index视图");
        return "index";
    }

    @GetMapping("/weekly")
    public String weekly(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("weekly");
        condition.setRankPerPage(2);
        model.addAttribute("pictures", getAndDownLoadWithCondition(condition));
        log.info("添加对象到model", model);
        log.info("返回index视图");
        return "index";
    }

    @GetMapping("/monthly")
    public String monthly(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("monthly");
        condition.setRankPerPage(2);
        model.addAttribute("pictures", getAndDownLoadWithCondition(condition));
        log.info("添加对象到model", model);
        log.info("返回index视图");
        return "index";
    }


    @GetMapping("/asyncDaily")
    public String someday(Model model) throws Exception {
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("daily");
        condition.setDate("2020-03-01");
        log.info("搜索时间：" + condition.getDate());
        log.info("访问/" + condition.getRankMode());
        condition.setRankPerPage(5);
        log.info("设置搜索条件为perpage=" + condition.getRankPerPage() + ";rankMode=" + condition.getRankMode());

        log.info("开始获取pictureList信息");

        log.info("设置搜索条件rankPerPage：" + condition.getRankPerPage() + "rankMode：" + condition.getRankMode());
        PictureController pictureController = new PictureController();
        ResponsePictures responsePictures = pictureController.getPictureListWithJsonPojo(condition);
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.ResponseItem responseItem = responsePictures.getResponse().get(0);
        WorksItem worksItem = responseItem.getWorks().get(0);
        List<WorksItem> worksItems = responseItem.getWorks();
        List<Work> works = null;
        log.info("worksItem的内容:" + worksItem.toString());
        log.info("worksItem的内容:" + worksItems.toString());
        List<PictureData> pictureDataList = new ArrayList<>();

        com.nyaxs.shypicture.bean.jsonpojo.picturelists.User user = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.Stats stats = null;
        com.nyaxs.shypicture.bean.jsonpojo.picturelists.FavoritedCount favoritedCount = null;
        Work work = null;
        log.info("遍历workItems,为PictureData注值");
        for (WorksItem item : worksItems) {
            log.info("遍历worksItems中item的值为：" + item.toString());
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
            log.info("遍历中注入的pictureData的Id为：" + pictureData.getPictureId());
            pictureDataList.add(pictureData);
        }
        log.info("注入后的pictureDataList值为" + pictureDataList.toString());
        for (PictureData pictureData :
                pictureDataList) {
            log.info("各个id为：" + pictureData.getPictureId());
        }
        long startTime = System.currentTimeMillis();
        log.info("开始执行异步下载");
     /*   PictureData pictureData1 = pictureDataList.get(0);
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
        */

        for (int i = 0; i < pictureDataList.size(); i++) {
            log.info("第" + i + "次下载");
            PictureData pictureData1 = pictureDataList.get(i);
            Future<String> task = asyncDownload.asyncDownloadOnePicture1(pictureData1.getLargeUrl(), pictureData1.getPictureId());
        }
        long endTime = System.currentTimeMillis();
        log.info("下载耗时：" + (endTime - startTime));
        log.info("下载完毕,获得返回的pictureList" + pictureDataList.toString());
        model.addAttribute("pictures", pictureDataList);
        log.info("添加对象到model");
        log.info("返回random视图");
        return "random";
    }


    @Scheduled(cron = "0 3 9 * * *")
    public List<PictureData> scheduledDownloadDailyRank() throws Exception {
        log.info("开始定时任务，现在的时间是：" + LocalDate.now());
        SearchCondition condition = new SearchCondition();
        condition.setRankMode("daily");
        condition.setRankPerPage(10);
        log.info("condition条件是：" + condition.toString());
        List<PictureData> pictureDataList = getAndDownLoadWithCondition(condition);
        return pictureDataList;
    }


}
