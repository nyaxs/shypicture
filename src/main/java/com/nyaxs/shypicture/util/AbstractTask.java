package com.nyaxs.shypicture.util;

import org.springframework.scheduling.annotation.Async;

import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

/**
 * @author nyaxs
 * @version V1.0
 * @Package com.nyaxs.shypicture.util
 * @date 2020/5/31 15:50
 */
public abstract class AbstractTask {

    private static Random random = new Random();

    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = currentTimeMillis();
        sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }


    @Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = currentTimeMillis();
        sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = currentTimeMillis();
        sleep(random.nextInt(10000));
        long end = currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }

}
