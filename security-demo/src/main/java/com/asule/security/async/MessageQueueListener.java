package com.asule.security.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private MessageQueue messageQueue;


    @Autowired
    private AsyncDeferredResultHandler resultHandler;

    //读取消息，返回结果到消息队列
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (StringUtils.isNotBlank(messageQueue.getMessage())){
                        logger.info("处理消息队列消息......");
                        resultHandler.getResults().get(messageQueue.getMessage()).setResult("return result");
                        logger.info("发送结果......");
                        messageQueue.setMessage(null);
                    }else{
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }
}
