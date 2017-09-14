package com.challenge.bizbuzzz.Pojo;

/**
 * Created by Guidezie on 13-09-2017.
 */

public class Upload {

    public String name;
    public String url;


    public Upload()
    {

    }
    public Upload(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

