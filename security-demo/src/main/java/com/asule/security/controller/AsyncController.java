package com.asule.security.controller;


import com.asule.security.async.AsyncDeferredResultHandler;
import com.asule.security.async.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RequestMapping("/async")
@Controller
public class AsyncController{


    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private MessageQueue messageQueue;


    @Autowired
    private AsyncDeferredResultHandler resultHandler;


    @ResponseBody
    @GetMapping
    public Callable<String> async(){
        logger.info("主线程 start");
        Callable callable=new Callable<String>() {
            @Override
            public String call() throws Exception {

                logger.info("子线程 start");
                Thread.sleep(1000);

                logger.info("子线程 end");
                return "xxxxxx";
            }
        };
        logger.info("主线程 end");
        return callable;
    }

    @GetMapping("/deferred")
    public DeferredResult asyncByDeferred(){
        logger.info("主线程 start");

        DeferredResult<Object> deferredResult = new DeferredResult<>();

        messageQueue.setMessage("asule");
        resultHandler.getResults().put("asule",deferredResult);

        logger.info("主线程 end");
        return deferredResult;

    }


}
