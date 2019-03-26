package com.secondhand.tradingplatformgeccocontroller.electricAppliance;

import com.secondhand.tradingplatformgeccocontroller.TradingplatformGeccoControllerApplicationTests;
import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.pipeline.Pipeline;

@PipelineName("electricApplianceDetailPipeline")
public class DangDetailPipeline implements Pipeline<DangDetail>{

    @Override
    public void process(DangDetail dangDetail) {
        TradingplatformGeccoControllerApplicationTests.insertElectricAppliance(dangDetail);
    }
}
