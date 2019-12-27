package com.example.hp.billmanger;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import com.example.hp.billmanger.Activity.MainActivity;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class Update extends BaseActivity
{
    private EditText textView;
    private EditText editText;
    private Button button;
    private  List<Input> list1;
    private ImageView return_up;
    private  Button riqi;
    private Input input1;
    private  Output output;
    private String name;
    private double much;
    private  String id;
    private int num;
    private  String day;
    private String YM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);
        textView = (EditText) findViewById(R.id.update);
        editText = (EditText) findViewById(R.id.update_text);
        button = (Button) findViewById(R.id.ud);
        return_up = (ImageView) findViewById(R.id.return_up);
        riqi=(Button)findViewById(R.id.riqi);

        Intent intent = getIntent();
        name = intent.getStringExtra("type");
        much = intent.getDoubleExtra("much", 0);
        day=intent.getStringExtra("Day");
        id = intent.getStringExtra("id");
        num = intent.getIntExtra("num", 0);
        String s = intent.getStringExtra("S");

        return_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update.this.finish();
            }
        });
        riqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(Update.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                        Toast.makeText(Update.this, text, Toast.LENGTH_SHORT).show();
                        riqi.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        YM=year+"-"+(month+1);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        textView.setText(name);
        editText.setText(String.valueOf(much));
        riqi.setText(day.toString());

        if (s.equals("1")) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog progressDialog = new ProgressDialog(Update.this);
                    progressDialog.setTitle("等待提交");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    output = new Output();
                    output.setNum(num);
                    output.setType(textView.getText().toString());
                    output.setDay(riqi.getText().toString());
                    output.setYM(YM);
                    output.setMuch(Double.parseDouble(editText.getText().toString()));
                    output.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                                        if(e==null) {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Thread.sleep(200);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    } finally {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                progressDialog.dismiss();
                                                                Intent intent = new Intent(Update.this, MainActivity.class);
                                                                intent.putExtra("result1", "更新成功");
                                                                startActivity(intent);
                                                                Update.this.finish();
                                                            }
                                                        });


                                                    }

                                                }
                                            }).start();
                                        }
                        }
                    });
                }
            });
        }
        else
        {
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final ProgressDialog progressDialog = new ProgressDialog(Update.this);
                    progressDialog.setTitle("等待提交");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    input1 = new Input();
                    input1.setNum(num);
                    input1.setType(textView.getText().toString());
                    input1.setDay(riqi.getText().toString());
                    input1.setYM(YM);
                    input1.setMuch(Double.parseDouble(editText.getText().toString()));
                    input1.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(200);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialog.dismiss();
                                                    Intent intent = new Intent(Update.this, MainActivity.class);
                                                    intent.putExtra("result1", "更新成功");
                                                    startActivity(intent);
                                                    Update.this.finish();
                                                }
                                            });

                                        }

                                    }
                                }).start();
                            }








                        }
                    });
                }
            });
        }


}
}
