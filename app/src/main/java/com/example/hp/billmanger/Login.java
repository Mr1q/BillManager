                                   package com.example.hp.billmanger;

//import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class Login extends BaseActivity implements View.OnClickListener {
   private EditText Username;
   private  EditText Userpassword;
   private  Button  Lg;       //登录
   private Button Rg;         //注册按钮
   private  TextView service; //注册信息
    private Button   B_Forget;  //忘记密码
    private CheckBox checkBox;
   String currentName;
   String currentPassword;
    NetworkInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_layout);
        Bmob.initialize(this, "34b84fbc62001682e9d5c7c75d455f9f");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //检查当前用户是否存在缓存
       Person bmobUser =BmobUser.getCurrentUser(Person.class);
        if (bmobUser != null) {
            Intent intent = new Intent(Login.this, MainActivity.class);   //跳转到主界面
            startActivity(intent);
            Login.this.finish();
        }
        init();
        ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cwjManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {

            Bmob.initialize(Login.this, "34b84fbc62001682e9d5c7c75d455f9f");

        } else {
            Toast.makeText(Login.this, "网络中断，请检查网络设置", Toast.LENGTH_LONG).show();
        }

    }

    private void login()
    {
        currentName=Username.getText().toString().trim();
        currentPassword=Userpassword.getText().toString().trim();
        if(TextUtils.isEmpty(currentName))         //判断用户名是不是为空
        {
            Toast.makeText(this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(currentPassword))     //判断密码手否为空
        {
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            BmobUser user= new BmobUser();
            user.setUsername(currentName);
            user.setPassword(currentPassword);
            user.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if(e==null)
                    {
                        Intent intent=new Intent(Login.this,MainActivity.class);   //跳转到主界面
                        startActivity(intent);
                        Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Login.this.finish();
                    }
                    else
                    {
                        Toast.makeText(Login.this,"登录失败",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    //初始化
    private  void init()
    {
            checkBox=(CheckBox)findViewById(R.id.checkbox);
            Username=(EditText)findViewById(R.id.username);
            Userpassword=(EditText)findViewById(R.id.password);
            service=(TextView)findViewById(R.id.service_message);
            B_Forget=(Button)findViewById(R.id.forget_password);
            Lg=(Button)findViewById(R.id.login);
            Rg=(Button) findViewById(R.id.register);
            Lg.setOnClickListener(this);
            Rg.setOnClickListener(this);
            service.setOnClickListener(this);
            B_Forget.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login:    //登陆按钮
                if(info!=null&&info.isConnected())
                {
                    if(checkBox.isChecked())
                    {
                        login();
                    }
                    else{
                        Toast.makeText(Login.this,"请勾选细则",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(Login.this,"网络中断，请检查网络设置",Toast.LENGTH_LONG).show();
                break;
            case R.id.register:     //注册按钮
                Intent intent=new Intent(Login.this,Register.class);   //跳转到主界面
                startActivity(intent);
                finish();//销毁当前活动
                 break;
            case R.id.service_message:
                Intent intent1=new Intent(Login.this, Webview.class);
                startActivity(intent1);
                break;
            case R.id.forget_password:
                Intent intent2=new Intent(Login.this,Forget.class);
                startActivity(intent2);
                break;

        }






    }




}
