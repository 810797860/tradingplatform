package com.secondhand.tradingplatformadmincontroller.controller.front.personal;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.personal.MyTransaction;
import com.secondhand.tradingplatformadminservice.service.front.personal.MyTransactionService;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author : zhangjk
 * @description : MyTransaction 控制器
 * @since : Create in 2019-04-09
 */
@Controller("frontMyTransactionController")
@Api(value = "/front/myTransaction", description = "MyTransaction 控制器")
@RequestMapping("/front/myTransaction")
public class MyTransactionController extends BaseController{

    @Autowired
    private MyTransactionService myTransactionService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getMyTransactionList(@ApiParam(name = "myTransaction", value = "MyTransaction 实体类") @RequestBody MyTransaction myTransaction) {
        Session session = SecurityUtils.getSubject().getSession();
        Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
        //查询条件，美化代码
        myTransaction.setUserId(userId);
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = myTransaction.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Long myTransactionTotal = myTransactionService.mySelectTotalWithParam(myTransaction);
        List<Map<String, Object>> myTransactionList = myTransactionService.mySelectListWithParam(myTransaction, current, size);
        resJson.setRecordsTotal(myTransactionTotal);
        resJson.setData(myTransactionList);
        resJson.setSuccess(true);
        return resJson;
    }


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @PostMapping(value = "/query_sale", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_sale", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getMyTransactionSaleList(@ApiParam(name = "myTransaction", value = "MyTransaction 实体类") @RequestBody MyTransaction myTransaction) {
        Session session = SecurityUtils.getSubject().getSession();
        Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
        //查询条件，美化代码
        myTransaction.setUserId(userId);
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = myTransaction.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Long myTransactionTotal = myTransactionService.mySelectSaleTotalWithParam(myTransaction);
        List<Map<String, Object>> myTransactionList = myTransactionService.mySelectSaleListWithParam(myTransaction, current, size);
        resJson.setRecordsTotal(myTransactionTotal);
        resJson.setData(myTransactionList);
        resJson.setSuccess(true);
        return resJson;
    }
}
