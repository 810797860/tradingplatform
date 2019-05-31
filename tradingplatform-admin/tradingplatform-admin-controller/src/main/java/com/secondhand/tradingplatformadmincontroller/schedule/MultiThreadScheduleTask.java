package com.secondhand.tradingplatformadmincontroller.schedule;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.secondhand.tradingplatformadminservice.service.admin.business.ShortMessageService;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ShortMessageService shortMessageService;

    /**
     * 每天凌晨五点执行
     */
    @Async("taskExecutor")
    @Scheduled(cron = "0 0 5 * * ?")
    public void scheduleTest() throws ClientException {

        //定义的变量
        //先默认为佛山
        String city = "佛山";
        String weather = "";
        String tem1 = "";
        String tem2 = "";
        String clothes = "";
        String yi = "";
        String ji = "";
        RestTemplate restTemplate = new RestTemplate();
        //找天气
        String weaResults = restTemplate.exchange("https://www.tianqiapi.com/api/?required=v1&cityid=101280800&city=%E4%BD%9B%E5%B1%B1", HttpMethod.GET, null, String.class).getBody();
        //unicode转中文
        weaResults = ToolUtil.asciiToNative(weaResults);
        //String转map
        Gson gson = new Gson();
        Map<String, Object> weaMap = new HashMap<>();
        weaMap = gson.fromJson(weaResults, weaMap.getClass());
        //获取今天天气
        if (weaMap.containsKey("data")) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) weaMap.get("data");
            if (dataList.size() > 0) {
                Map<String, Object> dateZeroMap = dataList.get(0);
                //获取wea
                if (dateZeroMap.containsKey("wea")) {
                    weather = dateZeroMap.get("wea").toString();
                }
                //获取气温
                if (dateZeroMap.containsKey("tem2")) {
                    tem1 = dateZeroMap.get("tem2").toString();
                }
                if (dateZeroMap.containsKey("tem1")) {
                    tem2 = dateZeroMap.get("tem1").toString();
                }
                //获取穿衣指数
                //获取index
                if (dateZeroMap.containsKey("index")) {
                    List<Map<String, Object>> indexList = (List<Map<String, Object>>) dateZeroMap.get("index");
                    for (Map<String, Object> indexMap : indexList) {
                        if (indexMap.get("title").equals("穿衣指数")) {
                            clothes = indexMap.get("desc").toString();
                            break;
                        }
                    }
                }
            }
        }
        //计算日期
        String todayStr = ToolUtil.getToday();
        //老黄历宜忌
        String yellowResults = restTemplate.exchange("http://v.juhe.cn/laohuangli/d?date=" + todayStr + "&key=7fc27b2eafb693c605b18273ad9eca1a", HttpMethod.GET, null, String.class).getBody();
        Map<String, Object> yellowMap = new HashMap<>();
        yellowMap = gson.fromJson(yellowResults, yellowMap.getClass());
        //宜
        if (yellowMap.containsKey("result")) {
            Map<String, Object> resultMap = (Map<String, Object>) yellowMap.get("result");
            if (resultMap.containsKey("yi")) {
                yi = resultMap.get("yi").toString();
                //判断yi是否超长
                if (yi.length() > 20){
                    yi = yi.substring(0, 19);
                    //然后再判断后面不要有空格
                    yi = yi.substring(0, yi.lastIndexOf(" "));
                }
            }
            if (resultMap.containsKey("ji")) {
                ji = resultMap.get("ji").toString();
                //判断ji是否超长
                if (ji.length() > 20){
                    ji = ji.substring(0, 19);
                    //然后再判断后面不要有空格
                    ji = ji.substring(0, ji.lastIndexOf(" "));
                }
            }
        }
        shortMessageService.dailyTextMessage(city, weather, tem1, tem2, clothes, yi, ji, "13809221266");
        shortMessageService.dailyTextMessage(city, weather, tem1, tem2, clothes, yi, ji, "13724926828");
        shortMessageService.dailyTextMessage(city, weather, tem1, tem2, clothes, yi, ji, "13068714662");
        System.out.println("元旦快乐！！！");
    }
}
