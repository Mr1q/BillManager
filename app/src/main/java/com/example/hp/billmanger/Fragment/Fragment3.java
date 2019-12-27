package com.example.hp.billmanger.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;


import com.example.hp.billmanger.CallBackvalue;
import com.example.hp.billmanger.Input;
import com.example.hp.billmanger.Input_data;
import com.example.hp.billmanger.MyPickerDialog;
import com.example.hp.billmanger.Output;
import com.example.hp.billmanger.Person;
import com.example.hp.billmanger.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment3 extends Fragment {
    private  PieChart pieChart;
    private List<Input_data> input_data;
    private  List<Double>  data=new ArrayList<Double>();
    private  List<Double>  data1=new ArrayList<Double>();
    private  List<Double>  data2=new ArrayList<Double>();
    private  List<Double>  data3=new ArrayList<Double>();
    private  List<Double>  data4=new ArrayList<Double>();
    private  List<Double>  data5=new ArrayList<Double>();
    private  List<Double>  data6=new ArrayList<Double>();
    private  List<Double>  data7=new ArrayList<Double>();
    private  List<Double>  data8=new ArrayList<Double>();
    public double sum=0;
    public double sum1=0;
    public double sum2=0;
    public double sum3=0;
    public double sum4=0;
    public double sum5=0;
    public double sum6=0;
    public double sum7=0;
    public double sum8=0;

    private    ArrayList<PieEntry> mEntry;
    private    ArrayList<PieEntry> mEntry1;

    private     BottomBar bottomBar;
    private Button riqi;
    private CallBackvalue mcallback;

    private boolean num=false;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.graph,container,false);
        pieChart=(PieChart)view.findViewById(R.id.chart_view);
        bottomBar=(BottomBar)view.findViewById(R.id.bottomBar);
        riqi=(Button)view.findViewById(R.id.riqi);

        riqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new MyPickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = "你选择了" + year + "年" + (month + 1) + "月";  //月份从0开始计数
                        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        riqi.setText(year + "-" + (month + 1));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                if(num==false) {
                    sum = 0;
                    sum1 = 0;
                    sum2 = 0;
                    sum3 = 0;
                    sum4 = 0;
                    sum5 = 0;
                    sum6 = 0;
                    sum7 = 0;
                    sum8 = 0;
                    input_data.clear();
                    mEntry1.clear();
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
                                        setdata(list);
                                        init();
                                    }
                                });
                            }
                        }
                    });
                    num=false;
                }
                if(num) {

                    sum  = 0;
                    sum1 = 0;
                    sum2 = 0;
                    sum3 = 0;
                    sum4 = 0;
                    sum5 = 0;
                    sum6 = 0;
                    sum7 = 0;
                    sum8 = 0;
                    input_data.clear();
                    mEntry.clear();
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
                                        setdata2(list);
                                        init();

                                    }
                                });
                            }
                        }
                    });

                    num=true;
                }








            }
        });

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId)
                {
                    case R.id.tab_in:
                        in();
                        if(num) {
                            sum  = 0;
                            sum1 = 0;
                            sum2 = 0;
                            sum3 = 0;
                            sum4 = 0;
                            sum5 = 0;
                            sum6 = 0;
                            sum7 = 0;
                            sum8 = 0;
                            input_data.clear();
                            mEntry.clear();
                            num=false;
                        }
                        break;
                    case R.id.tab_out:
                        out();
                        if(num==false) {
                            sum = 0;
                            sum1 = 0;
                            sum2 = 0;
                            sum3 = 0;
                            sum4 = 0;
                            sum5 = 0;
                            sum6 = 0;
                            sum7 = 0;
                            sum8 = 0;
                            input_data.clear();
                            mEntry1.clear();
                            num=true;
                        }
                        break;

                }
            }
        });
        return view;
    }


    private void in()
    {
        final Person person= BmobUser.getCurrentUser(Person.class);
        BmobQuery<Input> inputBmobQuery=new BmobQuery<Input>();
        inputBmobQuery.addWhereEqualTo("id",person.getObjectId());
        inputBmobQuery.findObjects(new FindListener<Input>() {
            @Override
            public void done(final List<Input> list, BmobException e) {
                if(e==null)
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setdata(list);
                            init();
                            pieChart.setCenterText("收入情况");

                        }
                    });
                }
            }
        });

    }



    private void out()
    {
        final Person person= BmobUser.getCurrentUser(Person.class);
        BmobQuery<Output> inputBmobQuery=new BmobQuery<Output>();
        inputBmobQuery.addWhereEqualTo("id",person.getObjectId());
        inputBmobQuery.findObjects(new FindListener<Output>() {
            @Override
            public void done(final List<Output> list, BmobException e) {
                if(e==null)
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setdata2(list);
                            init();
                            pieChart.setCenterText("支出情况");
                        }
                    });
                }
            }
        });

    }

    private void setdata2(List<Output> list)
    {
        for(Output output:list)
        {
            switch (output.getType())
            {
                case "吃喝":
                    data.add(output.getMuch());
                    sum += output.getMuch();
                    break;
                case "交通":
                    data1.add(output.getMuch());
                    sum1 += output.getMuch();
                    break;
                case "话费":
                    data2.add(output.getMuch());
                    sum2 += output.getMuch();
                    break;
                case "游戏":
                    sum3 += output.getMuch();
                    data3.add(output.getMuch());break;
                case"医疗":
                    sum4+= output.getMuch();
                    data4.add(output.getMuch());break;
                case"宠物":
                    sum5+= output.getMuch();
                    data5.add(output.getMuch());break;
                case"红包":
                    sum6+= output.getMuch();
                    data6.add(output.getMuch());break;
                case"日用品":
                    sum7+= output.getMuch();
                    data7.add(output.getMuch());break;
                default:
                    sum8+=output.getMuch();
                    data8.add(output.getMuch());break;
            }
        }
        input_data = new ArrayList<>();
        Input_data playing = new Input_data();;
        playing.setLabel("吃喝");
        playing.setValue(Float.valueOf(String.valueOf(sum)));
        Input_data pay = new Input_data();
        pay.setLabel("交通");
        pay.setValue(Float.valueOf(String.valueOf(sum1)));
        Input_data shopping = new Input_data();
        shopping.setLabel("话费");
        shopping.setValue(Float.valueOf(String.valueOf(sum2)));
        Input_data mobile = new Input_data();
        mobile.setLabel("游戏");
        mobile.setValue(Float.valueOf(String.valueOf(sum3)));
        Input_data moeny = new Input_data();
        moeny.setLabel("医疗");
        moeny.setValue(Float.valueOf(String.valueOf(sum4)));
        Input_data cw = new Input_data();
        cw.setLabel("宠物");
        cw.setValue(Float.valueOf(String.valueOf(sum5)));
        Input_data hongbao = new Input_data();
        hongbao.setLabel("红包");
        hongbao.setValue(Float.valueOf(String.valueOf(sum6)));
        Input_data ryp = new Input_data();
        ryp.setLabel("日用品");
        ryp.setValue(Float.valueOf(String.valueOf(sum7)));
        Input_data qita = new Input_data();
        qita.setLabel("其他");
        qita.setValue(Float.valueOf(String.valueOf(sum8)));
        if(sum>0)
            input_data.add(playing);
        if(sum1>0)
            input_data.add(pay);
        if(sum2>0)
            input_data.add(shopping);
        if(sum3>0)
            input_data.add(mobile);
        if(sum4>0)
            input_data.add(moeny);
        if(sum5>0)
            input_data.add(cw);
        if(sum6>0)
            input_data.add(hongbao);
        if(sum7>0)
            input_data.add(ryp);
        if(sum8>0)
            input_data.add(qita);
        setdata_2(input_data);

    }



    private void setdata(List<Input> list)
{
    for(Input input:list)
    {
        switch (input.getType())
        {
            case "兼职":
                data.add(input.getMuch());
                sum += input.getMuch();
                break;
            case "理财":
                data1.add(input.getMuch());
                sum1 += input.getMuch();
                break;
            case "红包":
                data2.add(input.getMuch());
                sum2 += input.getMuch();
                break;
            case "投资":
                sum3 += input.getMuch();
                data3.add(input.getMuch());break;
            case"奖金":
                sum4+= input.getMuch();
                data4.add(input.getMuch());break;
            default:
                sum5+=input.getMuch();
                data5.add(input.getMuch());break;
        }
    }
    input_data = new ArrayList<>();
    Input_data playing = new Input_data();;
    playing.setLabel("兼职");
    playing.setValue(Float.valueOf(String.valueOf(sum)));
    Input_data pay = new Input_data();
    pay.setLabel("理财");
    pay.setValue(Float.valueOf(String.valueOf(sum1)));
    Input_data shopping = new Input_data();
    shopping.setLabel("红包");
    shopping.setValue(Float.valueOf(String.valueOf(sum2)));
    Input_data mobile = new Input_data();
    mobile.setLabel("投资");
    mobile.setValue(Float.valueOf(String.valueOf(sum3)));
    Input_data moeny = new Input_data();
    moeny.setLabel("奖金");
    moeny.setValue(Float.valueOf(String.valueOf(sum4)));
    Input_data qita = new Input_data();
    qita.setLabel("其他");
    qita.setValue(Float.valueOf(String.valueOf(sum5)));
    if(sum>0)
    input_data.add(playing);
    if(sum1>0)
    input_data.add(pay);
    if(sum2>0)
    input_data.add(shopping);
    if(sum3>0)
    input_data.add(mobile);
    if(sum4>0)
    input_data.add(moeny);
    if(sum5>0)
    input_data.add(qita);
    setdata_1(input_data);

}

