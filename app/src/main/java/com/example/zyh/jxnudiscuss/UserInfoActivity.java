package com.example.zyh.jxnudiscuss;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zyh on 17-3-11.
 */

public class UserInfoActivity extends Activity implements View.OnClickListener{
    private TextView useractivity_username_tv;
    private  EditText useractivity_userage_edit;
    private  EditText useractivity_usersex_edit;
    private  Button  useractivity_update_all_btn;
    private  CommonUser commonUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfoactivity);
        commonUser= BmobUser.getCurrentUser(CommonUser.class);
        if(commonUser!=null)
        {
            useractivity_userage_edit=(EditText) findViewById(R.id.userinfoActivity_age_edit);
            useractivity_username_tv=(TextView)findViewById(R.id.userinfoActivity_name_tv);
            useractivity_usersex_edit=(EditText)findViewById(R.id.userinfoActivity_sex_edit);
            useractivity_update_all_btn=(Button)findViewById(R.id.userinfoActivity_update_all_btn);
            useractivity_username_tv.setText(commonUser.getUsername());
            useractivity_usersex_edit.setText(commonUser.getSex());
            useractivity_userage_edit.setText(commonUser.getAge());
            useractivity_update_all_btn.setOnClickListener(this);

        }
        else
        {
            Toast.makeText(this,"请登陆",Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.userinfoActivity_update_all_btn);
        {
            commonUser.setAge(useractivity_userage_edit.getText().toString());
            commonUser.setSex(useractivity_usersex_edit.getText().toString());
            commonUser.update(commonUser.getObjectId(), new UpdateListener() {
                 @Override
                 public void done(BmobException e) {
                     if(e==null){
                         Log.i("bmob","更新成功");
                         Toast.makeText(UserInfoActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                         Intent intent=new Intent(UserInfoActivity.this,MainActivity.class);
                         startActivity(intent);
                     }else{
                         Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                         Toast.makeText(UserInfoActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
        }
    }
}
