package com.zjj.myview.room;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import com.zjj.myview.R;
import com.zjj.myview.databinding.ActivityRoomBinding;

import java.util.List;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
public class RoomActivity extends AppCompatActivity {

    private ActivityRoomBinding bind;
    private WordsDatabase word_database;
    private WordsDao wordsDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRoomBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        setContentView(view);
        word_database = Room.databaseBuilder(getApplicationContext(), WordsDatabase.class, "word_database")
                .allowMainThreadQueries()//允许在主线程运行
                .build();
        wordsDao = word_database.getWordsDao();
        updateView();
        bind.buttonInsert.setOnClickListener(v->insertData());
    }

    private void insertData() {
        wordsDao.insertWords(new Words("hi","你好"),new Words("world","世界"));
    }

    private void updateView() {
        List<Words> allWords = wordsDao.getAllWORDS();
        String text="";
        for (Words words: allWords ) {
            text += words.get_id()+";"+words.getWord()+"="+words.getChineseMeaning();
        }
        bind.textView.setText(text);
    }
}
