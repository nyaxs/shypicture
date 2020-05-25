package com.nyaxs.shypicture.util;

import com.nyaxs.shypicture.bean.Picture;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DownloadUtil {

    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

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

    public void downloadPicturesByList(List<Picture> pictureList) throws Exception {

        OkHttpClient client = okHttpClient.newBuilder()
                .readTimeout(600, TimeUnit.SECONDS)
                .callTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();

        for (int i = 0; i < pictureList.size(); i++) {
            Picture picture1 = pictureList.get(i);
            Request request = new Request.Builder()
                    .header("referer", "https://app-api.pixiv.net/")
                    .url(picture1.getImage_urls().get("large"))
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected Code " + response);
                }
                ResponseBody responseBody = response.body();

                file = new File(downPath + picture1.getId() + ".jpg");

                if (file.exists()) {
                    continue;
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

    public void downloadPictureById(Picture picture1) throws Exception {
        OkHttpClient client = okHttpClient.newBuilder()
                .readTimeout(600, TimeUnit.SECONDS)
                .callTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .header("referer", "https://app-api.pixiv.net/")
                .url(picture1.getImage_urls().get("large"))
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

