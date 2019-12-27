package com.example.hp.billmanger;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.billmanger.Fragment.Fragment1;
import com.example.hp.billmanger.Fragment.Fragment2;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Calendar;
import java.util.List;

import com.example.hp.billmanger.Activity.MainActivity;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class In_output extends BaseActivity implements CallBackvalue ,View.OnClickListener{

    private Fragment fragment1,fragment2;
    private BottomBar bottomBar;
    private TextView textView;     //编辑类型
    private  EditText edit_my; //自定义类型
    private ImageView imageView;
    private EditText writeMessage;
    private Button write;
    private Person person;
   private Fragment currentfragment;
   private Button day;

   private  static  int id=0;

private  Input input;
private Output output;
    private  String YM;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type);
        person= BmobUser.getCurrentUser(Person.class);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar2);
        textView=(TextView) findViewById(R.id.input_Text);
        imageView=(ImageView)findViewById(R.id.input_Image) ;
        write=(Button)findViewById(R.id.write);
        edit_my=(EditText)findViewById(R.id.edit_My);
        writeMessage=(EditText)findViewById(R.id.input_Edit);
        day=(Button)findViewById(R.id.riqi_day);
        day.setOnClickListener(this);
        Calendar cal=Calendar.getInstance();
        day.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
         YM=day.getText().toString();//统计月份

        write.setOnClickListener(this);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId)
                {
                    case R.id.tab_in:
                        textView.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        edit_my.setVisibility(View.GONE);
                        writeMessage.setText("");
                        fragment1=new Fragment1();
                        currentfragment=fragment1;
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer2,fragment1).commit();
                        break;
                    case R.id.tab_out:
                        writeMessage.setText("");
                        textView.setVisibility(View.GONE);
                        edit_my.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        fragment2=new Fragment2();
                        currentfragment=fragment2;
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer2,fragment2).commit();
                        break;
                }

            }
        });
    }

    @Override
    public TextView gettexview() {
        return textView;
    }

    @Override
    public ImageView getimgeview() {
        return imageView;
    }

    @Override
    public EditText getMyEdit() {
        return  edit_my;
    }

    @Override
    public BottomBar getbottombar() {
        return null;
    }

    @Override
    public NavigationView getnav() {
        return null;
    }

    @Override
    public DrawerLayout getdraw() {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.write:
                final ProgressDialog progressDialog=new ProgressDialog(In_output.this);
                if (currentfragment == fragment1)
                {
                    if (writeMessage.getText().toString().equals(""))
                    {
                        Toast.makeText(In_output.this, "请输入金额", Toast.LENGTH_SHORT).show();
                    }
                    else if (Double.parseDouble(writeMessage.getText().toString())>10000000) {

                        Toast.makeText(In_output.this, "输入金额过大", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {

                            progressDialog.setTitle("等待提交");
                            progressDialog.setMessage("Loading...");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                        hand_in();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                finally {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(In_output.this, MainActivity.class);
                                    intent.putExtra("result","提交成功");
                                    startActivity(intent);
                                    In_output.this.finish();
                                }
                            }
                        }).start();

                    }
                }

               if (currentfragment == fragment2) {
                    if (writeMessage.getText().toString().equals("")) {
                        Toast.makeText(In_output.this, "请输入金额", Toast.LENGTH_SHORT).show();
                    }
                    else if (Double.parseDouble(writeMessage.getText().toString())>10000000) {

                        Toast.makeText(In_output.this, "输入金额过大", Toast.LENGTH_SHORT).show();
                    }
                        else {
                        hand_out();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                finally {
                                    progressDialog.dismiss();
                                    Intent intent1 = new Intent(In_output.this, MainActivity.class);
                                    intent1.putExtra("result","提交成功");
                                    startActivity(intent1);
                                    In_output.this.finish();
                                }
                            }
                        }).start();

                    }
                }
                break;

            case R.id.riqi_day:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(In_output.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                        Toast.makeText(In_output.this, text, Toast.LENGTH_SHORT).show();
                        day.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        YM=year+"-"+(month+1);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }

    }

    private void hand_out() {
        output=new Output();
        BmobQuery<Output> query=new BmobQuery<Output>();
        query.addQueryKeys("Num");
        query.findObjects(new FindListener<Output>() {
            @Override
            public void done(List<Output> list, BmobException e) {
                if(e==null)
                {
                    Output output1=(Output)list.get(list.size()-1);
                    output.setNum(output1.getNum()+1);
                    output.setDay(day.getText().toString());
                    output.setYM(YM);
                    if(!writeMessage.getText().equals(""))
                    {
                        output.setMuch(Double.parseDouble(writeMessage.getText().toString()));
                    }
                    if(edit_my.getVisibility()==View.VISIBLE)
                    {
                        output.setType(edit_my.getText().toString());
                    }
                    else
                        {
                            output.setType(textView.getText().toString());
                    }
                    output.setId(person.getObjectId());
                    output.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null)
                            {

                            }
                        }
                    });
                }
                else {
                    output.setNum(1);
                    output.setYM(YM);
                    output.setDay(day.getText().toString());
                    if(!writeMessage.equals(""))
                    {
                        output.setMuch(Double.parseDouble(writeMessage.getText().toString()));
                    }
                    if(edit_my.getVisibility()==View.VISIBLE)
                    {
                        output.setType(edit_my.getText().toString());
                    }
                    else
                    {
                        output.setType(textView.getText().toString());
                    }
                    output.setId(person.getObjectId());
                    output.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null)
                            {

                            }
                        }
                    });
                }
            }
        });


    }

    private void hand_in() {
         input=new Input();
        BmobQuery<Input> query=new BmobQuery<Input>();
        query.addQueryKeys("Num");
        query.findObjects(new FindListener<Input>() {
            @Override
            public void done(List<Input> list, BmobException e) {
                if(e==null) {
                    Input input1 = (Input) list.get(list.size() - 1);
                    input.setNum(input1.getNum() + 1);
                    input.setYM(YM);
                    input.setDay(day.getText().toString());
                    if(!writeMessage.getText().equals(""))
                    {
                        input.setMuch(Double.parseDouble(writeMessage.getText().toString()));
                    }
                    if(edit_my.getVisibility()==View.VISIBLE)
                    {
                        input.setType(edit_my.getText().toString());
                    }
                    else
                    {
                        input.setType(textView.getText().toString());
                    }
                    input.setId(person.getObjectId());
                    input.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null)
                            {

                            }
                        }
                    });

                }
                else {
                    input.setNum(1);
                    input.setYM(YM);
                    input.setDay(day.getText().toString());
                    if(!writeMessage.getText().equals(""))
                    {
                        input.setMuch(Double.parseDouble(writeMessage.getText().toString()));
                    }
                    if(edit_my.getVisibility()==View.VISIBLE)
                    {
                        input.setType(edit_my.getText().toString());
                    }
                    else
                    {
                        input.setType(textView.getText().toString());
                    }
                    input.setId(person.getObjectId());
                    input.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null)
                            {

                            }
                        }
                    });
                }
            }
        });


    }
}
