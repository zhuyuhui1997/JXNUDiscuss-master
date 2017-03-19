package com.example.zyh.jxnudiscuss;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyh on 17-3-9.
 */

public class ActivityCollector {
    public  static  List<Activity>  activityList=new ArrayList<Activity>();
    public  static  void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    public  static  void removeActivity(Activity activity)
    {
        activityList.remove(activity);
    }
    public  static  void finishAll()
    {
        for(Activity activity:activityList)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }

}
