package com.example.hp.billmanger;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.roughike.bottombar.BottomBar;

public interface CallBackvalue  {
    TextView gettexview();
    ImageView getimgeview();
    EditText getMyEdit();
    BottomBar getbottombar();
    NavigationView getnav();
    DrawerLayout getdraw();
}
