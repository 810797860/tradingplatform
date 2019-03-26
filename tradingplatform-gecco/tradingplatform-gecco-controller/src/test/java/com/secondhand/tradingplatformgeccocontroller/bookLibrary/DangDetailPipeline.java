package com.secondhand.tradingplatformgeccocontroller.bookLibrary;

import com.secondhand.tradingplatformgeccocontroller.TradingplatformGeccoControllerApplicationTests;
import com.secondhand.tradingplatformgeccocontroller.annotation.PipelineName;
import com.secondhand.tradingplatformgeccocontroller.pipeline.Pipeline;

@PipelineName("bookLibraryDetailPipeline")
public class DangDetailPipeline implements Pipeline<DangDetail>{

    @Override
    public void process(DangDetail dangDetail) {
        TradingplatformGeccoControllerApplicationTests.insertBookLibrary(dangDetail);
    }
}
