package com.example.hp.billmanger;

import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

public class Out_Adapter  extends RecyclerView.Adapter<Out_Adapter.ViewHolder> {
    List<Total> totals=new ArrayList<>();
    Output out;
    public Out_Adapter(List<Total> totals)
    {
        this.totals=totals;
    }

    @NonNull
    @Override
    public Out_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.total_type,parent,false);
        final Out_Adapter.ViewHolder viewHolder=new ViewHolder(view);

        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               final int Position=viewHolder.getAdapterPosition();
                Total input=totals.get(Position);
                out=new Output();
                BmobQuery<Output> e=new BmobQuery<Output>();
                e.addWhereEqualTo("Num",input.getId());
                e.findObjects(new FindListener<Output>() {
                    @Override
                    public void done(List<Output> list, BmobException e) {
                      if(e==null) {
                          Output output1 = (Output) list.get(0);
                          out.setObjectId(output1.getObjectId());
                          out.delete(new UpdateListener() {
                              @Override
                              public void done(BmobException e) {
                                  if (e == null) {
                                      Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                      removeItem(Position);
                                  }
                              }
                          });

                      }

                    }
                });

                return true;
            }
        });



        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Position=viewHolder.getAdapterPosition();
                Total output=totals.get(Position);
                Output in = new Output();
                BmobQuery<Output> e=new BmobQuery<Output>();
                e.addWhereEqualTo("Num",output.getId());
                e.findObjects(new FindListener<Output>() {
                    @Override
                    public void done(List<Output> list, BmobException e) {
                        for(Output output:list)
                        {
                            Intent intent=new Intent(view.getContext(),Update.class);
                            intent.putExtra("type",output.getType());
                            intent.putExtra("much",output.getMuch());
                            intent.putExtra("id",output.getObjectId());
                            intent.putExtra("num",output.getNum());
                            intent.putExtra("S","1");
                            intent.putExtra("Day",output.getDay());
                            view.getContext().startActivity(intent);
                            break;
                        }
                    }
                });

            }
        });





        return viewHolder;
    }
    public void removeItem(int pos){
        totals.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Total total=totals.get(position);
        holder.totalimage.setImageResource(total.getImageId());
        holder.totaltext.setText(total.getName());
        holder.totalmuch.setText("-"+total.getMuch());
        holder.totalmuch.setTextColor(Color.RED);
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
