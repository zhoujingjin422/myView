package com.zjj.myview.room;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zjj.myview.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
public class RoomAdapter extends PagedListAdapter<Words,RoomAdapter.MyViewHolder> {
    protected RoomAdapter() {
        super(new DiffUtil.ItemCallback<Words>() {

            @Override
            public boolean areItemsTheSame(Words oldItem, Words newItem) {
                // The ID property identifies when items are the same.
                return oldItem.getId() == newItem.getId();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(Words oldItem, Words newItem) {
                // Don't use the "==" operator here. Either implement and use .equals(),
                // or write custom data comparison logic here.
                return oldItem.equals(newItem);
            }
        });
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Words words = getItem(position);
//        Words words = list.get(position);
        holder.binding.textView.setText(String.valueOf(position));
        holder.binding.textView2.setText(words.getWord());
        holder.binding.textView3.setText(words.getChineseMeaning());
    }

//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void setAllWords(List<Words> value) {
//        this.list = value;
//    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        public  ItemViewBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemViewBinding.bind(itemView);
        }
    }
}
