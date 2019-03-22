package com.secondhand.tradingplatformgeccocontroller.images;

import com.secondhand.tradingplatformgeccocontroller.GeccoEngine;
import com.secondhand.tradingplatformgeccocontroller.annotation.Gecco;
import com.secondhand.tradingplatformgeccocontroller.annotation.HtmlField;
import com.secondhand.tradingplatformgeccocontroller.annotation.Image;
import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.pipeline.Pipeline;
import com.secondhand.tradingplatformgeccocontroller.spider.HtmlBean;

import java.util.List;

@PipelineName("imageListDemo")
@Gecco(matchUrl = "http://canlian.jiading.gov.cn/gyzc/zcxmdt/content_430614", pipelines = "imageListDemo")
public class ImageListDemo implements HtmlBean, Pipeline<ImageListDemo> {

	private static final long serialVersionUID = -5583524962096502124L;
	
	@Image
	@HtmlField(cssPath = ".conTxt p img")
	public List<String> pics;

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	@Override
	public void process(ImageListDemo test) {
		System.out.println(test.getPics());
	}

	public static void main(String[] args) {
        GeccoEngine.create()
                .classpath("com.secondhand.tradingplatformgeccocontroller.demo.images")
                .start("http://canlian.jiading.gov.cn/gyzc/zcxmdt/content_430614")
                .interval(1000)
                .run();
	}
}
