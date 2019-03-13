package com.secondhand.tradingplatformadmincontroller.controller.admin.business;

import com.google.code.kaptcha.Producer;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 生成验证码
 * @author 81079
 */
@Controller
@Api(value = "/admin/kaptcha", description = "kaptcha 控制器")
@RequestMapping("/admin/kaptcha")
public class KaptchaController {

    /**
     * 验证码工具
     */
    @Autowired
    private Producer captchaProducer;

    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/default")
    @ApiOperation(value = "/default", notes = "获取验证码")
    public void getDefaultKaptcha(@ApiParam(name = "request", value = "服务器请求") HttpServletRequest request,
                                  @ApiParam(name = "response", value = "服务器响应") HttpServletResponse response) throws IOException {

        //设置响应头
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //验证码的字符串，要放到session
        HttpSession session = request.getSession();
        String createText = captchaProducer.createText();
        session.setAttribute(MagicalValue.STRING_OF_KAPTCHA, createText);
        //向客户端写出
        BufferedImage bufferedImage = captchaProducer.createImage(createText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
        try {
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
