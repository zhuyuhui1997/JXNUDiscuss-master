package com.example.zyh.jxnudiscuss;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by zyh on 17-3-4.
 */

public class Theme  extends BmobObject{
    private  String Theme_Title;
    private  String Theme_Content;
    private  List<Comment>    commentList;
    private   CommonUser commonUser;


    public String getTheme_Title() {
        return Theme_Title;
    }

    public void setTheme_Title(String theme_Title) {
        Theme_Title = theme_Title;
    }

    public String getTheme_Content() {
        return Theme_Content;
    }

    public void setTheme_Content(String theme_Content) {
        Theme_Content = theme_Content;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public CommonUser getCommonUser() {
        return commonUser;
    }

    public void setCommonUser(CommonUser commonUser) {
        this.commonUser = commonUser;
    }
}
