package com.example.hp.billmanger;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.ViewHolder> {

    List<Total> totals=new ArrayList<>();
    Input in;
    public TotalAdapter(List<Total> totals)
    {
        this.totals=totals;
    }

    @NonNull
    @Override
    public TotalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.total_type,parent,false);
       final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v,"确认删除吗",Snackbar.LENGTH_LONG).setAction("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int Position=viewHolder.getAdapterPosition();
                        Total input=totals.get(Position);
                        in=new Input();
                        BmobQuery<Input> e=new BmobQuery<Input>();
                        e.addWhereEqualTo("Num",input.getId());
                        e.findObjects(new FindListener<Input>() {
                            @Override
                            public void done(List<Input> list, BmobException e) {
                                for(Input input1:list)
                                {
                                    in.setObjectId(input1.getObjectId());
                                    in.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null)
                                            {
                                                Toast.makeText(view.getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                                removeItem(Position);

                                            }
                                        }
                                    });
                                    break;
                                }

                            }
                        });
                    }
                }).show();


                return true;
            }
        });


        //点击更新事件
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Position=viewHolder.getAdapterPosition();
                Total input=totals.get(Position);
                in=new Input();
                BmobQuery<Input> e=new BmobQuery<Input>();
                e.addWhereEqualTo("Num",input.getId());
                e.findObjects(new FindListener<Input>() {
                    @Override
                    public void done(List<Input> list, BmobException e) {
                        for(Input input1:list)
                        {
                            Intent intent=new Intent(view.getContext(),Update.class);
                            intent.putExtra("type",input1.getType());
                            intent.putExtra("much",input1.getMuch());
                            intent.putExtra("id",input1.getObjectId());
                            intent.putExtra("num",input1.getNum());
                            intent.putExtra("S","0");       //判断传入是收入还是支出类型
                            intent.putExtra("Day",input1.getDay());
                            view.getContext().startActivity(intent);
                            break;
                        }
                    }
                });


            }
        });

        return viewHolder;
    }

    //删除适配器里的元素
    public void removeItem(int pos){
        totals.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public void onBindViewHolder(@NonNull TotalAdapter.ViewHolder holder, int position) {
        Total total=totals.get(position);
        holder.totalimage.setImageResource(total.getImageId());
        holder.totaltext.setText(total.getName());
        holder.totalmuch.setText("+"+total.getMuch());
        holder.day.setText(total.getDay());

    }


    @Override
    public int getItemCount() {
        return totals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView totalimage;
        TextView totaltext;
        TextView totalmuch;
        TextView day;
    View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            totalimage=(ImageView)itemView.findViewById(R.id.total_image);
            totaltext=(TextView)itemView.findViewById(R.id.total_name);
            totalmuch=(TextView)itemView.findViewById(R.id.totalmuch);
            day=(TextView)itemView.findViewById(R.id.Day_riqi);
        }
    }
}
