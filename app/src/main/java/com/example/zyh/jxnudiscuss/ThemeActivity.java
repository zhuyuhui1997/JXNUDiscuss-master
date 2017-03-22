package com.example.zyh.jxnudiscuss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zyh on 17-3-9.
 */

public class ThemeActivity extends Activity {
    private ListView  themeActivity_commentList_listview;
    private List<Comment>  commentList;
    private EditText themeActivity_comment_content_edit;
    private Button themeActivity_send_comment_btn;
    private  MyCommentAdapter myCommentAdapter;
    private  CommonUser commonUser;
    private   static Theme  theme;
    private  TextView themeActivity_theme_title_tv;
    private  TextView  themeActivity_theme_content_tv;
    private   TextView  themeActivity_theme_user_tv;
    private   TextView   themeActivity_theme_time_tv;
    public    static  Comment comment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themeactivity);
        commonUser= BmobUser.getCurrentUser(CommonUser.class);
        init();
        String Theme_title=getIntent().getStringExtra("theme_title");
        String Theme_content=getIntent().getStringExtra("theme_content");
        String Theme_time=getIntent().getStringExtra("theme_time");
        String  Theme_user=getIntent().getStringExtra("theme_user");
        themeActivity_theme_time_tv=(TextView)findViewById(R.id.themeActivity_theme_time);
        themeActivity_theme_user_tv=(TextView)findViewById(R.id.themeActivity_theme_user);
        themeActivity_theme_title_tv=(TextView)findViewById(R.id.themeActivity_theme_title_tv);
        themeActivity_theme_content_tv=(TextView)findViewById(R.id.themeActivity_theme_content_tv);
        themeActivity_theme_title_tv.setText(Theme_title);
        themeActivity_theme_content_tv.setText(Theme_content);
        themeActivity_theme_user_tv.setText(Theme_user);
        themeActivity_theme_time_tv.setText(Theme_time);
        ActivityCollector.addActivity(this);
        themeActivity_send_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public  void init()
    {
        themeActivity_commentList_listview=(ListView)findViewById(R.id.thmemActivity_comment_listview);
        commentList=new ArrayList<Comment>();
        getCommentsFromInternet();
        myCommentAdapter=new MyCommentAdapter(this,commentList);
        themeActivity_commentList_listview.setAdapter(myCommentAdapter);
        themeActivity_send_comment_btn=(Button)findViewById(R.id.themeActivity_comment_send_btn);
        themeActivity_comment_content_edit=(EditText)findViewById(R.id.themeActivity_comment_edit);
    }
    public  void sendComment()
    {
        if(commonUser==null)
        {
            Toast.makeText(this,"请登陆再回复帖子",Toast.LENGTH_SHORT).show();
        }
        else {
            if(themeActivity_comment_content_edit.getText().toString().equals("")) {
                Toast.makeText(this,"空的回复",Toast.LENGTH_SHORT).show();
            }
            else{
                Comment comment=new Comment(themeActivity_comment_content_edit.getText().toString(),commonUser);
                comment.setTheme(theme);
                myCommentAdapter.addcomment(comment,0);
                themeActivity_comment_content_edit.setText("");
            }
        }
    }



    public static void actionStart(Context context,Theme theme)
    {
        ThemeActivity.theme=theme;
        Intent intent=new Intent(context,ThemeActivity.class);
        intent.putExtra("theme_title",theme.getTheme_Title());
        intent.putExtra("theme_content",theme.getTheme_Content());
        intent.putExtra("theme_time",theme.getCreatedAt());
        if (theme.getCommonUser()==null)
        {
            intent.putExtra("theme_user","系统");
        }
        else
        {
            intent.putExtra("theme_user",theme.getCommonUser().getUsername());
        }

        context.startActivity(intent);
    }

     public  void getCommentsFromInternet(){
         BmobQuery<Comment> query=new BmobQuery<>();
         query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);
         query.addWhereEqualTo("theme",theme);
         query.include("commonUser");
         query.setLimit(100);
         query.order("createdAt");
         query.findObjects(new FindListener<Comment>() {
             @Override
             public void done(List<Comment> list, BmobException e) {
                 for (Comment comment:list)
                 {

                     myCommentAdapter.addcomment(comment,1);
                 }
             }
         });

    }
}
