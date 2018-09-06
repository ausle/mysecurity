package com.asule.security.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageQueue {



    private Logger logger = LoggerFactory.getLogger(getClass());



    private String  message;
    private boolean havingMessage=false;


    public boolean isHavingMessage() {
        return havingMessage;
    }

    public void setHavingMessage(boolean havingMessage) {
        this.havingMessage = havingMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        //开启一个线程去发送消息。此时消息队列里有消息了。
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("发送消息到消息队列......");
                MessageQueue.this.message=message;
                MessageQueue.this.havingMessage=true;
            }
        }).start();
    }
}
