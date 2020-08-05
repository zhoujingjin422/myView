package com.zjj.myview.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
@Database(entities = {Words.class},version = 1,exportSchema = false)
public abstract class WordsDatabase extends RoomDatabase {
    private static WordsDatabase INSTANCE;
    static synchronized WordsDatabase getWordsDatabase(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordsDatabase.class,"word_database")
//                    .allowMainThreadQueries()//允许在主线程访问数据库
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordsDao getWordsDao();
}
