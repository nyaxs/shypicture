package com.nyaxs.shypicture.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

/**
 * @author nyaxs
 * @version V1.0
 * @Package com.nyaxs.shypicture.util
 * @date 2020/5/31 14:39
 */
@Service
@Slf4j
public class AsyncDownload {

    @Async("taskExecutor")
    public Future<String> asyncDownloadOnePicture1(String url, int id) throws Exception {
        String downPath = "D:\\springbootProject\\resource\\pictures\\";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        byte[] bytes = null;
        log.info("进入asyncDownloadOnePicture1");
        long currentTimeMillisStart = System.currentTimeMillis();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(600, TimeUnit.SECONDS)
                .callTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .header("referer", "https://app-api.pixiv.net/")
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Code " + response);
            }
            ResponseBody responseBody = response.body();

            file = new File(downPath + id + ".jpg");

            if (file.exists()) {
                return new AsyncResult<String>("文件已存在，直接返回");
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
            long currentTimeMillisEnd = System.currentTimeMillis();
            log.info("下载耗时: " + (currentTimeMillisEnd - currentTimeMillisStart) + "ms");

            return new AsyncResult<String>("单个文件下载完成");
        }

    }

    private static Random random = new Random();

    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = currentTimeMillis();
        int num = random.nextInt(10000);
        sleep(num);
        long end = currentTimeMillis();
        System.out.println("sleep num: " + num);
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }


    @Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = currentTimeMillis();
        int num = random.nextInt(10000);
        sleep(num);
        long end = currentTimeMillis();
        System.out.println("sleep num: " + num);
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = currentTimeMillis();
        int num = random.nextInt(10000);
        sleep(num);
        System.out.println("sleep num: " + num);
        long end = currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }


}
