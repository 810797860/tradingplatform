package com.secondhand.tradingplatformgeccocontroller.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformgeccoentity.entity.Test;
import com.secondhand.tradingplatformgeccoservice.service.TestService;

import java.util.List;
import java.util.Map;

/**
 * @description : Test 控制器
 * @author : zhangjk
 * @since : Create in 2019-03-21
 */
@Controller("frontTestController")
@Api(value="/front/test", description="Test 控制器")
@RequestMapping("/front/test")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;


    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2019-03-21
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    @ResponseBody
    public TableJson<Map<String, Object>> getTestList(@ApiParam(name = "test", value = "Test 实体类") @RequestBody Test test) {
        TableJson<Map<String, Object>> resJson = new TableJson<>();
        Page resPage = test.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null || size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<Map<String, Object>> testPage = new Page(current, size);
        testPage = testService.mySelectPageWithParam(testPage, test);
        resJson.setRecordsTotal(testPage.getTotal());
        resJson.setData(testPage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取testMap
     * @author : zhangjk
     * @since : Create in 2019-03-21
     */
    @GetMapping(value = "/get_map_by_id/{testId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{testId}", notes = "根据id获取testMap")
    @ResponseBody
    public JsonResult<Map<String, Object>> getTestByIdForMap( @ApiParam(name = "id", value = "testId") @PathVariable("testId") Long testId){
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> test = testService.mySelectMapById(testId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(test);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除test
     * @author : zhangjk
     * @since : Create in 2019-03-21
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除test")
    @ResponseBody
    public JsonResult<Test> fakeDeleteById(@ApiParam(name = "id", value = "testId") @RequestBody Long testId){
        JsonResult<Test> resJson = new JsonResult<>();
        testService.myFakeDeleteById(testId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据ids批量假删除test
     * @author : zhangjk
     * @since : Create in 2019-03-21
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除test")
    @ResponseBody
    public JsonResult<Test> fakeBatchDelete(@ApiParam(name = "ids", value = "testIds") @RequestBody List<Long> testIds){

        JsonResult<Test> resJson = new JsonResult<>();
        resJson.setSuccess(testService.myFakeBatchDelete(testIds));
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);

        return resJson;
    }

    /**
     * @description : 新增或修改test
     * @author : zhangjk
     * @since : Create in 2019-03-21
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改test")
    @ResponseBody
    public JsonResult<Test> testCreateUpdate(@ApiParam(name = "test", value = "Test实体类") @RequestBody Test test){
        JsonResult<Test> resJson = new JsonResult<>();
        test = testService.myTestCreateUpdate(test);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(test);
        resJson.setSuccess(true);

        return resJson;
    }
}
