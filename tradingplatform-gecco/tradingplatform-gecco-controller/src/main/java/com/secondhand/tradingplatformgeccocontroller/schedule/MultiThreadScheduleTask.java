package com.secondhand.tradingplatformgeccocontroller.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 多线程定时任务
 *
 * @author 81079
 * @EnableScheduling 开启定时业务
 * @EnableAsync 开启多线程
 */
@Component
@EnableScheduling
@EnableAsync
public class MultiThreadScheduleTask {

    @Async("taskExecutor")
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void scheduleTest() {
        //测试定时业务，每年执行一次
        System.out.println("元旦快乐！！！");
    }
}
