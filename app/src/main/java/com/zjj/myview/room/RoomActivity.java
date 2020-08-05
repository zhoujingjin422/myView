package com.zjj.myview.room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
        bind.textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        wordsDao = word_database.getWordsDao();
        updateView();
        bind.buttonInsert.setOnClickListener(v->insertData());
        bind.buttonDeleteAll.setOnClickListener(v -> {wordsDao.deleteAll();updateView();});
        bind.buttonUpdate.setOnClickListener(v -> {
            Words words = new Words("hi", "你好啊");
            words.setId(50);
            wordsDao.updateWords(words);
            updateView();
        });
        bind.buttonDelete.setOnClickListener(v -> {
            Words words = new Words("hi", "你好啊");
            words.setId(49);
            wordsDao.deleteWords(words);
            updateView();
        });
    }

    private void insertData() {
        wordsDao.insertWords(new Words("hi","你好"),new Words("world","世界"));
        updateView();
    }

    private void updateView() {
        List<Words> allWords = wordsDao.getAllWORDS();
        String text="";
        for (Words words: allWords ) {
            text += words.getId()+";"+words.getWord()+"="+words.getChineseMeaning()+"\n";
        }
        bind.textView.setText(text);
    }
}
