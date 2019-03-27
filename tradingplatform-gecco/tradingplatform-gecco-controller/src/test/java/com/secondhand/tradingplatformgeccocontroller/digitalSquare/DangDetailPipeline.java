package com.secondhand.tradingplatformgeccocontroller.digitalSquare;

import com.secondhand.tradingplatformgeccocontroller.TradingplatformGeccoControllerApplicationTests;
import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.pipeline.Pipeline;

@PipelineName("digitalSquareDetailPipeline")
public class DangDetailPipeline implements Pipeline<DangDetail>{

    @Override
    public void process(DangDetail dangDetail) {
        TradingplatformGeccoControllerApplicationTests.insertDigitalSquare(dangDetail);
    }
}
