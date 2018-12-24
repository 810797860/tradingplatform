package com.secondhand.tradingplatformadmincontroller.controller.business;

import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformcommon.jsonResult.JsonResult;
import com.secondhand.tradingplatformcommon.jsonResult.TableJson;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.business.Annex;
import com.secondhand.tradingplatformadminservice.service.business.AnnexService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description : Annex 控制器
 * @author : zhangjk
 * @since : Create in 2018-12-14
 */
@RestController
@Api(value="/admin/annex", description="Annex 控制器")
@RequestMapping("/admin/annex")
public class AnnexController extends BaseController {

    @Autowired
    private AnnexService annexService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到annex的列表页面")
    public String toAnnexList(@ApiParam(name = "Model", value = "model") Model model) {
        return "annex/tabulation";
    }

    /**
     * @description : 跳转到修改annex的页面
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/{annexId}/update.html")
    @ApiOperation(value = "/{annexId}/update.html", notes = "跳转到修改页面")
    public String toUpdateAnnex(@ApiParam(name = "Model", value = "model") Model model, @PathVariable(value = "annexId") Long annexId) {
        //静态注入要回显的数据
        Map<String, Object> annex = annexService.mySelectMapById(annexId);
        model.addAttribute("annex", annex);
        return "annex/newAnnex";
    }

    /**
     * @description : 跳转到新增annex的页面
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateAnnex(@ApiParam(name = "Model", value = "model") Model model) {
        return "annex/newAnnex";
    }
    
    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes="获取分页列表")
    public TableJson<Annex> getAnnexList(@ApiParam(name = "Annex", value = "Annex 实体类") @RequestBody Annex annex) {
            TableJson<Annex> resJson = new TableJson<>();
            Page resPage = annex.getPage();
            Integer current = resPage.getCurrent();
            Integer size = resPage.getSize();
            if (current == null && size == null) {
                resJson.setSuccess(false);
                resJson.setMessage("异常信息：页数和页的大小不能为空");
                return resJson;
            }
            Page<Annex> annexPage = new Page<Annex>(current, size);
            annexPage = annexService.mySelectPageWithParam(annexPage, annex);
            resJson.setRecordsTotal(annexPage.getTotal());
            resJson.setData(annexPage.getRecords());
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 通过id获取annexMap
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/get_map_by_id/{annexId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{annexId}", notes = "根据id获取annexMap")
    public JsonResult<Map<String, Object>> getAnnexByIdForMap( @ApiParam(name = "id", value = "annexId") @PathVariable("annexId") Long annexId){
            JsonResult<Map<String, Object>> resJson = new JsonResult<>();
            Map<String, Object> annex = annexService.mySelectMapById(annexId);
            resJson.setData(annex);
            resJson.setSuccess(true);
            return resJson;
    }

    /**
     * @description : 根据id假删除annex
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除annex")
    public JsonResult<Annex> fakeDeleteById(@ApiParam(name = "id", value = "annexId") @RequestBody Long annexId){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Annex> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/annex/delete");
                annexService.myFakeDeleteById(annexId);
                resJson.setSuccess(true);
            }catch (UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 根据ids批量假删除annex
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除annex")
    public JsonResult<Annex> fakeBatchDelete(@ApiParam(name = "ids", value = "annexIds") @RequestBody List<Long> annexIds){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Annex> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/annex/batch_delete");
                resJson.setSuccess(annexService.myFakeBatchDelete(annexIds));
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 新增或修改annex
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改annex")
    public JsonResult<Annex> annexCreateUpdate(@ApiParam(name = "Annex", value = "Annex实体类") @RequestBody Annex annex){
            Subject subject = SecurityUtils.getSubject();
            JsonResult<Annex> resJson = new JsonResult<>();
            try{
                //检查是否具有权限
                subject.checkPermission("/admin/annex/create_update");
                annex = annexService.myAnnexCreateUpdate(annex);
                resJson.setData(annex);
                resJson.setSuccess(true);
            }catch(UnauthorizedException e){
                resJson.setSuccess(false);
                resJson.setMessage(e.getMessage());
            }
            return resJson;
    }

    /**
     * @description : 附件上传接口
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "/upload", notes = "附件上传接口")
    public JsonResult<Annex> annexUpload(@ApiParam(name = "resourceType", value = "附件类型") @RequestParam(value = "resourceType", required = true) String resourceType,
                                          @ApiParam(name = "description", value = "附件说明") @RequestParam(value = "description", required = false) String description,
                                          @ApiParam(name = "md5value", value = "附件md5值") @RequestParam(value = "md5value", required = false) String md5value,
                                          @ApiParam(name = "chunks", value = "附件总分片数") @RequestParam(value = "chunks", required = false) String chunks,
                                          @ApiParam(name = "chunk", value = "附件当前第几片") @RequestParam(value = "chunk", required = false) String chunk,
                                          @ApiParam(name = "name", value = "上传附件名") @RequestParam(value = "name", required = false) String name,
                                          @ApiParam(name = "file", value = "附件") @RequestParam(value = "file", required = false) MultipartFile file) {

        JsonResult<Annex> resJson = new JsonResult<>();
        if (name == null){
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：附件标题名不能为空");
            return resJson;
        }

        try {
            resJson.setData(annexService.myAnnexUpload(resourceType, description, md5value, chunks, chunk, name, file));
            resJson.setSuccess(true);
        } catch (CustomizeException e){
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据md5值判断该附件是否存在
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/getAnnexExistsByMd5/{md5value}")
    @ApiOperation(value = "/getAnnexExistsByMd5/{md5value}", notes = "根据md5值判断该附件是否存在")
    public JsonResult<Annex> getAnnexExistsByMd5(@ApiParam(name = "md5value", value = "md5值") @PathVariable(value = "md5value") String md5value){

        JsonResult<Annex> resJson = new JsonResult<>();
        Annex annex = annexService.myGetAnnexExistsByMd5(md5value);
        resJson.setData(annex);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据附件id获取图片
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/image/{annexId}")
    @ApiOperation(value = "/image/{annexId}", notes = "根据附件id获取图片")
    public void getImageByAnnexId(@ApiParam(name = "annexId", value = "附件id") @PathVariable(value = "annexId") Long annexId,
                                               @ApiParam(name = "response", value = "服务器响应") HttpServletResponse response) throws IOException, CustomizeException {

        Date date = new Date();
        //Last-Modified:页面的最后生成时间
        response.setDateHeader("Last-Modified",date.getTime());
        //Expires:过时期限值
        response.setDateHeader("Expires",date.getTime()+1*60*60*1000);
        //Cache-Control来控制页面的缓存与否,public:浏览器和缓存服务器都可以缓存页面信息；
        response.setHeader("Cache-Control", "public");
        //Pragma:设置页面是否缓存，为Pragma则缓存，no-cache则不缓存
        response.setHeader("Pragma", "Pragma");
        annexService.myGetImageByAnnexId(annexId, response);
    }

    /**
     * @description : 根据附件id下载文件
     * @author : zhangjk
     * @since : Create in 2018-12-14
     */
    @GetMapping(value = "/download/{annexId}")
    @ApiOperation(value = "/download/{annexId}", notes = "根据附件id下载文件")
    public void downloadFile (@ApiParam(name = "request", value = "服务器请求") HttpServletRequest request,
                              @ApiParam(name = "response", value = "服务器响应") HttpServletResponse response,
                              @ApiParam(name = "annexId", value = "附件id") @PathVariable(value = "annexId") Long annexId) throws CustomizeException, IOException {

        annexService.myDownloadFile(request, response, annexId);
    }
}
