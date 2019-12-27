package com.example.hp.billmanger;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {
    private List<Income> mincomes;
    private TextView editText;
    private  TextView day;

    static class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView incomeimage;
        TextView incometext;
        View  incomeView;
        public  ViewHolder(View view)
        {
            super(view);
            incomeView=view;
            incomeimage=(ImageView)view.findViewById(R.id.input_image);
            incometext=(TextView)view.findViewById(R.id.input_name);
        }
    }
    public  IncomeAdapter(List<Income> incomes)
    {
        mincomes=incomes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.input_type,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    private MyItemClick myItemClick;

    public interface MyItemClick{
        void onItemClick(View view, int postion);
    }
    public void setOnItemClick(MyItemClick myItemClick){
        this.myItemClick = myItemClick;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Income income=mincomes.get(position);
        holder.incomeimage.setImageResource(income.getImageId());
        holder.incometext.setText(income.getName());
        if(myItemClick!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    myItemClick.onItemClick(holder.itemView,position);
                }
            });

        }


    }

    @Override
    public int getItemCount()
    {
        return mincomes.size();
    }




}
