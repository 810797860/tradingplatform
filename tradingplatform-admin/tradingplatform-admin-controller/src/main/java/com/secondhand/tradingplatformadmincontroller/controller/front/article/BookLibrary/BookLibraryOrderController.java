package com.secondhand.tradingplatformadmincontroller.controller.front.article.BookLibrary;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.front.article.BookLibrary.BookLibraryOrder;
import com.secondhand.tradingplatformadminservice.service.front.article.BookLibrary.BookLibraryOrderService;

import java.util.List;
import java.util.Map;

/**
 * @description : BookLibraryOrder 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-27
 */
@Controller("frontBookLibraryOrderController")
@Api(value="/front/bookLibraryOrder", description="BookLibraryOrder 控制器")
@RequestMapping("/front/bookLibraryOrder")
public class BookLibraryOrderController extends BaseController {

    @Autowired
    private BookLibraryOrderService bookLibraryOrderService;

    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getBookLibraryOrderList(@ApiParam(name = "bookLibraryOrder", value = "BookLibraryOrder 实体类") @RequestBody BookLibraryOrder bookLibraryOrder) {
            TableJson<Map<String, Object>> resJson = new TableJson<>();
            Page resPage = bookLibraryOrder.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null || size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Map<String, Object>> bookLibraryOrderPage = new Page(current, size);
            bookLibraryOrderPage = bookLibraryOrderService.mySelectPageWithParam(bookLibraryOrderPage, bookLibraryOrder);
            resJson.setRecordsTotal(bookLibraryOrderPage.getTotal());
            resJson.setData(bookLibraryOrderPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取bookLibraryOrderMap
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @GetMapping(value = "/get_map_by_id/{bookLibraryOrderId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{bookLibraryOrderId}", notes = "根据id获取bookLibraryOrderMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getBookLibraryOrderByIdForMap( @ApiParam(name = "id", value = "bookLibraryOrderId") @PathVariable("bookLibraryOrderId") Long bookLibraryOrderId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> bookLibraryOrder = bookLibraryOrderService.mySelectMapById(bookLibraryOrderId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(bookLibraryOrder);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除bookLibraryOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除bookLibraryOrder")
    @ResponseBody
    public JsonResult<BookLibraryOrder> fakeDeleteById(@ApiParam(name = "id", value = "bookLibraryOrderId") @RequestBody Long bookLibraryOrderId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryOrder/delete");
                bookLibraryOrderService.myFakeDeleteById(bookLibraryOrderId);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除bookLibraryOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除bookLibraryOrder")
    @ResponseBody
    public JsonResult<BookLibraryOrder> fakeBatchDelete(@ApiParam(name = "ids", value = "bookLibraryOrderIds") @RequestBody List<Long> bookLibraryOrderIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryOrder/batch_delete");
                resJson.setSuccess(bookLibraryOrderService.myFakeBatchDelete(bookLibraryOrderIds));
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_CUSTOMIZE_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改bookLibraryOrder
     * @author : zhangjk
     * @since : Create in 2019-03-27
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改bookLibraryOrder")
    @ResponseBody
    public JsonResult<BookLibraryOrder> bookLibraryOrderCreateUpdate(@ApiParam(name = "bookLibraryOrder", value = "BookLibraryOrder实体类") @RequestBody BookLibraryOrder bookLibraryOrder){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<BookLibraryOrder> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/front/bookLibraryOrder/create_update");
                bookLibraryOrder = bookLibraryOrderService.myBookLibraryOrderCreateUpdate(bookLibraryOrder);
                resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
                resJson.setData(bookLibraryOrder);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }
}
