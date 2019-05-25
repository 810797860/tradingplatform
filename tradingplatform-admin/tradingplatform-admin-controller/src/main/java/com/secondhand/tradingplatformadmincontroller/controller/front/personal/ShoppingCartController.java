package com.secondhand.tradingplatformadmincontroller.controller.front.personal;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.admin.shiro.User;
import com.secondhand.tradingplatformadminentity.entity.front.personal.ShoppingCart;
import com.secondhand.tradingplatformadminservice.service.admin.shiro.UserService;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryOrderService;
import com.secondhand.tradingplatformadminservice.service.front.article.DigitalSquare.DigitalSquareOrderService;
import com.secondhand.tradingplatformadminservice.service.front.article.ElectricAppliance.ElectricApplianceOrderService;
import com.secondhand.tradingplatformadminservice.service.front.article.RentingHouse.RentingHouseOrderService;
import com.secondhand.tradingplatformadminservice.service.front.article.SportsSpecial.SportsSpecialOrderService;
import com.secondhand.tradingplatformadminservice.service.front.personal.ShoppingCartService;
import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
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
import org.springframework.transaction.annotation.Transactional;
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
public class ShoppingCartController extends BaseController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ElectricApplianceOrderService electricApplianceOrderService;

    @Autowired
    private BookLibraryOrderService bookLibraryOrderService;

    @Autowired
    private SportsSpecialOrderService sportsSpecialOrderService;

    @Autowired
    private DigitalSquareOrderService digitalSquareOrderService;

    @Autowired
    private RentingHouseOrderService rentingHouseOrderService;

    @Autowired
    private UserService userService;

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
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

    /**
     * @description : 结算购物车
     * @author : zhangjk
     * @since : Create in 2019-04-09
     */
    @PostMapping(value = "/settlement", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/settlement", notes = "结算购物车")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public JsonResult<Float> toSettlement(@ApiParam(name = "parameter", value = "结算参数") @RequestBody Map<String, List<Long>> parameter) {

        Session session = SecurityUtils.getSubject().getSession();
        //先找出这个人余款有多少
        User user = (User) session.getAttribute(MagicalValue.USER_SESSION);
        Float balance = user.getBalance();
        //分开五种商品
        if (parameter.containsKey("electricApplianceOrder")) {
            List<Long> electricApplianceOrderLists = parameter.get("electricApplianceOrder");
            //结算electricApplianceOrder
            balance = electricApplianceOrderService.mySettlementByListId(electricApplianceOrderLists, balance);
        }
        if (parameter.containsKey("bookLibraryOrder")) {
            List<Long> bookLibraryOrderLists = parameter.get("bookLibraryOrder");
            //结算bookLibraryOrder
            balance = bookLibraryOrderService.mySettlementByListId(bookLibraryOrderLists, balance);
        }
        if (parameter.containsKey("sportsSpecialOrder")) {
            List<Long> sportsSpecialOrderLists = parameter.get("sportsSpecialOrder");
            //结算sportsSpecialOrder
            balance = sportsSpecialOrderService.mySettlementByListId(sportsSpecialOrderLists, balance);
        }
        if (parameter.containsKey("digitalSquareOrder")) {
            List<Long> digitalSquareOrderLists = parameter.get("digitalSquareOrder");
            //结算
            balance = digitalSquareOrderService.mySettlementByListId(digitalSquareOrderLists, balance);
        }
        if (parameter.containsKey("rentingHouseOrder")) {
            List<Long> rentingHouseOrderLists = parameter.get("rentingHouseOrder");
            //结算
            balance = rentingHouseOrderService.mySettlementByListId(rentingHouseOrderLists, balance);
        }

        //把这个余额更新到那个用户上
        user.setBalance(balance);
        userService.myUpdateById(user);

        //结算完毕后，分别发短信
        if (parameter.containsKey("electricApplianceOrder")) {
            List<Long> electricApplianceOrderLists = parameter.get("electricApplianceOrder");
            //发短信
            electricApplianceOrderService.myNotifyByListId(electricApplianceOrderLists);
        }
        if (parameter.containsKey("bookLibraryOrder")) {
            List<Long> bookLibraryOrderLists = parameter.get("bookLibraryOrder");
            //发短信
            bookLibraryOrderService.myNotifyByListId(bookLibraryOrderLists);
        }
        if (parameter.containsKey("sportsSpecialOrder")) {
            List<Long> sportsSpecialOrderLists = parameter.get("sportsSpecialOrder");
            //发短信
            sportsSpecialOrderService.myNotifyByListId(sportsSpecialOrderLists);
        }
        if (parameter.containsKey("digitalSquareOrder")) {
            List<Long> digitalSquareOrderLists = parameter.get("digitalSquareOrder");
            //发短信
            digitalSquareOrderService.myNotifyByListId(digitalSquareOrderLists);
        }
        if (parameter.containsKey("rentingHouseOrder")) {
            List<Long> rentingHouseOrderLists = parameter.get("rentingHouseOrder");
            //发短信
            rentingHouseOrderService.myNotifyByListId(rentingHouseOrderLists);
        }

        //拼接返回结果
        JsonResult<Float> resJson = new JsonResult<>();
        resJson.setData(balance);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setSuccess(true);
        return resJson;
    }
}
