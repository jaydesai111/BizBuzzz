package com.challenge.bizbuzzz.Pojo;

import java.io.Serializable;

/**
 * Created by Guidezie on 13-09-2017.
 */

public class Upload implements Serializable{

    public String name;
    public String url;
    public String key;


    public Upload()
    {

    }
    public Upload(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

