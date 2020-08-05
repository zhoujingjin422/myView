package com.zjj.myview.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
@Dao
public interface WordsDao {
    @Insert
    void insertWords(Words ... words);

    @Update
    void updateWords(Words ... words);

    @Delete
    void deleteWords(Words...words);

    @Query("DELETE  FROM WORDS")
    void deleteAll();

    @Query("SELECT * FROM WORDS ORDER BY ID DESC")
    List<Words> getAllWORDS();
}
