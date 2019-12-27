package com.example.hp.billmanger.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import  android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hp.billmanger.ActivityCollector;
import com.example.hp.billmanger.BaseActivity;
import com.example.hp.billmanger.CallBackvalue;
import com.example.hp.billmanger.Fragment.Fragment3;
import com.example.hp.billmanger.Fragment.Fragment4;
import com.example.hp.billmanger.Fragment.Fragment5;
import com.example.hp.billmanger.In_output;
import com.example.hp.billmanger.Income;
import com.example.hp.billmanger.Person;
import com.example.hp.billmanger.R;
import com.example.hp.billmanger.Search;
import com.example.hp.billmanger.User_Message;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.bmob.v3.BmobUser.*;

public class MainActivity extends BaseActivity implements View.OnClickListener, CallBackvalue {

    private DrawerLayout mdrawerLayout;
    private List<Income> incomes=new ArrayList<>();
    private TextView mail,name;
    private CircleImageView imageView;
    private  NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private Fragment fragment5,fragment4;
    private RelativeLayout relativeLayout;

    private BmobUser bmobUser;
    private Person person;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "34b84fbc62001682e9d5c7c75d455f9f");

        Intent intent=getIntent();
        String result=intent.getStringExtra("result");
        String result1=intent.getStringExtra("result1");
        if(result !=null )
        {
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
        }
        if(result1!=null)
        {
            Toast.makeText(MainActivity.this,result1,Toast.LENGTH_SHORT).show();
        }


        bmobUser=getCurrentUser();
        person=BmobUser.getCurrentUser(Person.class);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdrawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
       navigationView=(NavigationView)findViewById(R.id.nav_view);
        //导航栏Toolbar
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icons8);
        }


        navigationView.setCheckedItem(R.id.Message);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                                                             @Override
                                                             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                                 switch (item.getItemId()) {
                                                                     case R.id.Message:

                                                                         break;
                                                                     case R.id.Message2:

                                                                         break;
                                                                     case R.id.bill:
                                                                         Intent intent = new Intent(MainActivity.this, User_Message.class);
                                                                         startActivity(intent);
                                                                         break;       //暂定

                                                                 }
                                                                 return  true;
                                                             }
                                                         });
        //初始化recyclerview的布局数据
        inits();

        //底部导航栏    replace ,hide两种方法
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId)
                {
                    case R.id.tab_main:
                        floatingActionButton.setVisibility(View.VISIBLE);
                        fragment4=new Fragment4();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,fragment4).commit();
                        break;
                    case R.id.qita:
                        floatingActionButton.setVisibility(View.GONE);
                        Fragment3 fragment3 = new Fragment3();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,fragment3).commit();
                        break;
                    case R.id.tab_graph:
                        floatingActionButton.setVisibility(View.GONE);
                        fragment5=new Fragment5();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,fragment5).commit();

                        break;
                }

            }
        });
        imageView.setOnClickListener(this);
    }

    private void inits()
    {
        floatingActionButton=(FloatingActionButton)findViewById(R.id.actionbar);
        View view=navigationView.getHeaderView(0);
        mail=(TextView)view.findViewById(R.id.mail);
        imageView=(CircleImageView)view.findViewById(R.id.head_image);
        relativeLayout=(RelativeLayout)view.findViewById(R.id.head_layout);
        name=(TextView)view.findViewById(R.id.User_name);
        name.setText((String)bmobUser.getObjectByKey("username"));
        mail.setText((String)bmobUser.getEmail());

//        设置初始头像
        if(person.getImage()!=null)
        {
            String uri= person.getImage().getUrl();
            Glide.with(getApplicationContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        }
        else
        {
            imageView.setImageResource(R.drawable.ic_perm_identity);
        }
        floatingActionButton.setBackgroundColor(Color.TRANSPARENT);
        floatingActionButton.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1,menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mdrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.over:
                ActivityCollector.finishAll();break;
//            case R.id.More:break;
            case R.id.Search:
                Intent intent1=new Intent(MainActivity.this, Search.class);
                startActivity(intent1);
                break;
            case R.id.deleteall:
                Toast.makeText(MainActivity.this,"长按删除",Toast.LENGTH_LONG).show();
                break;
            case R.id.back:
                BmobUser.logOut();
                Intent intent=new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                MainActivity.this.finish();
                break;
            case R.id.bill:
                         Intent intent2= new Intent(MainActivity.this, User_Message.class);
                         startActivity(intent2);
                         break;       //暂定
                default:break;

        }
    return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.head_image:
                Intent intent=new Intent(MainActivity.this,User_Message.class);
            startActivity(intent);
            break;
            case R.id.actionbar:

                Intent intent1=new Intent(MainActivity.this, In_output.class);
                startActivity(intent1);
                MainActivity.this.finish();
                break;
        }
    }


    //重写侧滑菜单，点击back键出现返回上一个Activity
    @Override
    public void onBackPressed() {
       if(mdrawerLayout.isDrawerOpen(GravityCompat.START))
       {
           mdrawerLayout.closeDrawers();
       }
       else {
           super.onBackPressed();
       }
    }

    @Override
    public TextView gettexview() {
        return null;
    }

    @Override
    public ImageView getimgeview() {
        return null;
    }

    @Override
    public EditText getMyEdit() {
        return null;
    }

    @Override
    public BottomBar getbottombar() {
        return bottomBar;
    }

    @Override
    public NavigationView getnav() {
        return navigationView;
    }

    @Override
    public DrawerLayout getdraw() {
        return mdrawerLayout;
    }
}