private  void init()
{
    pieChart.setUsePercentValues(true);
    pieChart.getDescription().setEnabled(false);
    pieChart.setExtraOffsets(7,10,7,5);

    pieChart.setDragDecelerationFrictionCoef(0.95f);


    pieChart.setCenterTextColor(Color.BLACK);
    pieChart.setCenterTextSize(20);

    pieChart.setDrawHoleEnabled(true);//是否绘制饼状图中间的圆
    pieChart.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色

    pieChart.setTransparentCircleColor(Color.BLACK);
    pieChart.setTransparentCircleAlpha(110);

    pieChart.setHoleRadius(40f);
    pieChart.setTransparentCircleRadius(40f);

    pieChart.setRotationAngle(0);
    // 触摸旋转
    pieChart.setRotationEnabled(true);
    pieChart.setHighlightPerTapEnabled(true);

}

    private void setdata_1(List<Input_data> input_data) {

        mEntry1 = new ArrayList<PieEntry>();
        for (Input_data monthData : input_data) {
            PieEntry entry = new PieEntry(monthData.getValue(), monthData.getLabel());
            mEntry1.add(entry);
        }
            PieDataSet dataSet=new PieDataSet(mEntry1,"收入类型");
            dataSet.setSliceSpace(2f);
            dataSet.setSelectionShift(4f);
            //设置颜色
            ArrayList<Integer> colors = new ArrayList<Integer>();
            for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.4f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


            PieData pieData=new PieData(dataSet);
            pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.RED);
        pieChart.setData(pieData);
        pieChart.animateX(3400, Easing.EasingOption.EaseInOutQuad);
        pieChart.highlightValues(null);


    }
    private void setdata_2(List<Input_data> input_data) {

        mEntry = new ArrayList<PieEntry>();
        for (Input_data monthData : input_data) {
            PieEntry entry = new PieEntry(monthData.getValue(), monthData.getLabel());
            mEntry.add(entry);
        }
        PieDataSet dataSet = new PieDataSet(mEntry, "收入类型");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(4f);
        //设置颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.4f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.RED);
        pieChart.setData(pieData);
        pieChart.animateX(3400, Easing.EasingOption.EaseInOutQuad);
        pieChart.highlightValues(null);
    }
}
