package com.zjj.myview.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
@Database(entities = {Words.class},version = 1,exportSchema = false)
public abstract class WordsDatabase extends RoomDatabase {
    public abstract WordsDao getWordsDao();
}
