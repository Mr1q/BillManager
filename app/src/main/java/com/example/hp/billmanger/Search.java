package com.example.hp.billmanger;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Search extends BaseActivity {
    private SearchView searchView;
    private RecyclerView lv;
    private List<Total> totals=new ArrayList<>();
    private List<Total> totals2=new ArrayList<>();

    private  int num=0;
    private  int num1=0;


private Button riqi;
    private BottomBar bottomBar;
    // 自动完成的列表
//    private final String[] mStrings = {"红包",
//            "兼职", "理财", "投资", "奖金",
//            "其他", "吃喝", "交通", "话费",
//            "游戏", "医疗","宠物","日用品"  };
    private TotalAdapter adapter;
    private  Out_Adapter adapter_out;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        searchView=(SearchView)findViewById(R.id.search1);
        searchView.setQueryHint("输入查找类型");
        bottomBar=(BottomBar)findViewById(R.id.bottomBar1);
        riqi=(Button)findViewById(R.id.riqi);
        lv = (RecyclerView) findViewById(R.id.recycleview1);
        LinearLayoutManager linearLayout=new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayout);



        riqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=0;
                num1=0;
                lv.removeAllViews();
                totals.clear();
                totals2.clear();
                final Calendar calendar = Calendar.getInstance();

          new DatePickerDialog(Search.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        hidePicker();
                        String text = "你选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";  //月份从0开始计数
                        Toast.makeText(Search.this, text, Toast.LENGTH_SHORT).show();
                        riqi.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //设置bottombar点击时间
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId)
                {
                    case R.id.tab_in:
                        num=0;
                        num1=0;
                       lv.removeAllViews();
                       totals.clear();
                       totals2.clear();
                       in_search();  //打开收入搜素框
                        break;
                    case R.id.tab_out:
                        num=0;
                        num1=0;
                        lv.removeAllViews();
                        totals.clear();
                        totals2.clear();
                        out_search();
                        break;
                }
            }
        });

    }

    public void out_search() {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(num1<1) {

                        if(riqi.getText().equals("日期"))
                        {
                            Person person= BmobUser.getCurrentUser(Person.class);
                            Toast.makeText(Search.this, query, Toast.LENGTH_SHORT).show();
                            BmobQuery<Output> outputBmobQuery = new BmobQuery<Output>();
                            BmobQuery<Output> outputBmobQuery2 = new BmobQuery<Output>();
                            BmobQuery<Output> outputBmobQuery3 = new BmobQuery<Output>();
                            outputBmobQuery.addWhereEqualTo("type", query);
                            outputBmobQuery2.addWhereEqualTo("id", person.getObjectId());

                            List<BmobQuery<Output>> output=new ArrayList<BmobQuery<Output>>();
                            output.add(outputBmobQuery);
                            output.add(outputBmobQuery2);


                            BmobQuery<Output> Totaloutput= new BmobQuery<Output>();
                            Totaloutput.and(output);
                            Totaloutput.setLimit(10);
                            Totaloutput.findObjects(new FindListener<Output>() {
                                @Override
                                public void done(List<Output> list, BmobException e) {
                                    if (e == null) {
                                        for(Output output:list)
                                        { switch (output.getType())
                                        {
                                            case "吃喝":
                                                Total total=new Total(output.getType(),R.mipmap.ic_drink,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total);
                                                break;
                                            case "交通":
                                                Total total2=new Total(output.getType(),R.mipmap.ic_car,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total2);
                                                break;
                                            case "话费":
                                                Total total3=new Total(output.getType(),R.mipmap.ic_phone,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total3);
                                                break;
                                            case "游戏":
                                                Total total4=new Total(output.getType(),R.mipmap.ic_play,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total4);
                                                break;
                                            case "医疗":
                                                Total total5=new Total(output.getType(),R.mipmap.ic_medicine,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total5);
                                                break;
                                            case "宠物":
                                                Total total6=new Total(output.getType(),R.mipmap.ic_rabbit,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total6);
                                                break;
                                            case "红包":
                                                Total total7=new Total(output.getType(),R.mipmap.ic_money1,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total7);
                                                break;
                                            case "日用品":
                                                Total total8=new Total(output.getType(),R.mipmap.ic_skirts,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total8);
                                                break;
                                            default:
                                                Total total9 = new Total(output.getType(),R.mipmap.ic_java, String.valueOf(output.getMuch()), output.getNum(), output.getDay());
                                                totals.add(total9);
                                                break;
                                        }
                                        }
                                        adapter_out = new Out_Adapter(totals);
                                        lv.setAdapter(adapter_out);
                                    }
                                    else {
                                        Toast.makeText(Search.this,"查询失败，请重新输入",Toast.LENGTH_SHORT).show();
                                        num1=0;
                                    }
                                }
                            });
                        }
                        else {
                            Person person= BmobUser.getCurrentUser(Person.class);
                            Toast.makeText(Search.this, query, Toast.LENGTH_SHORT).show();
                            BmobQuery<Output> outputBmobQuery = new BmobQuery<Output>();
                            BmobQuery<Output> outputBmobQuery2 = new BmobQuery<Output>();
                            BmobQuery<Output> outputBmobQuery3 = new BmobQuery<Output>();
                            outputBmobQuery.addWhereEqualTo("type", query);
                            outputBmobQuery2.addWhereEqualTo("id", person.getObjectId());
                            outputBmobQuery3.addWhereEqualTo("day",riqi.getText().toString());

                            List<BmobQuery<Output>> output=new ArrayList<BmobQuery<Output>>();
                            output.add(outputBmobQuery);
                            output.add(outputBmobQuery2);
                            output.add(outputBmobQuery3);

                            BmobQuery<Output> Totaloutput= new BmobQuery<Output>();
                            Totaloutput.and(output);
                            Totaloutput.setLimit(10);
                            Totaloutput.findObjects(new FindListener<Output>() {
                                @Override
                                public void done(List<Output> list, BmobException e) {
                                    if (e == null) {
                                        for(Output output:list)
                                        { switch (output.getType())
                                        {
                                            case "吃喝":
                                                Total total=new Total(output.getType(),R.mipmap.ic_drink,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total);
                                                break;
                                            case "交通":
                                                Total total2=new Total(output.getType(),R.mipmap.ic_car,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total2);
                                                break;
                                            case "话费":
                                                Total total3=new Total(output.getType(),R.mipmap.ic_phone,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total3);
                                                break;
                                            case "游戏":
                                                Total total4=new Total(output.getType(),R.mipmap.ic_play,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total4);
                                                break;
                                            case "医疗":
                                                Total total5=new Total(output.getType(),R.mipmap.ic_medicine,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total5);
                                                break;
                                            case "宠物":
                                                Total total6=new Total(output.getType(),R.mipmap.ic_rabbit,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total6);
                                                break;
                                            case "红包":
                                                Total total7=new Total(output.getType(),R.mipmap.ic_money1,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total7);
                                                break;
                                            case "日用品":
                                                Total total8=new Total(output.getType(),R.mipmap.ic_skirts,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                                totals.add(total8);
                                                break;
                                            default:
                                                Total total9 = new Total(output.getType(),R.mipmap.ic_java, String.valueOf(output.getMuch()), output.getNum(), output.getDay());
                                                totals.add(total9);
                                                break;
                                        }
                                        }
                                        adapter_out = new Out_Adapter(totals);
                                        lv.setAdapter(adapter_out);
                                    }
                                    else {
                                        Toast.makeText(Search.this,"查询失败，请重新输入",Toast.LENGTH_SHORT).show();
                                        num1=0;
                                    }
                                }
                            });
                        }
                        num1++;
                    }
                    else {
                        Toast.makeText(Search.this,"请勿重复查询",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    lv.removeAllViews();
                    totals.clear();
                    num1 = 0;
                    return true;
                }
            });
        }



    public  void in_search() {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (num < 1) {
                        if(riqi.getText().equals("日期")) {
                            Toast.makeText(Search.this, query, Toast.LENGTH_SHORT).show();
                            Person person = BmobUser.getCurrentUser(Person.class);
                            BmobQuery<Input> inputBmobQuery = new BmobQuery<Input>();
                            BmobQuery<Input> inputBmobQuery2 = new BmobQuery<Input>();
                            inputBmobQuery2.addWhereEqualTo("id", person.getObjectId());
                            inputBmobQuery.addWhereEqualTo("type", query);
                            List<BmobQuery<Input>> input = new ArrayList<BmobQuery<Input>>();
                            input.add(inputBmobQuery);
                            input.add(inputBmobQuery2);
                            BmobQuery<Input> Totalinput = new BmobQuery<Input>();
                            Totalinput.and(input);
                            Totalinput.setLimit(10);
                            Totalinput.findObjects(new FindListener<Input>() {
                                @Override
                                public void done(List<Input> list, BmobException e) {
                                    if (e == null) {
                                        for (Input input : list) {
                                            switch (input.getType())
                                            {
                                                case "兼职":
                                                    Total total = new Total(input.getType(), R.mipmap.ic_work, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total);
                                                    break;
                                                case "理财":
                                                    Total total1 = new Total(input.getType(), R.mipmap.ic_money1, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total1);
                                                    break;
                                                case "红包":
                                                    Total total2 = new Total(input.getType(), R.mipmap.ic_bill, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total2);
                                                    break;
                                                case "投资":
                                                    Total total3 = new Total(input.getType(),R.mipmap.ic_tou, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total3);
                                                    break;
                                                case "奖金":
                                                    Total total4 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total4);
                                                    break;
                                                default:
                                                    Total total5 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total5);
                                                    break;
                                            }
                                        }
                                        adapter = new TotalAdapter(totals2);
                                        lv.setAdapter(adapter);
                                    } else {
                                        Toast.makeText(Search.this, "查询失败，请重新输入", Toast.LENGTH_SHORT).show();
                                        num = 0;
                                    }

                                }
                            });

                        }
                        else {
                            Toast.makeText(Search.this, query, Toast.LENGTH_SHORT).show();
                            Person person = BmobUser.getCurrentUser(Person.class);
                            BmobQuery<Input> inputBmobQuery = new BmobQuery<Input>();
                            BmobQuery<Input> inputBmobQuery2 = new BmobQuery<Input>();
                            BmobQuery<Input> inputBmobQuery3 = new BmobQuery<Input>();

                            inputBmobQuery2.addWhereEqualTo("id", person.getObjectId());
                            inputBmobQuery.addWhereEqualTo("type", query);
                            inputBmobQuery3.addWhereEqualTo("day",riqi.getText().toString());

                            List<BmobQuery<Input>> input = new ArrayList<BmobQuery<Input>>();
                            input.add(inputBmobQuery);
                            input.add(inputBmobQuery2);
                            input.add(inputBmobQuery3);
                            BmobQuery<Input> Totalinput = new BmobQuery<Input>();
                            Totalinput.and(input);
                            Totalinput.setLimit(10);
                            Totalinput.findObjects(new FindListener<Input>() {
                                @Override
                                public void done(List<Input> list, BmobException e) {
                                    if (e == null) {
                                        for (Input input : list) {
                                            switch (input.getType())
                                            {
                                                case "兼职":
                                                    Total total = new Total(input.getType(), R.mipmap.ic_work, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total);
                                                    break;
                                                case "理财":
                                                    Total total1 = new Total(input.getType(), R.mipmap.ic_money1, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total1);
                                                    break;
                                                case "红包":
                                                    Total total2 = new Total(input.getType(), R.mipmap.ic_bill, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total2);
                                                    break;
                                                case "投资":
                                                    Total total3 = new Total(input.getType(),R.mipmap.ic_tou, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total3);
                                                    break;
                                                case "奖金":
                                                    Total total4 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total4);
                                                    break;
                                                default:
                                                    Total total5 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                                    totals2.add(total5);
                                                    break;
                                            }
                                        }
                                        adapter = new TotalAdapter(totals2);
                                        lv.setAdapter(adapter);
                                    } else {
                                        Toast.makeText(Search.this, "查询失败，请重新输入", Toast.LENGTH_SHORT).show();
                                        num = 0;
                                    }

                                }
                            });
                        }
                        num++;
                    }
                    else {
                        Toast.makeText(Search.this,"请勿重复查询",Toast.LENGTH_SHORT).show();
                    }

                        return true;

                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    lv.removeAllViews();
                    totals2.clear();
                    num = 0;
                    return true;
                }
            });
        }

    /**
     * 隐藏 DatePicker的年和日
     *
     * @param dialog
     */
    private void hidYearDay(Dialog dialog) {
        int SDKVersion;
        try {
            SDKVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            SDKVersion = 0;
        }

        DatePicker dp = findDatePicker((ViewGroup) dialog.getWindow()
                .getDecorView());

        if (dp != null) {
            if (SDKVersion < 11) {
                ((ViewGroup) dp.getChildAt(0)).getChildAt(3).setVisibility(
                        View.GONE);

            } else if (SDKVersion > 14) {
                View view1 = ((ViewGroup) ((ViewGroup) dp.getChildAt(0))
                        .getChildAt(0)).getChildAt(3);
                view1.setVisibility(View.GONE);

            }
        }
    }

    /**
     * 从当前Dialog中查找DatePicker子控件
     *
     * @param group
     * @return
     */
    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

}
