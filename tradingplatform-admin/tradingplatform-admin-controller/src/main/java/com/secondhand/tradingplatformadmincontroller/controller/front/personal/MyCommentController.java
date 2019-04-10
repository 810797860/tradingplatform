package com.secondhand.tradingplatformadmincontroller.controller.front.personal;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.personal.MyComment;
import com.secondhand.tradingplatformadminservice.service.front.personal.MyCommentService;
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
 * @description : MyComment 控制器
 * @since : Create in 2019-04-09
 */
@Controller("frontMyCommentController")
@Api(value = "/front/myComment", description = "MyComment 控制器")
@RequestMapping("/front/myComment")
public class MyCommentController extends BaseController{

    @Autowired
    private MyCommentService myCommentService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getMyCommentList(@ApiParam(name = "myComment", value = "MyComment 实体类") @RequestBody MyComment myComment) {
        Session session = SecurityUtils.getSubject().getSession();
        Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
        //查询条件，美化代码
        myComment.setUserId(userId);
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = myComment.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Long myCommentTotal = myCommentService.mySelectTotalWithParam(myComment);
        List<Map<String, Object>> myCommentList = myCommentService.mySelectListWithParam(myComment, current, size);
        resJson.setRecordsTotal(myCommentTotal);
        resJson.setData(myCommentList);
        resJson.setSuccess(true);
        return resJson;
    }


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @PostMapping(value = "/query_mine", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query_mine", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getMyCommentSaleList(@ApiParam(name = "myComment", value = "MyComment 实体类") @RequestBody MyComment myComment) {
        Session session = SecurityUtils.getSubject().getSession();
        Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
        //查询条件，美化代码
        myComment.setUserId(userId);
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = myComment.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Long myCommentTotal = myCommentService.mySelectSaleTotalWithParam(myComment);
        List<Map<String, Object>> myCommentList = myCommentService.mySelectSaleListWithParam(myComment, current, size);
        resJson.setRecordsTotal(myCommentTotal);
        resJson.setData(myCommentList);
        resJson.setSuccess(true);
        return resJson;
    }
}
