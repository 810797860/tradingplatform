package com.secondhand.tradingplatformgeccocontroller.sportsSpecial;

import com.secondhand.tradingplatformgeccocontroller.TradingplatformGeccoControllerApplicationTests;
import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.pipeline.Pipeline;

@PipelineName("sportsSpecialDetailPipeline")
public class DangDetailPipeline implements Pipeline<DangDetail>{

    @Override
    public void process(DangDetail dangDetail) {
        TradingplatformGeccoControllerApplicationTests.insertSportsSpecial(dangDetail);
    }
}
