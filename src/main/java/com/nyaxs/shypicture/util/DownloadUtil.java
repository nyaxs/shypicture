package com.nyaxs.shypicture.util;

import com.nyaxs.shypicture.bean.Picture;
import com.nyaxs.shypicture.bean.PictureData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DownloadUtil {

    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    private static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    public static DownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    public DownloadUtil() {
        okHttpClient = new OkHttpClient();
    }


    private String downPath = "D:\\springbootProject\\resource\\pictures\\";

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }


    InputStream inputStream = null;
    OutputStream outputStream = null;
    File file = null;
    byte[] bytes = null;

    public void downloadPicturesByPictureData(List<PictureData> pictureData) throws Exception {
        logger.info("downloadPicturesByListWithJsonPojo(pictureList)方法");
        logger.info("配置okhttp的timeout时间800s");
        OkHttpClient client = okHttpClient.newBuilder()
                .readTimeout(800, TimeUnit.SECONDS)
                .callTimeout(800, TimeUnit.SECONDS)
                .writeTimeout(800, TimeUnit.SECONDS)
                .connectTimeout(800, TimeUnit.SECONDS)
                .build();
        logger.info("开始循环读取pictureList对象,根据id获取目标图像");
        for (int i = 0; i < pictureData.size(); i++) {
            PictureData picture1 = pictureData.get(i);
            logger.info("建立连接request");
            Request request = new Request.Builder()
                    .header("referer", "https://app-api.pixiv.net/")
                    .url(picture1.getLargeUrl())
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected Code " + response);
                }

                ResponseBody responseBody = response.body();
                logger.info("获取响应体", responseBody);
                logger.info("命名文件id.jpg");
                file = new File(downPath + picture1.getPictureId() + ".jpg");
                logger.info("跳过已存在的文件");
                if (file.exists()) {
                    continue;
                }
                long total = responseBody.contentLength();
                inputStream = responseBody.byteStream();
                outputStream = new FileOutputStream(file);
                bytes = new byte[1024 * 8];
                int length = 0;
                logger.info("开始将文件写入硬盘");
                while ((length = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, length);
                }
                logger.info("文件写入完毕");


                outputStream.flush();
                outputStream.close();
            }
        }
    }

    public void downloadPicturesByList(List<Picture> pictureList) throws Exception {
        logger.info("进入downloadPictureByList(pictureList)方法");
        logger.info("配置okhttp的timeout时间800s");
        OkHttpClient client = okHttpClient.newBuilder()
                .readTimeout(800, TimeUnit.SECONDS)
                .callTimeout(800, TimeUnit.SECONDS)
                .writeTimeout(800, TimeUnit.SECONDS)
                .connectTimeout(800, TimeUnit.SECONDS)
                .build();
        logger.info("开始循环读取pictureList对象,根据id获取目标图像");
        for (int i = 0; i < pictureList.size(); i++) {
            Picture picture1 = pictureList.get(i);
            logger.info("建立连接request");
            Request request = new Request.Builder()
                    .header("referer", "https://app-api.pixiv.net/")
                    .url(picture1.getImage_urls().get("large"))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected Code " + response);
                }

                ResponseBody responseBody = response.body();
                logger.info("获取响应体", responseBody);
                logger.info("命名文件id.jpg");
                file = new File(downPath + picture1.getId() + ".jpg");
                logger.info("跳过已存在的文件");
                if (file.exists()) {
                    continue;
                }
                long total = responseBody.contentLength();
                inputStream = responseBody.byteStream();
                outputStream = new FileOutputStream(file);
                bytes = new byte[1024 * 8];
                int length = 0;
                logger.info("开始将文件写入硬盘");
                while ((length = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, length);
                }
                logger.info("文件写入完毕");
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    public void downloadPictureById(Picture picture1) throws Exception {
        OkHttpClient client = okHttpClient.newBuilder()
                .readTimeout(600, TimeUnit.SECONDS)
                .callTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .header("referer", "https://app-api.pixiv.net/")
                .url(picture1.getLargeUrl())
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Code " + response);
            }
            ResponseBody responseBody = response.body();

            file = new File(downPath + picture1.getId() + ".jpg");

            if (file.exists()) {
                return;
            }
            long total = responseBody.contentLength();
            inputStream = responseBody.byteStream();
            outputStream = new FileOutputStream(file);
            bytes = new byte[1024 * 8];
            int length = 0;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.flush();
            outputStream.close();
        }
    }

}

