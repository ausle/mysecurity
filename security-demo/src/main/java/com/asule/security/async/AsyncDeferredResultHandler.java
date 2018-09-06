package com.asule.security.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Component
public class AsyncDeferredResultHandler {


    private Map<String,DeferredResult> results=new HashMap<>();


    public Map<String, DeferredResult> getResults() {
        return results;
    }

    public void setResults(Map<String, DeferredResult> results) {
        this.results = results;
    }
}
