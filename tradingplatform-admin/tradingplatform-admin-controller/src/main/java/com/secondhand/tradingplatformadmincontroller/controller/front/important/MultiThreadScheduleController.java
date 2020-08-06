package com.secondhand.tradingplatformadmincontroller.controller.front.important;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.secondhand.tradingplatformadminservice.service.admin.business.ShortMessageService;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 手动调用发送短信接口
 * @author Administrator
 * @date 2020/8/6
 */
@Controller("frontMultiThreadScheduleController")
@Api(value = "/front/multiThreadSchedule", description = "MultiThreadSchedule 控制器")
@RequestMapping("/front/multiThreadSchedule")
public class MultiThreadScheduleController extends BaseController {

    @Autowired
    private ShortMessageService shortMessageService;

    @GetMapping(value = "/send_messages", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/send_messages", notes = "手动发送天气短信")
    @ResponseBody
    public JsonResult<String> sendMessages(@ApiParam(name = "手机号码", value = "mobilePhoneNumber") @RequestParam(value = "mobilePhoneNumber", required = false) String mobilePhoneNumber) throws ClientException {
        //定义的变量
        //先默认为佛山
        String city = "佛山";
        String weather = "";
        String tem1 = "";
        String tem2 = "";
        String humidity = "";
        String gCity = "广州";
        String gWeather = "";
        String gTem1 = "";
        String gTem2 = "";
        String gHumidity = "";
        String yi = "";
        String ji = "";
        RestTemplate restTemplate = new RestTemplate();
        //找佛山天气
        String weaResults = restTemplate.exchange("https://www.tianqiapi.com/api/?version=v6&appid=27932465&appsecret=7Aby1eHz&cityid=101280800", HttpMethod.GET, null, String.class).getBody();
        //unicode转中文
        weaResults = ToolUtil.asciiToNative(weaResults);
        //String转map
        Gson gson = new Gson();
        Map<String, Object> weaMap = new HashMap<>();
        weaMap = gson.fromJson(weaResults, weaMap.getClass());
        //获取今天天气
        //气温
        if (weaMap.containsKey("wea")){
            weather = weaMap.get("wea").toString();
        }
        //最低气温
        if (weaMap.containsKey("tem2")){
            tem1 = weaMap.get("tem2").toString() + "℃";
        }
        if (weaMap.containsKey("tem1")){
            tem2 = weaMap.get("tem1").toString() + "℃";
        }
        if (weaMap.containsKey("humidity")){
            humidity = "相对湿度" + weaMap.get("humidity").toString() + "。";
        }
        //找广州天气
        String gWeaResults = restTemplate.exchange("https://www.tianqiapi.com/api/?version=v6&appid=27932465&appsecret=7Aby1eHz&cityid=101280101", HttpMethod.GET, null, String.class).getBody();
        //unicode转中文
        gWeaResults = ToolUtil.asciiToNative(gWeaResults);
        //String转map
        Map<String, Object> gWeaMap = new HashMap<>();
        gWeaMap = gson.fromJson(gWeaResults, gWeaMap.getClass());
        //获取天气
        if (gWeaMap.containsKey("wea")){
            gWeather = gWeaMap.get("wea").toString();
        }
        if (gWeaMap.containsKey("tem2")){
            gTem1 = gWeaMap.get("tem2").toString() + "℃";
        }
        if (gWeaMap.containsKey("tem1")){
            gTem2 = gWeaMap.get("tem1").toString() + "℃";
        }
        if (gWeaMap.containsKey("humidity")){
            gHumidity = "相对湿度" + gWeaMap.get("humidity").toString() + "。";
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
                if (yi.length() > 9){
                    yi = yi.substring(0, 8);
                    //然后再判断后面不要有空格
                    yi = yi.substring(0, yi.lastIndexOf(" "));
                }
            }
            if (ToolUtil.strIsEmpty(yi)){
                yi = "诸事不宜";
            }
            if (resultMap.containsKey("ji")) {
                ji = resultMap.get("ji").toString();
                //判断ji是否超长
                if (ji.length() > 9){
                    ji = ji.substring(0, 8);
                    //然后再判断后面不要有空格
                    ji = ji.substring(0, ji.lastIndexOf(" "));
                }
            }
            if (ToolUtil.strIsEmpty(yi)){
                yi = "诸事不忌";
            }
        }
        if (ToolUtil.strIsEmpty(mobilePhoneNumber)) {
            shortMessageService.dailyTextMessage(city, weather, tem1, tem2, humidity, yi, ji, "13809221266");
            shortMessageService.dailyTextMessage(city, weather, tem1, tem2, humidity, yi, ji, "13724926828");
            shortMessageService.dailyTextMessage(city, weather, tem1, tem2, humidity, yi, ji, "13068714662");
            shortMessageService.dailyTextMessage(city, weather, tem1, tem2, humidity, yi, ji, "13925962430");
            shortMessageService.dailyTextMessage(city, weather, tem1, tem2, humidity, yi, ji, "13265683120");
            shortMessageService.dailyTextMessage(city, weather, tem1, tem2, humidity, yi, ji, "13652288353");
        } else {
            shortMessageService.dailyTextMessage(city, weather, tem1, tem2, humidity, yi, ji, mobilePhoneNumber);
        }
//        shortMessageService.dailyTextMessage(gCity, gWeather, gTem1, gTem2, gHumidity, yi, ji, "13652288353");
        //        System.out.println("已经发送了，可以关闭程序了");
        JsonResult<String> resJson = new JsonResult<>();
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData("ok");
        resJson.setSuccess(true);
        return resJson;
    }

}
