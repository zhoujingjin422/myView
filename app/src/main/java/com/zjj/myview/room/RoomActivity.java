package com.zjj.myview.room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zjj.myview.databinding.ActivityRoomBinding;

import java.util.List;
import java.util.zip.Adler32;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
public class RoomActivity extends AppCompatActivity {

    private ActivityRoomBinding bind;
    private WordViewModel wordViewModel;
    private RoomAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRoomBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        setContentView(view);
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoomAdapter();
        bind.recyclerView.setAdapter(adapter);
        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);
        wordViewModel.getAllWORDS().observe(this, adapter::submitList);

        //        wordViewModel.getAllWORDS().observe(this, new Observer<List<Words>>() {
//            @Override
//            public void onChanged(List<Words> allWords) {
//               adapter.setAllWords( wordViewModel.getAllWORDS().getValue());
//                adapter.notifyDataSetChanged();
//            }
//        });
        bind.buttonInsert.setOnClickListener(v->insertData());
        bind.buttonDeleteAll.setOnClickListener(v -> wordViewModel.deleteAll());
        bind.buttonUpdate.setOnClickListener(v -> {
            Words words = new Words("hi", "你好不好");
            words.setId(188);
            wordViewModel.upDate(words);
        });
        bind.buttonDelete.setOnClickListener(v -> {
            Words words = new Words("hi", "你好啊");
            words.setId(169);
            wordViewModel.delete(words);
        });
    }

    private void insertData() {
       wordViewModel.insert(new Words("hi","你好"),new Words("world","世界"));
    }
}
