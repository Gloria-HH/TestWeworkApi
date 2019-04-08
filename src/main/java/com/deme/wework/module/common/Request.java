package com.deme.wework.module.common;

import java.util.HashMap;

public class Request {
    public String url;
    public String method;
    public HashMap<String, String> headers;
    public HashMap<String, String> query = new HashMap<String, String>();
    public String body;

    public Request() {

    }

    public Request(String url, String method) {
        this.url = url;
        this.method = method;
    }


}
