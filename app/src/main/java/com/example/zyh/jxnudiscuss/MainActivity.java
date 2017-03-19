package com.example.zyh.jxnudiscuss;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,AdapterView.OnItemClickListener {
    public static Context context;
    private  static  int RequestCodeToNewThemeActivity=1;
    private ListView mainActivity_themelist_listview;
    private List<Theme> themeList;
    private  MyThemesAdapter myThemesAdapter;
    public static CommonUser currentUser;
    private   TextView   currentUser_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Bmob.initialize(this, "45487cb2ed18cc0e6caa4f65e6288d11");
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);
        themeList=getThemeList();
        currentUser= BmobUser.getCurrentUser(CommonUser.class);
        myThemesAdapter=new MyThemesAdapter(this,R.layout.mainactivity_singletheme,themeList);
        mainActivity_themelist_listview=(ListView)findViewById(R.id.mainActivity_themeList_listview);
        mainActivity_themelist_listview.setAdapter(myThemesAdapter);
        NavigationView navigationView1=(NavigationView)findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        currentUser_tv=(TextView)headerView.findViewById(R.id.currentuser);
        if(currentUser!=null)
        {
                currentUser_tv.setText(currentUser.getUsername());
        }
        else
        {
            Toast.makeText(this,"现在是游客身份",Toast.LENGTH_SHORT).show();
        }

        mainActivity_themelist_listview.setOnItemClickListener(this);
        ActivityCollector.addActivity(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_quit:
                ActivityCollector.finishAll();break;
            case R.id.action_add:
                if(currentUser==null)
                {
                    Toast.makeText(this,"  现在是游客,请登陆",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(this,NewThemeActivity.class);
                    startActivityForResult(intent,RequestCodeToNewThemeActivity);break;
                }

        }
        return  true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Login) {
            Intent intent =new Intent(this,LoginActivity.class);
            this.startActivity(intent);


            // Handle the camera action
        } else if (id == R.id.UserInfo) {
            Intent intent=new Intent(this,UserInfoActivity.class);
            this.startActivity(intent);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  static Context getContext(){
        return context;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

               if(resultCode==RESULT_OK)
               {
                   String title=data.getStringExtra("title");
                   String content=data.getStringExtra("content");
                   Theme theme=new Theme();
                   theme.setTheme_Title(title);
                   theme.setTheme_Content(content);
                   theme.setCommonUser(currentUser);
                   addTheme(theme);
               }

       }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Theme theme=themeList.get(position);
        ThemeActivity.actionStart(this,theme.getTheme_Title(),theme.getTheme_Content(),theme);
    }
    private  List<Theme> getThemeList() {
        themeList=new ArrayList<Theme>();
        BmobQuery<Theme> query=new BmobQuery<Theme>();
        query.setLimit(50);
        query.order("-createdAt");
        query.findObjects(new FindListener<Theme>() {
            @Override
            public void done(List<Theme> list, BmobException e) {
                if(e==null)
                {
                    if(list!=null)
                        Toast.makeText(MainActivity.this,"获取数据成功",Toast.LENGTH_SHORT).show();
                    themeList=list;
                }

                else {
                    Toast.makeText(MainActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return themeList;
    }
    public  void addTheme(Theme theme)
    {
        myThemesAdapter.addThemes(theme);
    }

    }

