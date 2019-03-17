package com.secondhand.tradingplatformadmincontroller.controller.admin.business;

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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.secondhand.tradingplatformcommon.base.BaseController.BaseController;
import com.secondhand.tradingplatformadminentity.entity.admin.business.SocketMessage;
import com.secondhand.tradingplatformadminservice.service.admin.business.SocketMessageService;

/**
 * @author : zhangjk
 * @description : SocketMessage 控制器
 * @since : Create in 2018-12-25
 */
@RestController("adminSocketMessageController")
@Api(value = "/admin/socketMessage", description = "SocketMessage 控制器")
@RequestMapping("/admin/socketMessage")
public class SocketMessageController extends BaseController {

    @Autowired
    private SocketMessageService socketMessageService;

    /**
     * @description : 跳转到列表页面
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @GetMapping(value = "/tabulation.html")
    @ApiOperation(value = "/tabulation.html", notes = "跳转到socketMessage的列表页面")
    public String toSocketMessageList(@ApiParam(name = "model", value = "Model") Model model) {
        return "socketMessage/tabulation";
    }

    /**
     * @description : 跳转到修改socketMessage的页面
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @GetMapping(value = "/{socketMessageId}/update.html")
    @ApiOperation(value = "/{socketMessageId}/update.html", notes = "跳转到修改页面")
    public String toUpdateSocketMessage(@ApiParam(name = "model", value = "Model") Model model, @PathVariable(value = "socketMessageId") Long socketMessageId) {
        //静态注入要回显的数据
        Map<String, Object> socketMessage = socketMessageService.mySelectMapById(socketMessageId);
        model.addAttribute("socketMessage", socketMessage);
        return "socketMessage/newSocketMessage";
    }

    /**
     * @description : 跳转到新增socketMessage的页面
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @GetMapping(value = "/create.html")
    @ApiOperation(value = "/create.html", notes = "跳转到新增页面")
    public String toCreateSocketMessage(@ApiParam(name = "model", value = "Model") Model model) {
        return "socketMessage/newSocketMessage";
    }

    /**
     * @description : 获取分页列表
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @PostMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/query", notes = "获取分页列表")
    public TableJson<SocketMessage> getSocketMessageList(@ApiParam(name = "SocketMessage", value = "SocketMessage 实体类") @RequestBody SocketMessage socketMessage) {
        TableJson<SocketMessage> resJson = new TableJson<>();
        Page resPage = socketMessage.getPage();
        Integer current = resPage.getCurrent();
        Integer size = resPage.getSize();
        if (current == null && size == null) {
            resJson.setSuccess(false);
            resJson.setMessage("异常信息：页数和页的大小不能为空");
            return resJson;
        }
        Page<SocketMessage> socketMessagePage = new Page<SocketMessage>(current, size);
        socketMessagePage = socketMessageService.mySelectPageWithParam(socketMessagePage, socketMessage);
        resJson.setRecordsTotal(socketMessagePage.getTotal());
        resJson.setData(socketMessagePage.getRecords());
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 通过id获取socketMessageMap
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @GetMapping(value = "/get_map_by_id/{socketMessageId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/get_map_by_id/{socketMessageId}", notes = "根据id获取socketMessageMap")
    public JsonResult<Map<String, Object>> getSocketMessageByIdForMap(@ApiParam(name = "id", value = "socketMessageId") @PathVariable("socketMessageId") Long socketMessageId) {
        JsonResult<Map<String, Object>> resJson = new JsonResult<>();
        Map<String, Object> socketMessage = socketMessageService.mySelectMapById(socketMessageId);
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(socketMessage);
        resJson.setSuccess(true);
        return resJson;
    }

    /**
     * @description : 根据id假删除socketMessage
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @PutMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/delete", notes = "根据id假删除socketMessage")
    public JsonResult<SocketMessage> fakeDeleteById(@ApiParam(name = "id", value = "socketMessageId") @RequestBody Long socketMessageId) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SocketMessage> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/socketMessage/delete");
            socketMessageService.myFakeDeleteById(socketMessageId);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 根据ids批量假删除socketMessage
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @PutMapping(value = "/batch_delete", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/batch_delete", notes = "根据ids批量假删除socketMessage")
    public JsonResult<SocketMessage> fakeBatchDelete(@ApiParam(name = "ids", value = "socketMessageIds") @RequestBody List<Long> socketMessageIds) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SocketMessage> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/socketMessage/batch_delete");
            resJson.setSuccess(socketMessageService.myFakeBatchDelete(socketMessageIds));
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : 新增或修改socketMessage
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @PostMapping(value = "/create_update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "/create_update", notes = "新增或修改socketMessage")
    public JsonResult<SocketMessage> socketMessageCreateUpdate(@ApiParam(name = "SocketMessage", value = "SocketMessage实体类") @RequestBody SocketMessage socketMessage) {
        Subject subject = SecurityUtils.getSubject();
        JsonResult<SocketMessage> resJson = new JsonResult<>();
        try {
            //检查是否具有权限
            subject.checkPermission("/admin/socketMessage/create_update");
            socketMessage = socketMessageService.mySocketMessageCreateUpdate(socketMessage);
            resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
            resJson.setData(socketMessage);
            resJson.setSuccess(true);
        } catch (UnauthorizedException e) {
            resJson.setCode(MagicalValue.CODE_OF_UNAUTHORIZED_EXCEPTION);
            resJson.setSuccess(false);
            resJson.setMessage(e.getMessage());
        }
        return resJson;
    }

    /**
     * @description : webSocket发送消息
     * @author : zhangjk
     * @since : Create in 2018-12-25
     */
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public JsonResult<SocketMessage> sendWebSocketMessage(@ApiParam(name = "SocketMessage", value = "SocketMessage实体类") @RequestBody SocketMessage socketMessage) {
        // 放值到sockSession： headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        // 获取sockSession值： String username = (String) headerAccessor.getSessionAttributes().get("username");
        // 后台发送信息： messagingTemplate.convertAndSend("/topic/public", chatMessage); (chatMessage是pojo)
        // 详情去看：spring-boot-websocket-chat-demo
        JsonResult<SocketMessage> resJson = new JsonResult<>();
        resJson.setCode(MagicalValue.CODE_OF_SUCCESS);
        resJson.setData(socketMessage);
        resJson.setSuccess(true);
        return resJson;
    }
}
