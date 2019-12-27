package com.example.hp.billmanger.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.billmanger.BaseActivity;
import com.example.hp.billmanger.Person;
import com.example.hp.billmanger.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.view.WindowManager.*;

public class Register extends BaseActivity implements View.OnClickListener{

    private String Name;
    private  String mail;
    private String currentpassword;
    private EditText UserName;//用户名
    private EditText PassWord;//密码
    private EditText Mail;//邮箱
    private  Button Rbutton;     //注册按钮
    private ImageView returnview;
    private TextView enterview;
    ProgressDialog pd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.login_register);
        Bmob.initialize(Register.this, "34b84fbc62001682e9d5c7c75d455f9f");
        init();          //初始化控件

    }
    private void init()
    {
        UserName=(EditText)findViewById(R.id.username);
        Mail=(EditText)findViewById(R.id.Mail);
        PassWord=(EditText)findViewById(R.id.password);
        Rbutton=(Button)findViewById(R.id.Rbutton);
        returnview=(ImageView)findViewById(R.id.Rreturn);
        enterview=(TextView)findViewById(R.id.Enter_change);
        Rbutton.setOnClickListener(this);
       returnview.setOnClickListener(this);
       enterview.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.Rbutton:
                    register();break;//注册方法
                case R.id.Rreturn:
                    Intent intent =new Intent(Register.this, Login.class);
                    startActivity(intent);
                    Register.this.finish();
                    break;
                case R.id.Enter_change:
                    Intent intent2 =new Intent(Register.this,Login.class);
                    startActivity(intent2);
                    Register.this.finish();
                    break;
                default:break;
            }
    }

    private void register()
    {
        mail=Mail.getText().toString().trim();
        Name=UserName.getText().toString().trim();
        currentpassword=PassWord.getText().toString().trim();
        if(TextUtils.isEmpty(Name))
        {
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
           UserName.requestFocus();//使输入失去焦点
            return;
        }else if (TextUtils.isEmpty(mail))
        {
            Toast.makeText(this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
            Mail.requestFocus();//使输入失去焦点
            return;
        }else if(TextUtils.isEmpty(currentpassword))
        {
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            PassWord.requestFocus();//使输入失去焦点
            return;
        }else if(Name.length()>20)
        {
            Toast.makeText(this,"用户名不能超过20位",Toast.LENGTH_SHORT).show();
            UserName.clearComposingText();
            UserName.setText("");
            UserName.requestFocus();
            return;
        }
        if (Name.length()<5)
        {
            Toast.makeText(this,"用户名不能少于5位",Toast.LENGTH_SHORT).show();
            UserName.clearComposingText();
            UserName.setText("");
            UserName.requestFocus();
            return;
        }
        else  if(currentpassword.length()>20) {
            Toast.makeText(this, "密码不能超过20位", Toast.LENGTH_SHORT).show();
            PassWord.clearComposingText();
            PassWord.setText("");
            PassWord.requestFocus();
            return;
        }
        if(currentpassword.length()<8)
        {
            Toast.makeText(this, "密码不能少于8位", Toast.LENGTH_SHORT).show();
            PassWord.clearComposingText();
            PassWord.setText("");
            PassWord.requestFocus();
            return;
        }

         pd= ProgressDialog.show(this,"提示","正在注册中");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    pd.dismiss();
                    Person user =new Person();
                    user.setUsername(Name);
                    user.setPassword(currentpassword);
                    user.setEmail(mail);
                    user.setSex("");
                    user.setAddress("");
                    user.setBirthday("");
                 // user.setTotalin(0);
                   // user.setTotalout(0);
                    user.signUp(new SaveListener<BmobUser>()
                    {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if(e==null)
                            {
                                Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(Register.this,e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    returnLogin();
                    Register.this.finish();
                }

            }
        }).start();


    }
    private void returnLogin()
    {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }

}
