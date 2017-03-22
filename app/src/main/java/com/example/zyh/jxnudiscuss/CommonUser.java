package com.example.zyh.jxnudiscuss;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by zyh on 17-3-1.
 */

public class CommonUser extends BmobUser {
    private String age;
    private String sex;
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
