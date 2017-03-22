package com.example.zyh.jxnudiscuss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zyh on 17-3-4.
 */

public class MyThemesAdapter extends ArrayAdapter {
    private int resourceId;
    private List<Theme>  themeList;

    public MyThemesAdapter(Context context, int resource, List<Theme> themeList) {
        super(context, resource, themeList);
        resourceId = resource;
        this.themeList=themeList;
    }

    public View getView(int position, View convertview, ViewGroup parent)
    {
        Theme theme=themeList.get(position);
        View view;
        if(convertview==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        }
        else
            view=convertview;
        TextView mainActivity_themes_title_tv=(TextView)view.findViewById(R.id.mainActivity_singletheme_title);
        mainActivity_themes_title_tv.setText(theme.getTheme_Title());
        return  view;

    }

    public  void addThemes(Theme theme ,int i)
    {
        themeList.add(theme);
        if(i==0)
        {
            theme.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null)
                    {
                        Toast.makeText(MainActivity.getContext(),"保存主题成功",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.getContext(),"保存主题失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        notifyDataSetChanged();
    }

}
