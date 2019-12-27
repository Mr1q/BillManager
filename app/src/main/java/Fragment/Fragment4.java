package Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.billmanger.CallBackvalue;
import com.example.hp.billmanger.Input;
import com.example.hp.billmanger.MainActivity;
import com.example.hp.billmanger.MyPickerDialog;
import com.example.hp.billmanger.Out_Adapter;
import com.example.hp.billmanger.Output;
import com.example.hp.billmanger.Person;
import com.example.hp.billmanger.R;
import com.example.hp.billmanger.Total;
import com.example.hp.billmanger.TotalAdapter;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment4 extends Fragment{

    View view;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private List<Total> total_in;
    private List<Total> total_out;
    private static double Sum_in=0;
    private  static  double Sum_out=0;
    private  String in,out;
    TextView textView;
    TextView textView2;
    TextView textView3;
    private DrawerLayout drawerLayout;
    private TextView type;

    private NavigationView navigationView;
    private  Button myedit;  //选择时间

    Person person;
    TotalAdapter totalAdapter;
    Out_Adapter out_adapter;

private  SwipeRefreshLayout swipeRefreshLayout;
    private  SwipeRefreshLayout swipeRefreshLayout2;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.total_layout,container,false);
        swipeRefreshLayout =view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout2=view.findViewById(R.id.refresh_layout2);
        type=(TextView)view.findViewById(R.id.type);


        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                total_out.clear();
                recyclerView2.removeAllViews();
                Find_Output();
                swipeRefreshLayout2.setRefreshing(false);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                total_in.clear();
                recyclerView.removeAllViews();
                Find_Input();
                swipeRefreshLayout.setRefreshing(false);
                }
        });

        person= BmobUser.getCurrentUser(Person.class);
        myedit=(Button)view.findViewById(R.id.riqi);  //日期
       total_in =new ArrayList<>();
        total_out =new ArrayList<>();
        Find_Input();
        Find_Output();


        myedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                new MyPickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String text = "你选择了" + year + "年" + (month + 1) + "月";  //月份从0开始计数
                        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        myedit.setText(year + "-" + (month + 1) );
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }

        });

        TextWatcher mtextwatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                total_in.clear();
                total_out.clear();
                recyclerView.removeAllViews();
                recyclerView2.removeAllViews();
                Find_Input();
                Find_Output();
            }
        };
        myedit.addTextChangedListener(mtextwatcher);

        //收入表
    //支出表
    recyclerView2=(RecyclerView)view.findViewById(R.id.recycleview_output);
    textView2=(TextView)view.findViewById(R.id.total_out);
    textView3=(TextView)view.findViewById(R.id.total_left);
    StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
    recyclerView2.setLayoutManager(manager2);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (navigationView != null) {
            navigationView.setCheckedItem(R.id.Message);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.Message:
                            type.setText("收入明细");
                            recyclerView2.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            swipeRefreshLayout2.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.Message2:
                            type.setText("支出明细");
                            swipeRefreshLayout2.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.GONE);
                            recyclerView2.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            drawerLayout.closeDrawers();
                            break;
                   default:break;
                    }
                    return true;

                }
            });
        }
