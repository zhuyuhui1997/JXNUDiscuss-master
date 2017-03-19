package com.example.zyh.jxnudiscuss;

import android.util.Log;

import cn.bmob.v3.BmobObject;

/**
 * Created by zyh on 17-3-5.
 */

public class Comment extends BmobObject{
    private  String comment_content;
    private  String user_name;
    private  Theme theme;
    private   CommonUser commonUser;



    public  Comment(String comment_content,String user_name)
    {
        this.comment_content=comment_content;
        this.user_name=user_name;
    }
    public String getContent() {
        return comment_content;
    }

    public void setContent(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public CommonUser getCommonUser() {
        return commonUser;
    }

    public void setCommonUser(CommonUser commonUser) {
        this.commonUser = commonUser;
    }
}
