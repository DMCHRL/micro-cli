package com.micro.saas.job.xxljobexecutor.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SampleXxlJob {


    /**
     * 样例
     * @throws Exception
     */
    @XxlJob("sampleXxlJobDemo")
    public void sampleXxlJobDemo() throws Exception {

    }



}
