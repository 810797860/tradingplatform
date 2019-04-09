package com.secondhand.tradingplatformadmincontroller.controller.front.personal;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.front.personal.ShoppingCart;
import com.secondhand.tradingplatformadminservice.service.front.personal.ShoppingCartService;
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
 * @description : ShoppingCart 控制器
 * @since : Create in 2019-04-09
 */
@Controller("frontShoppingCartController")
@Api(value = "/front/shoppingCart", description = "ShoppingCart 控制器")
@RequestMapping("/front/shoppingCart")
public class ShoppingCartController extends BaseController{

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getShoppingCartList(@ApiParam(name = "shoppingCart", value = "ShoppingCart 实体类") @RequestBody ShoppingCart shoppingCart) {
        Session session = SecurityUtils.getSubject().getSession();
        Long userId = Long.valueOf(session.getAttribute(MagicalValue.USER_SESSION_ID).toString());
        //查询条件，美化代码
        shoppingCart.setUserId(userId);
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = shoppingCart.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Long shoppingCartTotal = shoppingCartService.mySelectTotalWithParam(shoppingCart);
        List<Map<String, Object>> shoppingCartList = shoppingCartService.mySelectListWithParam(shoppingCart, current, size);
        resJson.setRecordsTotal(shoppingCartTotal);
        resJson.setData(shoppingCartList);
        resJson.setSuccess(true);
        return resJson;
    }
}
