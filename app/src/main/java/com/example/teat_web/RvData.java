package com.example.teat_web;

import android.icu.text.StringSearch;

public class RvData {
    private String title;
    private String shareUser;
    private String link;

    public RvData(String title,String shareUser,String link) {
        this.title = title;
        this.shareUser = shareUser;
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
