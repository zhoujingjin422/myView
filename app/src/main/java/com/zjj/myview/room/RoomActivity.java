package com.zjj.myview.room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zjj.myview.databinding.ActivityRoomBinding;

import java.util.List;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
public class RoomActivity extends AppCompatActivity {

    private ActivityRoomBinding bind;
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRoomBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        setContentView(view);
        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);
        bind.textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        wordViewModel.getAllWORDS().observe(this, new Observer<List<Words>>() {
            @Override
            public void onChanged(List<Words> allWords) {
                StringBuilder text= new StringBuilder();
                for (Words words: allWords ) {
                    text.append(words.getId()).append(";").append(words.getWord()).append("=").append(words.getChineseMeaning()).append("\n");
                }
                bind.textView.setText(text.toString());
            }
        });
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
