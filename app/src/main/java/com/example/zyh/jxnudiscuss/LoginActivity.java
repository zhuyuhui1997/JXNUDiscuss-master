package com.example.zyh.jxnudiscuss;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zyh on 17-3-9.
 */

public class LoginActivity extends Activity  implements View.OnClickListener {
    private EditText user_edit;
    private EditText passwd_edit;
    private Button login_btn;
    private Button register_btn;
    private Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        init();
        ActivityCollector.addActivity(this);
    }

    public void init() {
        logout_btn=(Button)findViewById(R.id.logout_btn);
        user_edit = (EditText) findViewById(R.id.loginActivity_username_tv_edit);
        passwd_edit = (EditText) findViewById(R.id.loginActivity_userpasswd_edit);
        login_btn = (Button) findViewById(R.id.loginActivity_login_btn);
        register_btn = (Button) findViewById(R.id.loginActivity_register_btn);
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginActivity_register_btn:
                if (passwd_edit.getText().toString().length() > 7) {
                    CommonUser commonUser = new CommonUser();
                    commonUser.setUsername(user_edit.getText().toString());
                    commonUser.setPassword(passwd_edit.getText().toString());
                    commonUser.setSex("boy");
                    commonUser.signUp(new SaveListener<CommonUser>() {

                        @Override
                        public void done(CommonUser commonUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "密码长度应大于7，请修改后再试一次", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.loginActivity_login_btn:
                CommonUser commonuser = new CommonUser();
                commonuser.setPassword(passwd_edit.getText().toString());
                commonuser.setUsername(user_edit.getText().toString());
                commonuser.login(new SaveListener<CommonUser>() {

                    @Override
                    public void done(CommonUser commonUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent  intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "网络问题,登录失败", Toast.LENGTH_LONG).show();
                        }

                    }


                });
                break;
            case R.id.logout_btn:
                CommonUser commonUser=BmobUser.getCurrentUser(CommonUser.class);
                commonUser.logOut();

                if (commonUser==null)
                {
                    Toast.makeText(this,"退出登录成功, 现在是游客",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent  intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this,"退出登录失败,出现网络问题",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

