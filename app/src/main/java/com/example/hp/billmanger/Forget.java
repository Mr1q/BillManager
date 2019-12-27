package com.example.hp.billmanger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class Forget extends BaseActivity {

    EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_layout);
     editText=(EditText)findViewById(R.id.forget_mail);
        Button button=(Button)findViewById(R.id.F);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emai=editText.getText().toString().trim();
                BmobUser.resetPasswordByEmail(emai, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null)
                        {
                            Toast.makeText(Forget.this,"重置密码请求成功，请登录邮箱重置",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(Forget.this,"输入邮箱有误",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