//        监听滑动菜单栏

    }

    private void Find_Output() {

        if(myedit.getText().equals("日期"))
        {
            recyclerView2=(RecyclerView)view.findViewById(R.id.recycleview_output);
            textView2=(TextView)view.findViewById(R.id.total_out);
            StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
            recyclerView2.setLayoutManager(manager2);
            BmobQuery<Output> inputBmobQuery=new BmobQuery<Output>();
            inputBmobQuery.addWhereEqualTo("id",person.getObjectId());
            inputBmobQuery.setLimit(10);
            inputBmobQuery.findObjects(new FindListener<Output>() {
                @Override
                public void done(List<Output> list, BmobException e) {
                    if(e==null)
                    {
                        for(Output output:list)
                        { switch (output.getType())
                        {
                            case "吃喝":
                                Total total=new Total(output.getType(),R.mipmap.ic_drink,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total);
                                break;
                            case "交通":
                                Total total2=new Total(output.getType(),R.mipmap.ic_car,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total2);
                                break;
                            case "话费":
                                Total total3=new Total(output.getType(),R.mipmap.ic_phone,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total3);
                                break;
                            case "游戏":
                                Total total4=new Total(output.getType(),R.mipmap.ic_play,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total4);
                                break;
                            case "医疗":
                                Total total5=new Total(output.getType(),R.mipmap.ic_medicine,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total5);
                                break;
                            case "宠物":
                                Total total6=new Total(output.getType(),R.mipmap.ic_rabbit,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total6);
                                break;
                            case "红包":
                                Total total7=new Total(output.getType(),R.mipmap.ic_money1,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total7);
                                break;
                            case "日用品":
                                Total total8=new Total(output.getType(),R.mipmap.ic_skirts,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                total_out.add(total8);
                                break;
                            default:
                                Total total9 = new Total(output.getType(),R.mipmap.ic_java, String.valueOf(output.getMuch()), output.getNum(), output.getDay());
                                total_out.add(total9);
                            break;
                        }

                        }
                        intit();
                        out_adapter=new Out_Adapter(total_out);
                        recyclerView2.setAdapter(out_adapter);
                        textView2.setText("-"+out);
                        Sum_in=0;
                        Sum_out=0;

                    }
                }
            });

        }
        else
        {
            recyclerView2=(RecyclerView)view.findViewById(R.id.recycleview_output);
            textView2=(TextView)view.findViewById(R.id.total_out);
            StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
            recyclerView2.setLayoutManager(manager2);
            BmobQuery<Output> inputBmobQuery=new BmobQuery<Output>();
            BmobQuery<Output> inputBmobQuery2=new BmobQuery<Output>();
            inputBmobQuery2.addWhereEqualTo("YM",myedit.getText());
            inputBmobQuery.addWhereEqualTo("id",person.getObjectId());

            List<BmobQuery<Output>> output=new ArrayList<BmobQuery<Output>>();
            output.add(inputBmobQuery);
            output.add(inputBmobQuery2);

            BmobQuery<Output> Total=new BmobQuery<Output>();
            Total.and(output);
            Total.setLimit(15);
            Total.findObjects(new FindListener<Output>() {
                @Override
                public void done(List<Output> list, BmobException e) {
                    if(e==null)
                    {
                        for(Output output:list)
                        {
                            switch (output.getType())
                            {
                                case "吃喝":
                                    Total total=new Total(output.getType(),R.mipmap.ic_drink,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total);
                                    break;
                                case "交通":
                                    Total total2=new Total(output.getType(),R.mipmap.ic_car,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total2);
                                    break;
                                case "话费":
                                    Total total3=new Total(output.getType(),R.mipmap.ic_phone,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total3);
                                    break;
                                case "游戏":
                                    Total total4=new Total(output.getType(),R.mipmap.ic_play,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total4);
                                    break;
                                case "医疗":
                                    Total total5=new Total(output.getType(),R.mipmap.ic_medicine,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total5);
                                    break;
                                case "宠物":
                                    Total total6=new Total(output.getType(),R.mipmap.ic_rabbit,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total6);
                                    break;
                                case "红包":
                                    Total total7=new Total(output.getType(),R.mipmap.ic_money1,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total7);
                                    break;
                                case "日用品":
                                    Total total8=new Total(output.getType(),R.mipmap.ic_skirts,String.valueOf(output.getMuch()),output.getNum(),output.getDay());
                                    total_out.add(total8);
                                    break;
                                default:
                                    Total total9 = new Total(output.getType(),R.mipmap.ic_java, String.valueOf(output.getMuch()), output.getNum(), output.getDay());
                                    total_out.add(total9);
                                    break;
                            }

                        }
                        intit();
                        out_adapter=new Out_Adapter(total_out);
                        recyclerView2.setAdapter(out_adapter);
                        textView2.setText("-"+out);
                        Sum_in=0;
                        Sum_out=0;

                    }
                }
            });

        }

    }

    private void Find_Input() {
        if(myedit.getText().equals("日期")) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_input);
            textView = (TextView) view.findViewById(R.id.total_in);
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            BmobQuery<Input> inputBmobQuery = new BmobQuery<Input>();
            inputBmobQuery.addWhereEqualTo("id", person.getObjectId());
            inputBmobQuery.setLimit(10);
            inputBmobQuery.findObjects(new FindListener<Input>() {
                @Override
                public void done(List<Input> list, BmobException e) {
                    if (e == null) {
                        for (Input input : list) {
                            switch (input.getType())
                            {
                                case "兼职":
                                    Total total = new Total(input.getType(), R.mipmap.ic_work, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total);
                                    break;
                                case "理财":
                                    Total total1 = new Total(input.getType(), R.mipmap.ic_money1, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total1);
                                    break;
                                case "红包":
                                    Total total2 = new Total(input.getType(), R.mipmap.ic_bill, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total2);
                                    break;
                                case "投资":
                                    Total total3 = new Total(input.getType(),R.mipmap.ic_tou, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total3);
                                    break;
                                case "奖金":
                                    Total total4 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total4);
                                    break;
                                default:
                                    Total total5 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total5);
                                    break;
                            }
                        }
                        intit();
                        totalAdapter = new TotalAdapter(total_in);
                        recyclerView.setAdapter(totalAdapter);
                        textView.setText("+" + in);
                        Sum_in = 0;
                        Sum_out = 0;

                    }
                }
            });
        }
        else
        {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_input);
            textView = (TextView) view.findViewById(R.id.total_in);
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            BmobQuery<Input> inputBmobQuery = new BmobQuery<Input>();
            BmobQuery<Input> inputBmobQuery2 = new BmobQuery<Input>();
            inputBmobQuery.addWhereEqualTo("id", person.getObjectId());
            inputBmobQuery2.addWhereEqualTo("YM",myedit.getText());

            List<BmobQuery<Input>> input =new ArrayList<BmobQuery<Input>>();
            input.add(inputBmobQuery);
            input.add(inputBmobQuery2);

            BmobQuery<Input> Total=new BmobQuery<Input>();
            Total.and(input);
            Total.setLimit(15);
            Total.findObjects(new FindListener<Input>() {
                @Override
                public void done(List<Input> list, BmobException e) {
                    if (e == null) {
                        for (Input input : list) {
                            switch (input.getType())
                            {
                                case "兼职":
                                    Total total = new Total(input.getType(), R.mipmap.ic_work, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total);
                                    break;
                                case "理财":
                                    Total total1 = new Total(input.getType(), R.mipmap.ic_money1, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total1);
                                    break;
                                case "红包":
                                    Total total2 = new Total(input.getType(), R.mipmap.ic_bill, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total2);
                                    break;
                                case "投资":
                                    Total total3 = new Total(input.getType(),R.mipmap.ic_tou, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total3);
                                    break;
                                case "奖金":
                                    Total total4 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total4);
                                    break;
                                default:
                                    Total total5 = new Total(input.getType(),R.mipmap.ic_pay, String.valueOf(input.getMuch()), input.getNum(), input.getDay());
                                    total_in.add(total5);
                                    break;
                            }
                        }
                        intit();
                        totalAdapter = new TotalAdapter(total_in);
                        recyclerView.setAdapter(totalAdapter);
                        textView.setText("+" + in);
                        Sum_in = 0;
                        Sum_out = 0;

                    }
                }
            });


        }
    }


    private void intit() {
        for(Total total:total_in)
        {
            Sum_in+=Double.parseDouble( total.getMuch().toString());
        }
       DecimalFormat format = new DecimalFormat("#.00");
       in=format.format(Sum_in);
        for(Total total:total_out)
        {
            Sum_out+=Double.parseDouble( total.getMuch().toString());
        }
        DecimalFormat format1 = new DecimalFormat("#.00");
       out =format1.format(Sum_out);

        textView3.setText(String.valueOf(Sum_in-Sum_out));
        if(Sum_in-Sum_out<=0)
        {
            textView3.setTextColor(Color.GREEN);
        }
        else
        {
            textView3.setTextColor(Color.RED);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity=(Activity)context;
        if(activity instanceof MainActivity)
        {
         navigationView =((CallBackvalue)context).getnav();
       drawerLayout=((CallBackvalue)context).getdraw();
        }
        else return;
    }
}
