package com.example.hp.billmanger.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hp.billmanger.Input;
import com.example.hp.billmanger.MyPickerDialog;
import com.example.hp.billmanger.Output;
import com.example.hp.billmanger.Person;
import com.example.hp.billmanger.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment5 extends Fragment {
View view;
private BarChart barChart;
private  Handler handler;
private List<Input> inputs;
private  BarData data;

    private double sum=0;
    private double sum1=0;
    private double sum2=0;
    private double sum3=0;
    private double sum4=0;
    private double sum5=0;
    private double sum6=0;
    private double sum7=0;
    private double sum8=0;
    private Button riqi;

    private  BarDataSet set;
    private  BarDataSet set1;
    private  BarDataSet set2;
    private  BarDataSet set3;
    private  BarDataSet set4;
    private  BarDataSet set5;
    private  BarDataSet set6;
    private  BarDataSet set7;
    private  BarDataSet set8;

    List<BarEntry> entries = new ArrayList<>();
    List<BarEntry> entries2 = new ArrayList<>();
    List<BarEntry> entries3 = new ArrayList<>();
    List<BarEntry> entries4 = new ArrayList<>();
    List<BarEntry> entries5 = new ArrayList<>();
    List<BarEntry> entries6 = new ArrayList<>();
    List<BarEntry> entries7 = new ArrayList<>();
    List<BarEntry> entries8 = new ArrayList<>();
    List<BarEntry> entries9 = new ArrayList<>();

    ArrayList<String> names=new ArrayList<String>();
    ArrayList<String> names2=new ArrayList<String>();
    Button in;
    Button out;
    private int num=1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.graph_2,container,false);
        riqi=(Button)view.findViewById(R.id.riqi);
        in=(Button)view.findViewById(R.id.in);
        out=(Button)view.findViewById(R.id.out);
        In();

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(num!=0)
                    {
                        Toast.makeText(getActivity(),"请勿重复点击",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        sum=0;
                        sum1=0;
                        sum2=0;
                        sum3=0;
                        sum4=0;
                        sum5=0;
                        sum6=0;
                        sum7=0;
                        sum8=0;
                        entries.clear();
                        entries2.clear();
                        entries3.clear();
                        entries4.clear();
                        entries5.clear();
                        entries6.clear();
                        entries7.clear();
                        entries8.clear();
                        entries9.clear();
                        set.clear();
                        set1.clear();
                        set2.clear();
                        set3.clear();
                        set4.clear();
                        set5.clear();
                        set6.clear();
                        set7.clear();
                        set8.clear();
                        In();
                        num=1;
                    }
            }
        });
        
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num==1)
                {
                    sum=0;
                    sum1=0;
                    sum2=0;
                    sum3=0;
                    sum4=0;
                    sum5=0;
                    sum6=0;
                    sum7=0;
                    sum8=0;
                    entries.clear();
                    entries2.clear();
                    entries3.clear();
                    entries4.clear();
                    entries5.clear();
                    entries6.clear();
                    set.clear();
                    set1.clear();
                    set2.clear();
                    set3.clear();
                    set4.clear();
                    set5.clear();
                    Out();
                    num=0;

                }else
                    {
                        Toast.makeText(getActivity(),"请勿重复点击",Toast.LENGTH_SHORT).show();
                }

            }
        });

            riqi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Calendar calendar = Calendar.getInstance();
