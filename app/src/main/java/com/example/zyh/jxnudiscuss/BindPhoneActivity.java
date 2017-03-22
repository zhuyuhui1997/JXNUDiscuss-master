package com.example.zyh.jxnudiscuss;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by zyh on 2017/3/22.
 */

public class BindPhoneActivity extends Activity implements View.OnClickListener{
    private   EditText   number_edit;
    private   EditText   identifying_edit;
    private   Button     send_number_btn;
    private   Button     bind;
    public  String  number;
    public  String  identifying;
    public  CommonUser commonUser;
    private TextView  the_number_bind;
    private LinearLayout the_number_bind_layout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bindphoneactivity);
        commonUser=BmobUser.getCurrentUser(CommonUser.class);
        number_edit=(EditText)findViewById(R.id.number_edit);
        identifying_edit=(EditText)findViewById(R.id.identifying);
        send_number_btn=(Button)findViewById(R.id.sendphonenumber);
        the_number_bind_layout=(LinearLayout)findViewById(R.id.the_number_bind_layout);
        the_number_bind=(TextView)findViewById(R.id.the_number_bind);
        bind=(Button)findViewById(R.id.bind);
        send_number_btn.setOnClickListener(this);
        bind.setOnClickListener(this);
        if (commonUser.getMobilePhoneNumber()!=null)
        {
            the_number_bind_layout.setVisibility(View.VISIBLE);
            the_number_bind.setText(commonUser.getMobilePhoneNumber());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.sendphonenumber:
               number=number_edit.getText().toString();
                BmobSMS.requestSMSCode(number, "我的模板",new QueryListener<Integer>() {

                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        if(ex==null){//验证码发送成功
                            Toast.makeText(BindPhoneActivity.this,"发送短信成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                });break;
            case R.id.bind:
                identifying=identifying_edit.getText().toString();
                BmobSMS.verifySmsCode(number, identifying, new UpdateListener() {

                    @Override
                    public void done(BmobException ex) {
                        if(ex==null){//短信验证码已验证成功
                            bind(number);
                            Toast.makeText(BindPhoneActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(BindPhoneActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    public  void bind(String  number)
    {

        commonUser.setMobilePhoneNumber(number);
        commonUser.setMobilePhoneNumberVerified(true);
        commonUser.update(commonUser.getObjectId(),new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(BindPhoneActivity.this,"绑定手机成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BindPhoneActivity.this,"绑定手机失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
