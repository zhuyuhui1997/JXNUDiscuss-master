package com.example.zyh.jxnudiscuss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zyh on 17-3-5.
 */

public class MyCommentAdapter extends BaseAdapter {
    private  Context context;
    private List<Comment>  commentList;
    public MyCommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }



    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_comment,null);
            viewHolder.comment_name=(TextView)convertView.findViewById(R.id.user_name);
            viewHolder.comment_content=(TextView)convertView.findViewById(R.id.comment_content);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.comment_name.setText(commentList.get(position).getCommonUser().getUsername()+":");
        viewHolder.comment_content.setText(commentList.get(position).getContent());

        return  convertView;
    }

    public  void addcomment( Comment comment)
    {
        commentList.add(comment);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null)
                {
                    Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();

                }
                else {
                    Toast.makeText(context,"网络原因，评论失败",Toast.LENGTH_SHORT).show();
                    commentList.remove(commentList.size()-1);
                    notifyDataSetChanged();
                }
            }
        });


    }

    static class ViewHolder{
        TextView comment_name;
        TextView comment_content;
    }
}