//                    new MyPickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            String text = "你选择了" + year + "年" + (month + 1) + "月";  //月份从0开始计数
//                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
//                            riqi.setText(year + "-" + (month + 1));
//                        }
//                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    Gettime();
                }
            });


        riqi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(num==1)
               {
                   sum=0;
                   sum1=0;
                   sum2=0;
                   sum3=0;
                   sum4=0;
                   sum5=0;
                   sum6=0;
                   sum7=0;
                   sum8=0;
                   entries.clear();
                   entries2.clear();
                   entries3.clear();
                   entries4.clear();
                   entries5.clear();
                   entries6.clear();
                   set.clear();
                   set1.clear();
                   set2.clear();
                   set3.clear();
                   set4.clear();
                   set5.clear();
                   Person person = BmobUser.getCurrentUser(Person.class);
                   BmobQuery<Input> inputBmobQuery = new BmobQuery<Input>();
                   BmobQuery<Input> inputBmobQuery2 = new BmobQuery<Input>();
                   inputBmobQuery2.addWhereEqualTo("YM",riqi.getText().toString());
                   inputBmobQuery.addWhereEqualTo("id", person.getObjectId());
                   List<BmobQuery<Input>> input=new ArrayList<BmobQuery<Input>>();
                   input.add(inputBmobQuery);
                   input.add(inputBmobQuery2);
                   BmobQuery<Input> Total=new BmobQuery<Input>();
                   Total.and(input);
                   Total.findObjects(new FindListener<Input>() {
                       @Override
                       public void done(final List<Input> list, BmobException e) {
                           if (e == null) {
                               Toast.makeText(getActivity(),riqi.getText().toString(),Toast.LENGTH_LONG).show();
                               getActivity().runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       set();
                                       setData(barChart,6,list);
                                   }
                               });
                           }
                       }
                   });
               }
               else {
                   sum=0;
                   sum1=0;
                   sum2=0;
                   sum3=0;
                   sum4=0;
                   sum5=0;
                   sum6=0;
                   sum7=0;
                   sum8=0;
                   entries.clear();
                   entries2.clear();
                   entries3.clear();
                   entries4.clear();
                   entries5.clear();
                   entries6.clear();
                   entries7.clear();
                   entries8.clear();
                   entries9.clear();
                   set.clear();
                   set1.clear();
                   set2.clear();
                   set3.clear();
                   set4.clear();
                   set5.clear();
                   set6.clear();
                   set7.clear();
                   set8.clear();
                   Person person = BmobUser.getCurrentUser(Person.class);
                   BmobQuery<Output> inputBmobQuery = new BmobQuery<Output>();
                   BmobQuery<Output> inputBmobQuery2 = new BmobQuery<Output>();
                   inputBmobQuery2.addWhereEqualTo("YM",riqi.getText().toString());
                   inputBmobQuery.addWhereEqualTo("id", person.getObjectId());
                   List<BmobQuery<Output>> ouput=new ArrayList<BmobQuery<Output>>();
                   ouput.add(inputBmobQuery);
                   ouput.add(inputBmobQuery2);
                   BmobQuery<Output> Total=new BmobQuery<Output>();
                   Total.and(ouput);
                   Total.findObjects(new FindListener<Output>() {
                       @Override
                       public void done(final List<Output> list, BmobException e) {
                           if (e == null) {
                               Toast.makeText(getActivity(),riqi.getText().toString(),Toast.LENGTH_LONG).show();
                               getActivity().runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       set();
                                       setData_out(barChart,6,list);
                                   }
                               });
                           }
                       }
                   });
               }
            }
        });
        return view;

    }
    public void Gettime() {
        final StringBuilder stringBuilder = new StringBuilder();
        final Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                stringBuilder.append(hourOfDay + ":" + minute);
                riqi.setText(stringBuilder);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show();

        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

                stringBuilder.append(year + "-" + (month + 1) + "-" + dayOfMonth + " ");
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    private void Out() {

        if(riqi.getText().toString().equals("日期"))
        {
            Person person = BmobUser.getCurrentUser(Person.class);
            BmobQuery<Output> inputBmobQuery = new BmobQuery<Output>();
            inputBmobQuery.addWhereEqualTo("id", person.getObjectId());
            inputBmobQuery.findObjects(new FindListener<Output>() {
                @Override
                public void done(final List<Output> list, BmobException e) {
                    if (e == null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                set_out();
                                setData_out(barChart,6,list);
                            }
                        });
                    }
                }
            });
        }

    }

    private void In() {
        if(riqi.getText().toString().equals("日期"))
        {
            Person person = BmobUser.getCurrentUser(Person.class);
            BmobQuery<Input> inputBmobQuery = new BmobQuery<Input>();
            inputBmobQuery.addWhereEqualTo("id", person.getObjectId());
            inputBmobQuery.findObjects(new FindListener<Input>() {
                @Override
                public void done(final List<Input> list, BmobException e) {
                    if (e == null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                set();
                                setData(barChart,6,list);
                            }
                        });
                    }
                }
            });
        }
        

    }


    private void set() {
        barChart=(BarChart)view.findViewById(R.id.barchart);
        names.add("兼职");
        names.add("理财");
        names.add("红包");
        names.add("投资");
        names.add("奖金");
        names.add("其他");
        setBarChartStyle(barChart);

    }
    private void set_out() {
        barChart=(BarChart)view.findViewById(R.id.barchart);
        names2.add("吃喝");
        names2.add("交通");
        names2.add("话费");
        names2.add("游戏");
        names2.add("医疗");
        names2.add("宠物");
        names2.add("红包");
        names2.add("日用品");
        names2.add("其他");
        setBarChartStyle(barChart);

    }

    private void setData( BarChart barChart, int i,List<Input> list1) {

        for (Input input : list1) {
            switch (input.getType()) {
                case "兼职":
                    sum += input.getMuch();
                    break;
                case "理财":
                    sum1 += input.getMuch();
                    break;
                case "红包":
                    sum2 += input.getMuch();
                    break;
                case "投资":
                    sum3 += input.getMuch();
                    break;
                case "奖金":
                    sum4 += input.getMuch();
                    break;
                default:
                    sum5 += input.getMuch();
                    break;
            }
        }
        entries.add(new BarEntry(1f,Float.parseFloat(String.valueOf(sum))));
        entries2.add(new BarEntry(2f,Float.parseFloat(String.valueOf(sum1))));
        entries3.add(new BarEntry(3f,Float.parseFloat(String.valueOf(sum2))));
        entries4.add(new BarEntry(4f,Float.parseFloat(String.valueOf(sum3))));
        entries5.add(new BarEntry(5f,Float.parseFloat(String.valueOf(sum4))));
        entries6.add(new BarEntry(6f,Float.parseFloat(String.valueOf(sum5))));
         set = new BarDataSet(entries, "兼职");
         set1 = new BarDataSet(entries2, "理财");
         set2 = new BarDataSet(entries3, "红包");
         set3 = new BarDataSet(entries4, "投资");
         set4 = new BarDataSet(entries5, "奖金");
         set5 = new BarDataSet(entries6, "其他");
        set.setColor(Color.parseColor("#FFBB33"));
        set1.setColor(Color.parseColor("#8B1A1A"));
        set2.setColor(Color.parseColor("#EE82EE"));
        set3.setColor(Color.parseColor("#D1EEEE"));
        set4.setColor(Color.parseColor("#836FFF"));
        set5.setColor(Color.parseColor("#008B00"));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);
        data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f); //设置自定义条形宽度



        barChart.setData(data);
        barChart.setFitBars(true); //使x轴完全适合所有条形
        barChart.invalidate(); // refresh



    }
    private void setData_out( BarChart barChart, int i,List<Output> list1) {

        for (Output output : list1) {
            switch (output.getType()) {
                case "吃喝":
                    sum += output.getMuch();
                    break;
                case "交通":
                    sum1 += output.getMuch();
                    break;
                case "话费":
                    sum2 += output.getMuch();
                    break;
                case "游戏":
                    sum3 += output.getMuch();
                    break;
                case "医疗":
                    sum4 += output.getMuch();
                    break;
                case "宠物":
                    sum5 += output.getMuch();
                    break;
                case "红包":
                    sum6 += output.getMuch();
                    break;
                case "日用品":
                    sum7 += output.getMuch();
                    break;
                default:
                    sum8 += output.getMuch();
                    break;
            }
        }
        entries.add(new BarEntry(1f,Float.parseFloat(String.valueOf(sum))));
        entries2.add(new BarEntry(2f,Float.parseFloat(String.valueOf(sum1))));
        entries3.add(new BarEntry(3f,Float.parseFloat(String.valueOf(sum2))));
        entries4.add(new BarEntry(4f,Float.parseFloat(String.valueOf(sum3))));
        entries5.add(new BarEntry(5f,Float.parseFloat(String.valueOf(sum4))));
        entries6.add(new BarEntry(6f,Float.parseFloat(String.valueOf(sum5))));
        entries7.add(new BarEntry(7f,Float.parseFloat(String.valueOf(sum6))));
        entries8.add(new BarEntry(8f,Float.parseFloat(String.valueOf(sum7))));
        entries9.add(new BarEntry(6f,Float.parseFloat(String.valueOf(sum8))));
        set = new BarDataSet(entries, "吃喝");
        set1 = new BarDataSet(entries2, "交通");
        set2 = new BarDataSet(entries3, "话费");
        set3 = new BarDataSet(entries4, "游戏");
        set4 = new BarDataSet(entries5, "医疗");
        set5 = new BarDataSet(entries6, "宠物");
        set6=new BarDataSet(entries7,"红包");
        set7=new  BarDataSet(entries7,"日用品");
        set8=new BarDataSet(entries7,"其他");

        set.setColor(Color.parseColor("#FFBB33"));
        set1.setColor(Color.parseColor("#8B1A1A"));
        set2.setColor(Color.parseColor("#EE82EE"));
        set3.setColor(Color.parseColor("#D1EEEE"));
        set4.setColor(Color.parseColor("#836FFF"));
        set5.setColor(Color.parseColor("#008B00"));
        set6.setColor(Color.parseColor("#FF3030"));
        set7.setColor(Color.parseColor("#EE30A7"));
        set8.setColor(Color.parseColor("#8B8B00"));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        dataSets.add(set4);
        dataSets.add(set5);
        dataSets.add(set6);
        dataSets.add(set7);
        dataSets.add(set8);

        data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f); //设置自定义条形宽度


        barChart.setData(data);
        barChart.setFitBars(true); //使x轴完全适合所有条形
        barChart.invalidate(); // refresh
            barChart.animateXY(2000,2000);


    }

    private void  setBarChartStyle(BarChart barChart)
 {

    barChart.setDrawBarShadow(false);
    barChart.setDrawValueAboveBar(true);
    barChart.setMaxVisibleValueCount(60);
    barChart.setPinchZoom(false);
    barChart.setDrawGridBackground(false);
    barChart.animateXY(1000,1500);
    barChart.getAxisRight().setEnabled(false);
    barChart.getAxisLeft().setAxisMinValue(0.0f);//设置Y轴显示最小值，不然0下面会有空隙

    XAxis xAxis=barChart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setDrawGridLines(false);
    xAxis.setDrawLabels(true);
 //   xAxis.setSpaceMax(2);
    xAxis.setCenterAxisLabels(true);
    xAxis.setGranularity(1f);
    xAxis.setLabelCount(names.size());
    xAxis.setValueFormatter(new IndexAxisValueFormatter(names));


    YAxis rightAxis = barChart.getAxisRight();
    rightAxis.setDrawGridLines(false);
    rightAxis.setLabelCount(5, false);
    rightAxis.setSpaceTop(15f);
    rightAxis.setTextColor(Color.GREEN);


    Legend mLegend = barChart.getLegend();
    mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    mLegend.setForm(Legend.LegendForm.SQUARE);
    mLegend.setFormSize(15f);
    mLegend.setTextSize(12f);
    mLegend.setXEntrySpace(5f);

 }

}

